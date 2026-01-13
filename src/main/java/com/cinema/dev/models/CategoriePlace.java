package com.cinema.dev.models;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
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

    @Column(name = "prix", nullable = false)
    private BigDecimal prix;

    @OneToMany(mappedBy = "categoriePlace")
    private List<Place> places = new ArrayList<>();

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
}
