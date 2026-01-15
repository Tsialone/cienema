package com.cinema.dev.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@ToString
@SuperBuilder
@NoArgsConstructor
@Table(name = "prix_c")
public class PrixC {

    @Id
    @Column(name = "id_pc", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPc;

    @Column(name = "created", nullable = false)
    private LocalDateTime created;

    @Column(name = "prix", nullable = false)
    private BigDecimal prix;

    @ManyToOne
    @JoinColumn(name = "id_cp", nullable = false)
    private CategoriePlace categoriePlace;
}
