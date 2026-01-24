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
    public static Categorie getById(Long idCategorie, CategorieRepository categorieRepository) {
        Categorie categorie = categorieRepository.findById(idCategorie).orElse(null);
        return categorie;
    }

    @Transient
    public Categorie getParent(CategHeritRepository categHeritRepository) {
        CategHerit categHerit = CategHerit.getByIdCategorie(idCategorie, categHeritRepository).orElse(null);
        if (categHerit != null && categHerit.getParent() != null) {
            return categHerit.getParent();

        }
        return null;
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
    public Double getRemiseValByCategPlace(Double montantNormal, Long categorieClientId, Long categoriePlaceId,
            LocalDateTime date, CategHeritRepository categHeritRepository,
            RemiseRepository remiseRepository) {
        Categorie parent = this.getParent(categHeritRepository);
        Double pourcentage = 0.0;
        Remise remise = Remise
                .getRemiseByIdCategPlaceClientByDateTime(categoriePlaceId, categorieClientId, date,
                        remiseRepository)
                .orElse(null);

        // if (remise == null) {
        // System.out.println("remise is null for idcategPlace: " + categoriePlaceId + "
        // and idcategClient: "
        // + categorieClientId + " at date " + date);
        // }
        if (parent != null) {
            pourcentage = this.getPourcentage(categHeritRepository);

            System.out.println("applying percentage " + pourcentage + " for categorie " + this.getLibelle());
            System.out.println("idcategPlace: " + getStrId() + " parent: " + parent.getStrId());
            remise = Remise
                    .getRemiseByIdCategPlaceClientByDateTime(categoriePlaceId, parent.getIdCategorie(), date,
                            remiseRepository)
                    .orElse(null);

            if (remise != null) {
                System.out.println("rrrrrrrrrrrrrrrrrrrrr: " + categoriePlaceId);
                System.out.println("parent is 2>>>>>>>>>>>>>>>>: " + remise.getMontant().doubleValue() + " parent "
                        + parent.getStrId() + " enfant: " + this.getStrId());
            }

            // System.out.println("searching remise for parent categorieParent " +
            // parent.getIdCategorie() + " : " + remise + " idcategPlace: " +
            // categoriePlaceId);
            // System.out.println("ccccccccccccccccccccccc: " + remise);

        }
        // if (parent == null && remise != null) {
        //     System.out.println("remise native: " + remise.getMontant().doubleValue() + " for categorie "
        //             + this.getLibelle() + " categPlace: " + categoriePlaceId + " at date " + date);
        // }
        if (remise != null && pourcentage > 0) {
            System.out.println("atooo eeee: " + remise.getMontant().doubleValue());
            return remise.getMontant().doubleValue() * (pourcentage / 100);
        }

        // if (remise != null) {
        //     System.out.println("tayyyyyyyyyyyyyyy: " + remise.getMontant().doubleValue());

        // }
        return remise != null ? remise.getMontant().doubleValue() : null;

    }

}