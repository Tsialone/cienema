package com.cinema.dev.models;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.cinema.dev.repositories.TicketRepository;

@Entity
@Getter
@Setter
@ToString
@SuperBuilder
@NoArgsConstructor
@Table(name = "salle")
public class Salle {

    @Id
    @Column(name = "id_salle", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idSalle;

    @Column(name = "nom")
    private String nom;

    @Column(name = "capacite", nullable = false)
    private Integer capacite;

    @Transient
    public final String str = "SAL";

    @Transient
    public String getStrId() {
        return str + idSalle;
    }

    @OneToMany(mappedBy = "salle", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<Place> places = new ArrayList<>();

    @OneToMany(mappedBy = "salle")
    private List<Seance> seances;

    @Transient
    public List<Place> getPlaceDispo(LocalDateTime date , TicketRepository ticketRepository) {

        List<Place> dispoPlaces = new ArrayList<>();
        System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxxxxx");
        for (Place place : this.places) {
            if (place.isDispo(date, ticketRepository)) {
                dispoPlaces.add(place);
                
            }
            
        }
        return  dispoPlaces;
    }

}
