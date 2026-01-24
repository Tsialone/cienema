package com.cinema.dev.models;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.cinema.dev.repositories.DiffusionRepository;
import com.cinema.dev.repositories.SocieteRepository;

@Entity
@Getter
@Setter
@ToString
@SuperBuilder
@NoArgsConstructor
@Table(name = "societe")
public class Societe {

    @Id
    @Column(name = "id_societe", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idSociete;

    @Column(name = "nom")
    private String nom;

    @OneToMany(mappedBy = "societe", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<Pub> pubs = new ArrayList<>();

    // @OneToMany(mappedBy = "societe", cascade = CascadeType.ALL, orphanRemoval = true)
    // @Builder.Default
    // private List<PaiementSociete> paiementsSociete = new ArrayList<>();

    @Transient
    public final String str = "SOC";

    @Transient
    public String getStrId() {
        return str + idSociete;
    }

    @Transient
    public static List<Societe> getAll(SocieteRepository societeRepository) {
        return societeRepository.findAll();
    }

    @Transient
     public Double getApayer(LocalDate date, DiffusionRepository diffusionRepository) {
        // double totalPaye = 0.0;
        double aPaye = 0.0;
        // for (PaiementSociete paiement : paiementsSociete) {
        //     if (paiement.getCreated().toLocalDate().isBefore(date)
        //             || paiement.getCreated().toLocalDate().isEqual(date)) {
        //         totalPaye += paiement.getMontant().doubleValue();
        //     }
        // }

        List<Diffusion> diffusions = Diffusion.getByDate(diffusionRepository, date);
        for (Diffusion diffusion : diffusions) {
            if (diffusion.getPub().getSociete().getIdSociete() == this.idSociete)
            aPaye +=diffusion.getSeance().getPrixPub().doubleValue();
        }

        return aPaye;

    }

    @Transient
    public Double getRestePayeByDate(LocalDate date, DiffusionRepository diffusionRepository) {
        double totalPaye = 0.0;
        double aPaye = getApayer(date, diffusionRepository);
        // for (PaiementSociete paiement : paiementsSociete) {
        //     if (paiement.getCreated().toLocalDate().isBefore(date)
        //             || paiement.getCreated().toLocalDate().isEqual(date)) {
        //         totalPaye += paiement.getMontant().doubleValue();
        //     }
        // }

        return aPaye - totalPaye;

    }
}
