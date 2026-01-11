package com.cinema.dev.models;

import java.util.List;

import com.cinema.dev.repositories.ClientRepository;

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
@Table(name = "client")
public class Client {

    @Id
    @Column(name = "id_client", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idClient;

    @ManyToOne
    @JoinColumn(name = "id_genre", nullable = false)
    private Genre genre;

    @OneToOne
    @JoinColumn(name = "id_utilisateur", nullable = false, unique = true)
    private Utilisateur utilisateur;

    @Transient
    public static List<Client> getAll (ClientRepository clientRepository) {
        return clientRepository.findAll();
    }

    @Transient
    public final String str = "CLI";

    @Transient
    public String getStrId() {
        return str + idClient;
    }
}
