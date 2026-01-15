package com.cinema.dev.forms;

import lombok.Data;

@Data
public class ClientForm {
    private String nom;
    private Long idGenre;

    public void control() throws Exception {
        if (nom == null || nom.trim().isEmpty()) {
            throw new Exception("Le nom du client est obligatoire.");
        }
        if (idGenre == null) {
            throw new Exception("Le genre est obligatoire.");
        }
    }
}
