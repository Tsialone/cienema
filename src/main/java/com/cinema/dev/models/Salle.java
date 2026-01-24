package com.cinema.dev.models;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.cinema.dev.repositories.CategHeritRepository;
import com.cinema.dev.repositories.CategorieRepository;
import com.cinema.dev.repositories.RemiseRepository;
import com.cinema.dev.repositories.TicketRepository;

@Entity
@Getter
@Setter
@ToString
@SuperBuilder
@NoArgsConstructor
@Table(name = "salle")
public class Salle {

    @Id
    @Column(name = "id_salle", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idSalle;

    @Column(name = "nom")
    private String nom;

    @Column(name = "capacite", nullable = false)
    private Integer capacite;

    @Transient
    public final String str = "SAL";

    @Transient
    public String getStrId() {
        return str + idSalle;
    }

    @OneToMany(mappedBy = "salle", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<Place> places = new ArrayList<>();

    @OneToMany(mappedBy = "salle")
    private List<Seance> seances;

    @Transient
    public Integer getCapaciteMax() {
        return places != null ? places.size() : 0;
    }

    @Transient
    public Double getCa(LocalDateTime date, CategorieRepository categorieRepository, CategHeritRepository categorieH,
            RemiseRepository remiseRepository ) {
        Double resp = 0.0;


        // for (Seance seance : seances) {

        //     for (Ticket ticket : seance.getTickets()) {
        //         // Reservation reservation =  ticket.getReservation();
        //         // Reservation reservation = ticket.getReservation();
        //         if (reservation.getDateReservation().isBefore(date) || ticket.getCreated().isEqual(date)) {
        //             resp += ticket.getMontant(categorieH, categorieRepository, remiseRepository);
        //         }

        //     }
        // }
        return resp;

    }

    @Transient
    public Double getMaxPrixPlace() {
        Double maxPrix = 0.0;
        // System.out.println("------------salle:" + this.getStrId() );
        for (Place place : places) {
            // maxPrix += place.getPrixPlace();
            // System.out.println("place" + place.getStrId() + " prix: " +
            // place.getPrixPlace() );
        }
        // System.out.println("salle:" + this.getStrId() + " total: " + maxPrix );
        return maxPrix;
    }

    @Transient
    public Double getMaxPrixPlace(LocalDateTime dateTime) {
        Double maxPrix = 0.0;
        // System.out.println("------------salle:" + this.getStrId() );
        for (Place place : places) {
            Double prixPlace = place.getPrixPlace(dateTime);
            // if (prixPlace != null) {
            maxPrix += prixPlace;
            // System.out.println("place" + place.getStrId() + " prix: " + prixPlace );
            // }
        }
        // System.out.println("salle:" + this.getStrId() + " total: " + maxPrix );
        return maxPrix;
    }

    @Transient
    public List<Place> getPlaceDispo(LocalDateTime date, TicketRepository ticketRepository) {

        List<Place> dispoPlaces = new ArrayList<>();
        // System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxxxxx");
        for (Place place : this.places) {
            if (place.isDispo(date, ticketRepository)) {
                dispoPlaces.add(place);
            }

        }
        return dispoPlaces;
    }

    @Transient
    public List<Place> getPlaceDispo(LocalDateTime date, Long idCategoriePlace, TicketRepository ticketRepository) {

        List<Place> dispoPlaces = new ArrayList<>();
        // System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxxxxx");
        for (Place place : this.places) {
            if (place.isDispo(date, ticketRepository) && place.getCategoriePlace().getIdCp().equals(idCategoriePlace)) {
                dispoPlaces.add(place);
            }

        }
        return dispoPlaces;
    }

}
