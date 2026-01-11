package com.cinema.dev.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.time.LocalTime;
import java.util.List;

import com.cinema.dev.repositories.FilmRepository;

@Entity
@Getter
@Setter
@ToString
@SuperBuilder
@NoArgsConstructor
@Table(name = "film")
public class Film {

    @Id
    @Column(name = "id_film", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idFilm;

    @Column(name = "nom", nullable = false)
    private String nom;

    @Column(name = "duree", nullable = false)
    private LocalTime duree;

    @Transient
    public static List<Film> getAll(FilmRepository filmRepository) {
        return filmRepository.findAll();
    }
}
