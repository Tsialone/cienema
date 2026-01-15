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

import com.cinema.dev.repositories.CategoriePlaceRepository;

@Entity
@Getter
@Setter
@ToString
@SuperBuilder
@NoArgsConstructor
@Table(name = "categorie_p")
public class CategoriePlace {

    @Id
    @Column(name = "id_cp", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCp;

    @Column(name = "libelle", length = 50)
    private String libelle;

    @Column(name = "prix_defaut", nullable = false)
    private BigDecimal prixDefaut;

    @OneToMany(mappedBy = "categoriePlace")
    private List<Place> places = new ArrayList<>();

    @OneToMany(mappedBy = "categoriePlace")
    private List<PrixC> prixCs = new ArrayList<>();

    @Transient
    public final String str = "CAT";

    @Transient
    public String getStrId() {
        return str + idCp;
    }

    @Transient
    public static List<CategoriePlace> getAll(CategoriePlaceRepository categoriePlaceRepository) {
        return categoriePlaceRepository.findAll();
    }

    @Transient
    public BigDecimal getPrixRecent() {
        if (prixCs == null || prixCs.isEmpty()) {
            return this.getPrixDefaut();
        }
        return prixCs.stream()
                .max((p1, p2) -> p1.getCreated().compareTo(p2.getCreated()))
                .map(PrixC::getPrix)
                .orElse(this.getPrixDefaut());
    }

    @Transient
    public BigDecimal getPrixByDateTime(LocalDateTime dateTime) {
        if (prixCs == null || prixCs.isEmpty() || dateTime == null) {
            return this.getPrixDefaut();
        }
        return prixCs.stream()
                .filter(p -> p.getCreated().isBefore(dateTime) || p.getCreated().isEqual(dateTime))
                .max((p1, p2) -> p1.getCreated().compareTo(p2.getCreated()))
                .map(PrixC::getPrix)
                .orElse(this.getPrixDefaut());
    }
}
