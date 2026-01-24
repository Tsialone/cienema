package com.cinema.dev.forms;

import lombok.Data;

@Data
public class DiffusionForm {
    private Long idSeance;
    private Long idPub;
    private Integer nbrPub;

    public void control() throws Exception {
        if (idSeance == null) {
            throw new Exception("La séance est obligatoire.");
        }
        if (idPub == null) {
            throw new Exception("La publicité est obligatoire.");
        }
        if (nbrPub == null || nbrPub < 1) {
            throw new Exception("Le nombre de diffusions doit être supérieur à 0.");
        }
    }
}
