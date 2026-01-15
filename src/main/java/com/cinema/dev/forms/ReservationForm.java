package com.cinema.dev.forms;

import lombok.Data;
import java.util.List;

@Data
public class ReservationForm {

    private Long idFilm;
    private Long idSalle;
    private String dateSeance;
    private String heure;
    private Long idSeance;
    private Long idClient;
    private List<Long> idPlaces;

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
        if (idPlaces == null || idPlaces.isEmpty()) {
            throw new Exception("Au moins une place doit être sélectionnée.");
        }
    }
}
