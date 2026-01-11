package com.cinema.dev.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.cinema.dev.repositories.PaiementRepository;

@Entity
@Getter
@Setter
@ToString
@SuperBuilder
@NoArgsConstructor
@Table(name = "paiement")
public class Paiement {

    @Id
    @Column(name = "id_paiement", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPaiement;

    @Column(name = "created")
    private LocalDateTime created;

    @Column(name = "montant", nullable = false)
    private BigDecimal montant;

    @ManyToOne
    @JoinColumn(name = "id_ticket", nullable = false)
    private Ticket ticket;

    @ManyToOne
    @JoinColumn(name = "id_mc", nullable = false)
    private MouvementCaisse mouvementCaisse;

    @Transient
    public Paiement save (PaiementRepository paiementRepository , MouvementCaisse mouvementCaisse) {
        this.setMouvementCaisse(mouvementCaisse);
        return paiementRepository.save(this);
    }

}
