package com.cinema.dev.forms;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class TicketForm {

    private Long blocId;

    // private Integer nbrPlace;

    private LocalDateTime datePrevue;

    private List<Long> numeroPlace;

    private Long clientId;

    private Long idFilm; // Add this line

    public void control() throws Exception {
        // if (nbrPlace == null || nbrPlace <= 0) {
        //     throw new Exception("Le nombre de places doit être supérieur à zéro.");
        // }
        if (numeroPlace == null || numeroPlace.isEmpty()) {
            throw new Exception("La liste des numéros de places ne peut pas être nulle ou vide.");
        }
        if (datePrevue == null || datePrevue.isBefore(LocalDateTime.now())) {
            throw new Exception("La date prévue doit être une date future.");
        }
        if (blocId == null) {
            throw new Exception("L'ID du bloc ne peut pas être nul.");
        }
        if (clientId == null) {
            throw new Exception("L'ID du client ne peut pas être nul.");
        }
        if (numeroPlace == null || numeroPlace.isEmpty()) {
            throw new Exception("La liste des numéros de places ne peut pas être nulle ou vide.");
        }
      
    }

}