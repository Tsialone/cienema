package com.cinema.dev.forms;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.Data;

@Data
public class PaiementSocieteForm {
    private Long idSociete;
    private BigDecimal montant;
    private LocalDateTime created;

    public void control() throws Exception {
        if (idSociete == null) {
            throw new Exception("La société est obligatoire.");
        }
        if (montant == null || montant.compareTo(BigDecimal.ZERO) <= 0) {
            throw new Exception("Le montant doit être supérieur à 0.");
        }
        if (created == null) {
            throw new Exception("La date est obligatoire.");
        }
    }
}
