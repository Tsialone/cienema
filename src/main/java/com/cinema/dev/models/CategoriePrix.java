package com.cinema.dev.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@ToString
@SuperBuilder
@NoArgsConstructor
@Table(name = "categorie_prix")
@IdClass(CategoriePrixId.class)
public class CategoriePrix {

    @Id
    @ManyToOne
    @JoinColumn(name = "id_cp", nullable = false)
    private CategoriePlace categoriePlace;

    @Id
    @ManyToOne
    @JoinColumn(name = "id_pc", nullable = false)
    private PrixC prixC;

    @Column(name = "prix", nullable = false)
    private BigDecimal prix;

    @Column(name = "created", nullable = false)
    private LocalDateTime created;
}


