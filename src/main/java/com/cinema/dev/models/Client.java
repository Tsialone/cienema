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

    @Column(name = "nom", nullable = false)
    private String nom;

    @ManyToOne
    @JoinColumn(name = "id_categorie", nullable = false)
    private Categorie categorie;

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
