package com.cinema.dev.models;

import com.cinema.dev.repositories.PlaceRepository;

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
@Table(name = "place")
public class Place {

    @Id
    @Column(name = "id_place", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPlace;

    @Column(name = "numero", nullable = false)
    private Long numero;

    @ManyToOne
    @JoinColumn(name = "id_bloc", nullable = false)
    private Bloc bloc;

    @ManyToOne
    @JoinColumn(name = "id_salle", nullable = false)
    private Salle salle;

    @Transient
    public static Place getByNumeroPlace(PlaceRepository placeRepository, Long numero) {
        return placeRepository.findByNumero(numero);
    }

    @Transient
    public final String str = "PLC";

    @Transient
    public String getStrId() {
        return str + idPlace;
    }
}
