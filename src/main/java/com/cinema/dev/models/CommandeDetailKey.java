package com.cinema.dev.models;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Embeddable
@Getter
@Setter
@ToString
@NoArgsConstructor
public class CommandeDetailKey implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long idDiffusion;

    private Long idCommande;

}
