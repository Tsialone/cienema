package com.cinema.dev.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;
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
@Table(name = "utilisateur_role")
@IdClass(UtilisateurRoleId.class)
public class UtilisateurRole {

    @Id
    @Column(name = "id_utilisateur", nullable = false)
    private Long idUtilisateur;

    @Id
    @Column(name = "id_role", nullable = false)
    private Long idRole;

}
