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
    private List<CategoriePrix> categoriePrix = new ArrayList<>();

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
        if (categoriePrix == null || categoriePrix.isEmpty()) {
            return null;
        }
        return categoriePrix.stream()
                .max((cp1, cp2) -> cp1.getCreated().compareTo(cp2.getCreated()))
                .map(CategoriePrix::getPrix)
                .orElse(this.getPrixDefaut());
    }

    @Transient
    public BigDecimal getPrixByDateTime(LocalDateTime dateTime) {
        if (categoriePrix == null || categoriePrix.isEmpty() || dateTime == null) {
            return null;
        }
        return categoriePrix.stream()
                .filter(cp -> cp.getCreated().isBefore(dateTime) || cp.getCreated().isEqual(dateTime))
                .max((cp1, cp2) -> cp1.getCreated().compareTo(cp2.getCreated()))
                .map(CategoriePrix::getPrix)
                .orElse(this.getPrixDefaut());
    }
}
