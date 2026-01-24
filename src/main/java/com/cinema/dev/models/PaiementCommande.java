package com.cinema.dev.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
@ToString
@SuperBuilder
@NoArgsConstructor
@Table(name = "paiement_commande")
public class PaiementCommande {

    @Id
    @Column(name = "id_pc", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPc;

    @Column(name = "montant")
    private BigDecimal montant;

    @ManyToOne
    @JoinColumn(name = "id_cd", nullable = false)
    private CommandeDetail commandeDetail;

}
