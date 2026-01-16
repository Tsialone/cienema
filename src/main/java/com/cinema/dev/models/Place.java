package com.cinema.dev.models;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import com.cinema.dev.repositories.PlaceRepository;
import com.cinema.dev.repositories.ReservationRepository;
import com.cinema.dev.repositories.TicketRepository;
import com.cinema.dev.utils.DateUtils;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@Setter
@ToString
@SuperBuilder
@NoArgsConstructor
@Table(name = "place")
public class Place {

    @Id
    @Column(name = "id_place", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPlace;

    @Column(name = "numero", nullable = false)
    private Integer numero;

    @ManyToOne
    @JoinColumn(name = "id_cp", nullable = false)
    private CategoriePlace categoriePlace;

    @ManyToOne
    @JoinColumn(name = "id_salle", nullable = false)
    private Salle salle;

    @Transient
    private Double prixPlace;

    @Transient
    public static Place getByNumeroPlace(PlaceRepository placeRepository, Long numero) {
        return placeRepository.findByNumero(numero);
    }

    @Transient
    public final String str = "PLC";

    @Transient
    public String getStrId() {
        return str + idPlace;
    }

    // @Transient
    // public Double getPrixPlace() {
    //     return this.categoriePlace.getPrixRecent().doubleValue();
    // }

    @Transient
    public Double getPrixPlace(LocalDateTime dateTime) {
        BigDecimal prix = this.categoriePlace.getPrixByDateTime(dateTime);
        // System.out.println("null ve: " + prix);
        return prix != null ? prix.doubleValue() : this.getCategoriePlace().getPrixDefaut().doubleValue();
    }

    @Transient
    public boolean isDispo(LocalDateTime dateTime, TicketRepository ticketRepository) {
        for (Ticket ticket : ticketRepository.findAll()) {
            Seance seance = ticket.getSeance();
            LocalDateTime debut = seance.getDateTimeDebut();
            LocalDateTime fin = seance.getDateFinPrevue();

            LocalDateTime dateTimeFin = dateTime.plusMinutes(ticket.getFilm().getDuree().getHour() * 60
                    + ticket.getFilm().getDuree().getMinute());
            // if (DateUtils.isConflit(debut, fin, dateTime)) {
            // System.out.println("Place non dispo: " + this.getStrId() + " pour la date " +
            // dateTime);
            // System.out.println("Conflit avec la séance du " + debut + " au " + fin);
            // return false;
            // }

            if (this.idPlace.equals(ticket.getPlace().getIdPlace())
                    && DateUtils.isConflit(debut, fin, dateTime, dateTimeFin)) {
                return false;
            }
            if (this.idPlace.equals(ticket.getPlace().getIdPlace())) {
                System.out.println("Place dispo: " + this.getStrId() + " pour la date " +
                        dateTime);
                System.out.println("Pas de conflit avec la séance du " + debut + " au " +
                        fin);
                System.out.println("\n");

            }

        }
        return true;
    }

}
