package com.cinema.dev.models;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import com.cinema.dev.repositories.RemiseRepository;

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
@Table(name = "remise")
public class Remise {

    @Id
    @Column(name = "id_remise", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idRemise;

    @Column(name = "montant", nullable = false)
    private BigDecimal montant;

    @ManyToOne
    @JoinColumn(name = "id_categorie", nullable = false)
    private Categorie categorie;

    @ManyToOne
    @JoinColumn(name = "id_cp", nullable = false)
    private CategoriePlace categoriePlace;

    @Transient
    public final String str = "REM";

    @Transient
    public String getStrId() {
        return str + idRemise;
    }

    @Transient
    public static Optional<Remise> getRemiseByIdCategPlaceClientByDateTime(
            Long idCategPlace, Long idCategClient, LocalDateTime dateTime, RemiseRepository remiseRepository) {
        return remiseRepository.findRemiseByIdCategPlaceClientByDateTime(idCategPlace, idCategClient, dateTime);
    }
}
