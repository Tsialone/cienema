package com.cinema.dev.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.cinema.dev.repositories.MouvementCaisseRepository;

@Entity
@Getter
@Setter
@ToString
@SuperBuilder
@NoArgsConstructor
@Table(name = "mouvement_caisse")
public class MouvementCaisse {

    @Id
    @Column(name = "id_mc", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idMc;

    @Column(name = "debit", nullable = false)
    private BigDecimal debit;

    @Column(name = "credit", nullable = false)
    private BigDecimal credit;

    @Column(name = "created", nullable = false)
    private LocalDateTime created;

    @ManyToOne
    @JoinColumn(name = "id_caisse", nullable = false)
    private Caisse caisse;

    @Transient
    public final String str = "MC";

    @Transient
    public String getStrId() {
        return str + idMc;
    }

    @Transient
    public  MouvementCaisse save (MouvementCaisseRepository mouvementCaisseRepository ) {
        this.setCreated(LocalDateTime.now());
        return mouvementCaisseRepository.save(this);

    }

}
