package com.cinema.dev.forms;

import lombok.Data;

import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Data
public class ReservationForm {

    private Long idFilm;
    private Long idSalle;
    private String dateSeance;
    private String heure;
    private Long idSeance;
    private Long idClient;
    private Long idCp;
    private Integer nombrePlaces;

    public LocalDateTime getDateTimeSeance() {
        LocalTime time = LocalTime.parse(heure);
        LocalDate date = LocalDate.parse(dateSeance);
        return LocalDateTime.of(date, time);
    }

    // private List<Long> idPlaces;

    public void control() throws Exception {
        if (idSeance == null || idSeance <= 0) {
            throw new Exception("La séance est obligatoire." + idSeance);
        }
        if (idFilm == null || idFilm <= 0) {
            throw new Exception("Le film est obligatoire.");
        }
        if (idSalle == null || idSalle <= 0) {
            throw new Exception("La salle est obligatoire.");
        }
        if (dateSeance == null || dateSeance.isEmpty()) {
            throw new Exception("La date de séance est obligatoire.");
        }
        if (idClient == null || idClient <= 0) {
            throw new Exception("Le client est obligatoire.");
        }
        // if (idPlaces == null || idPlaces.isEmpty()) {
        // throw new Exception("Au moins une place doit être sélectionnée.");
        // }
        if (nombrePlaces == null || nombrePlaces <= 0) {
            throw new Exception("Le nombre de places doit être au moins 1.");
        }
    }
}
