package com.cinema.dev.models;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import com.cinema.dev.repositories.CategHeritRepository;
import com.cinema.dev.repositories.CategorieRepository;
import com.cinema.dev.repositories.RemiseRepository;

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
@Table(name = "categorie")
public class Categorie {

    @Id
    @Column(name = "id_categorie", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCategorie;

    @Column(name = "libelle", length = 50)
    private String libelle;

    

    @OneToMany(mappedBy = "categorie")
    private List<Client> clients;

    @OneToMany(mappedBy = "categorie")
    private List<Remise> remises;

    @Transient
    public static List<Categorie> getAll(CategorieRepository categorieRepository) {
        return categorieRepository.findAll();
    }

    @Transient
    public final String str = "CAT";

    @Transient
    public String getStrId() {
        return str + idCategorie;
    }

    @Transient
    public Categorie getParent(CategHeritRepository categHeritRepository) {
        Categorie parent = CategHerit.getByIdCategorie(idCategorie, categHeritRepository).orElse(null).getCategorie();
        if (parent == null) {
            return null;
        }
        return parent;
    }

    @Transient
    public Double getPourcentage(CategHeritRepository categHeritRepository) {
        Double resp = 0.0;
        Categorie parent = this.getParent(categHeritRepository);

        if (parent != null) {
            CategHerit categHerit = CategHerit.getByIdCategorie(idCategorie, categHeritRepository).orElse(null);
            if (categHerit != null) {
                resp = categHerit.getPourcentage().doubleValue();
            }

        }

        return resp;
    }
    @Transient
    public Double  getRealPrix ( Long idClient , LocalDateTime date  ,CategHeritRepository categHeritRepository , RemiseRepository remiseRepository){
        Double montant = 0.0;
        Double pourcentage = this.getPourcentage(categHeritRepository);
        Categorie parent = this.getParent(categHeritRepository);
        if (parent != null) {
            Remise remise = Remise.getRemiseByIdCategPlaceClientByDateTime(parent.getIdCategorie(), idClient , date  ,remiseRepository).orElse(null);
            if (remise != null){
                montant+=  montant / pourcentage;
            }
        }
        return montant;

    }

}