package com.cinema.dev.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@ToString
@SuperBuilder
@NoArgsConstructor
@Table(name = "seance")
public class Seance {

    @Id
    @Column(name = "id_seance", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idSeance;

    @Column(name = "date_time_debut", nullable = false)
    private LocalDateTime dateTimeDebut;

    @Column(name = "id_salle", nullable = false)
    private Long idSalle;

    @Column(name = "id_film", nullable = false)
    private Long idFilm;

}
