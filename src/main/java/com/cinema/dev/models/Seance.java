package com.cinema.dev.models;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.cinema.dev.repositories.CommandeRepository;
import com.cinema.dev.views.SeanceDetail;
import com.cinema.dev.views.SeanceRecap;

@Entity
@Getter
@Setter
@ToString
@SuperBuilder
@NoArgsConstructor
@Table(name = "seance")
public class Seance {

    @Id
    @Column(name = "id_seance", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idSeance;

    @Column(name = "date_time_debut", nullable = false)
    private LocalDateTime dateTimeDebut;

    @Column(name = "prix_pub", nullable = false)
    private BigDecimal prixPub;

    @ManyToOne
    @JoinColumn(name = "id_salle", nullable = false)
    private Salle salle;

    @ManyToOne
    @JoinColumn(name = "id_film", nullable = false)
    private Film film;

    @OneToMany(mappedBy = "seance", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    private List<Ticket> tickets = new ArrayList<>();

    @OneToMany(mappedBy = "seance", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<Diffusion> diffusions = new ArrayList<>();

    @Transient
    public final String str = "SEC";

    @Transient
    public Double getTotalPayer(CommandeRepository commandeRepository) {
        double total = 0.0;
        for (Diffusion diffusion : diffusions) {   
            CommandeDetail commandeDetail = diffusion.getCommandeDetail();
            if (commandeDetail != null) {
                total+= commandeDetail.getTotalPaye();
            }


            // Pub pub = diffusion.getPub();
            // if (pub != null && pub.getSociete() != null) {
            //     Societe societe = pub.getSociete();
                
            //     List<Commande> commandes =   commandeRepository.findBySocieteIdSociete(societe.getIdSociete());
            //     for (Commande commande : commandes) { 
            //         System.out.println("Commande ID: " + commande.getIdCommande() + " for Societe ID: " + societe.getIdSociete()  + " total: " + commande.getTotalPaiements());
            //         total += commande.getTotalPaiements().doubleValue();
            //     }

            // }
        }

        return total;
    }

    @Transient
    public Double getChiffreAffairePub()

    {
        int totalDifiusions = diffusions != null ? diffusions.size() : 0;
        System.out.println("Total diffusions: " + totalDifiusions + " for Seance ID: " + idSeance);
        for (Diffusion diffusion : diffusions) {
            System.out.println("Diffusion ID: " + diffusion.getIdDiffusion());
        }
        return this.prixPub != null ? this.prixPub.doubleValue() * totalDifiusions : 0.0;
    }

    @Transient
    public Double getChiffreAffaire() {
        double total = 0.0;
        if (tickets != null) {
            for (Ticket ticket : tickets) {
                if (ticket.getPlace() != null && ticket.getPlace().getPrixPlace(this.dateTimeDebut) != null) {
                    total += ticket.getPlace().getPrixPlace(this.dateTimeDebut);
                }
            }
        }
        return total;
    }

    @Transient
    public static SeanceRecap getSeanceRecap(List<Seance> seances) {
        SeanceRecap recap = new SeanceRecap();
        if (seances == null || seances.isEmpty()) {
            recap.setTotalSeance(0);
            recap.setTotalReservation(0);
            recap.setChiffreAffaire(0.0);
            return recap;
        }

        int totalTicket = 0;
        double totalChiffre = 0.0;
        for (Seance seance : seances) {
            if (seance == null || seance.getTickets() == null) {
                continue;
            }
            for (Ticket ticket : seance.getTickets()) {
                if (ticket == null) {
                    continue;
                }
                totalTicket++;
                // if (ticket.getPlace() != null && ticket.getPlace().getPrixPlace() != null) {
                // totalChiffre += ticket.getPlace().getPrixPlace();
                // }
            }
        }
        recap.setTotalSeance(seances.size());
        recap.setChiffreAffaire(totalChiffre);
        recap.setTotalReservation(totalTicket);

        return recap;
    }

    @Transient
    public Double getMaxRevenu() {

        if (getSalle() != null) {
            return getSalle().getMaxPrixPlace();
        }

        return 0.0;

    }

    @Transient
    public Double getMaxRevenu(LocalDateTime dateTime) {

        if (getSalle() != null) {
            return getSalle().getMaxPrixPlace(dateTime);
        }

        return 0.0;

    }

    @Transient
    public String getStrId() {
        return str + idSeance;
    }

    @Transient
    public LocalDateTime getDateFinPrevue() {
        return this.dateTimeDebut.plusMinutes(this.film.getDuree().getHour() * 60 + this.film.getDuree().getMinute());
    }

    @Transient
    public static List<SeanceDetail> getSeanceDetail(LocalDateTime debut, LocalDateTime fin, Long idSalle,
            Long idFilm) {

        return List.of();

    }

}
