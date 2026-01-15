package com.cinema.dev.views;

import java.time.LocalDate;
import java.time.LocalTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SeanceDetail {
    private String idSeance;
    private String debut;
    private String idSalle;
    private String salleNom;
    private String idFilm;
    private String filmNom;
    private String filmDuree;
    private String heure;

    private Double maxRevenu;
}
