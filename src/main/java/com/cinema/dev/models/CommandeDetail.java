package com.cinema.dev.models;

import java.util.ArrayList;
import java.util.List;

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
@Table(name = "commande_details")
public class CommandeDetail {

    @Id
    @Column(name = "id_cd", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCd;

    @OneToOne
    @JoinColumn(name = "id_diffusion", nullable = false)
    private Diffusion diffusion;

    @ManyToOne
    @JoinColumn(name = "id_commande", nullable = false)
    private Commande commande;

    @OneToMany(mappedBy = "commandeDetail", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PaiementCommande> paiementCommandes = new ArrayList<>();


    

    @Transient
    public Double getTotalPaye   (){
        Double  resp  = 0.0;
        for (PaiementCommande paiementCommande : paiementCommandes) {
            resp+= paiementCommande.getMontant().doubleValue();
        }

        return resp;
    }

}
