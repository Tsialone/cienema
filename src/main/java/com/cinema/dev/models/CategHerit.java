package com.cinema.dev.models;

import java.math.BigDecimal;
import java.util.Optional;

import com.cinema.dev.repositories.CategHeritRepository;

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
@Table(name = "categ_herit")
public class CategHerit {

    @Id
    @Column(name = "id_ch", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCh;

    @Column(name = "pourcentage", nullable = false)
    private BigDecimal pourcentage;

    @ManyToOne
    @JoinColumn(name = "id_categorie", nullable = false)
    private Categorie categorie;

    @ManyToOne
    @JoinColumn(name = "id_categorie_1")
    private Categorie parent;

    @Transient
    public final String str = "CH";

    @Transient
    public String getStrId() {
        return str + idCh;
    }

    @Transient
    public static Optional<CategHerit> getByIdCategorie(Long idCategorie, CategHeritRepository categHeritRepository) {
        return categHeritRepository.findByIdCategorie(idCategorie);
    }
}
