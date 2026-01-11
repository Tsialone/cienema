package com.cinema.dev.models;

import com.cinema.dev.repositories.UtilisateurRepository;

import jakarta.persistence.*;
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
@Table(name = "utilisateur")
public class Utilisateur {

    @Id
    @Column(name = "id_utilisateur", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idUtilisateur;

    @Column(name = "nom")
    private String nom;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "passe")
    private String passe;

    @OneToOne(mappedBy = "utilisateur")
    private Client client;

    @Transient
    public Utilisateur signin(UtilisateurRepository utilisateurRepository) throws Exception {
        Utilisateur user = utilisateurRepository.findByEmail(email);
        if (user != null)
            throw new Exception("Utilisateur existe deja");
        utilisateurRepository.save(this);
        return user;

    }

    @Transient
    public static Utilisateur login(UtilisateurRepository utilisateurRepository, String email, String passe)
            throws Exception {
        if (email == null || email.isEmpty())
            throw new Exception("Email requis");
        if (passe == null || passe.isEmpty())
            throw new Exception("Mot de passe requis");
        Utilisateur user = utilisateurRepository.findByEmail(email);
        if (user == null)
            throw new Exception("Utilisateur non trouve");
        if (!user.getPasse().equals(passe))
            throw new Exception("Mot de passe incorrect");

        return user;
    }

}
