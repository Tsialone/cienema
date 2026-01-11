package com.cinema.dev.models;

import java.util.List;

import com.cinema.dev.repositories.GenreRepository;

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
@Table(name = "genre")
public class Genre {

    @Id
    @Column(name = "id_genre", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idGenre;

    @Column(name = "libelle", length = 50)
    private String libelle;

    @OneToMany(mappedBy = "genre")
    private List<Client> clients;

    @Transient
    public static List<Genre> getAll(GenreRepository genreRepository) {
        return genreRepository.findAll();
    }

    @Transient
    public final String str = "GEN";

    @Transient
    public String getStrId() {
        return str + idGenre;
    }
}
