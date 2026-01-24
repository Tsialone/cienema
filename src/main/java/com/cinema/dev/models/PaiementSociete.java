package com.cinema.dev.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.cinema.dev.repositories.PaiementSocieteRepository;

@Entity
@Getter
@Setter
@ToString
@SuperBuilder
@NoArgsConstructor
@Table(name = "paiement_societe")
public class PaiementSociete {

    @Id
    @Column(name = "id_ps", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPs;

    @Column(name = "created", nullable = false)
    private LocalDateTime created;

    @Column(name = "montant")
    private BigDecimal montant;

    @ManyToOne
    @JoinColumn(name = "id_societe", nullable = false)
    private Societe societe;

    @Transient
    public final String str = "PS";

    @Transient
    public String getStrId() {
        return str + idPs;
    }

    @Transient
    public static PaiementSociete save(PaiementSociete paiementSociete, PaiementSocieteRepository paiementSocieteRepository) {
        return paiementSocieteRepository.save(paiementSociete);
    }
}
