package com.cinema.dev.models;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;

import com.cinema.dev.repositories.CaisseRepository;

@Entity
@Getter
@Setter
@ToString
@SuperBuilder
@NoArgsConstructor
@Table(name = "caisse")
public class Caisse {

    @Id
    @Column(name = "id_caisse", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCaisse;

    @Column(name = "nom")
    private String nom;

    @OneToMany(mappedBy = "caisse", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<MouvementCaisse> mouvements = new ArrayList<>();

    @Transient
    public final String str = "CAI";

    @Transient
    public String getStrId() {
        return str + idCaisse;
    }

    @Transient
    public static List<Caisse> getAll(CaisseRepository caisseRepository) {
        return caisseRepository.findAll();
    }

    @Transient
    public static Caisse findByStrId(CaisseRepository caisseRepository, String strId) {
        for (Caisse caisse : getAll(caisseRepository)) {
            if (caisse.getStrId().equals(strId)) {
                return caisse;
            }
        }
        return null;
    }

    

}
