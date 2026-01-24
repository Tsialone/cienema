package com.cinema.dev.forms;

import lombok.Data;

@Data
public class PlaceForm {
    private Long salleId;
    private Long idCategoriePlace;
    private Integer quantite;
    private String nom;

    public void control() throws Exception {
        if (salleId == null) {
            throw new Exception("La salle est obligatoire.");
        }
        if (idCategoriePlace == null) {
            throw new Exception("La catégorie de place est obligatoire.");
        }
        if (quantite == null || quantite <= 0) {
            throw new Exception("La quantité doit être supérieure à 0.");
        }
    }
}
