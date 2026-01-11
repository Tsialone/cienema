package com.cinema.dev.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.cinema.dev.repositories.BlocRepository;

@Entity
@Getter
@Setter
@ToString
@SuperBuilder
@NoArgsConstructor
@Table(name = "bloc")
public class Bloc {

    @Id
    @Column(name = "id_bloc", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idBloc;

    @Column(name = "libelle")
    private String libelle;

    @Column(name = "prix", nullable = false)
    private BigDecimal prix;

    // @ManyToOne
    // @JoinColumn(name = "id_salle", nullable = false)
    // private Salle salle;

    @OneToMany(mappedBy = "bloc", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Place> places = new ArrayList<>();

    @Transient
    public final String str = "BLC";

    @Transient
    public Salle getSalle() {
        if (places != null && !places.isEmpty()) {
            return places.get(0).getSalle();
        }
        return null;
    }

    @Transient
    public static List<Bloc> getAll(BlocRepository blocRepository) {
        return blocRepository.findAll();
    }

    @Transient
    public String getStrId() {
        return str + idBloc;
    }

}
