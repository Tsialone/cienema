package com.cinema.dev.models;

import jakarta.persistence.*;
import jakarta.transaction.Transactional;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.cinema.dev.forms.CommandeForm;
import com.cinema.dev.models.CommandeDetail;
import com.cinema.dev.models.CommandeDetailKey;
import com.cinema.dev.models.Diffusion;
import com.cinema.dev.models.Seance;
import com.cinema.dev.models.Pub;
import com.cinema.dev.models.PaiementCommande;
import java.math.BigDecimal;
import com.cinema.dev.repositories.CommandeDetailRepository;
import com.cinema.dev.repositories.CommandeRepository;
import com.cinema.dev.repositories.SocieteRepository;
import com.cinema.dev.repositories.DiffusionRepository;
import com.cinema.dev.repositories.SeanceRepository;
import com.cinema.dev.repositories.PubRepository;

@Entity
@Getter
@Setter
@ToString
@SuperBuilder
@NoArgsConstructor
@Table(name = "commande")
public class Commande {

    @Id
    @Column(name = "id_commande", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCommande;

    @Column(name = "creted", nullable = false)
    private LocalDateTime creted;

    @ManyToOne
    @JoinColumn(name = "id_societe", nullable = false)
    private Societe societe;

    @OneToMany(mappedBy = "commande", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CommandeDetail> details = new ArrayList<>();

   

    @Transient
    public final String str = "CMD";

    @Transient
    public String getStrId() {
        return str + idCommande;
    }

    @Transient
    public Double restePayer (){
        Double totalPaiements = getTotalPaiements();
        Double totalCommande = 0.0;
        System.out.println("xxxxxxxxxxxxxxxxxx: "   + details.size());
        for (CommandeDetail detail : details) {
            Diffusion diffusion = detail.getDiffusion();
            if (diffusion != null) {
                Seance seance = diffusion.getSeance();
                if (seance != null) {
                    totalCommande +=seance.getPrixPub().doubleValue();
                }
            }
        }
        return   totalCommande   - totalPaiements;
    }
    @Transient
    public Double getTotalPaiements() {
        Double total = 0.0;
        for (CommandeDetail commandeDetail : details) {
            total+= commandeDetail.getTotalPaye();
        }
        // if (paiements != null) {
        //     for (PaiementCommande p : paiements) {
        //         if (p != null && p.getMontant() != null) {
        //             total = total.add(p.getMontant());
        //         }
        //     }
        // }
        return total;
    }


    @Transactional
    public static  List<CommandeDetail> saveByForm(CommandeForm commandeForm,
                                           SocieteRepository societeRepository,
                                           CommandeRepository commandeRepository,
                                           CommandeDetailRepository commandeDetailRepository,
                                        DiffusionRepository diffusionRepository,
                                           SeanceRepository seanceRepository,
                                           PubRepository pubRepository) {

        List<CommandeDetail> resp = new ArrayList<>();

        Societe societe = societeRepository.findById(commandeForm.getIdSociete()).orElse(null);
        if (societe == null) {
            throw new IllegalArgumentException("Société non trouvée");
        }

        Commande commande = new Commande();
        commande.setCreted(LocalDateTime.now());
        commande.setSociete(societe);
        commande = commandeRepository.save(commande);

        if (commandeForm.getDiffusionIds() != null) {
            for (Long idDiff : commandeForm.getDiffusionIds()) {
                if (idDiff == null) continue;
                Diffusion diffusion = diffusionRepository.findById(idDiff).orElse(null);
                if (diffusion == null) continue;

                CommandeDetail detail = new CommandeDetail();
                detail.setCommande(commande);
                detail.setDiffusion(diffusion);
                commandeDetailRepository.save(detail);
                resp.add(detail);
            }
        }

        // 2) new diffusions created from paired seanceIds / pubIds
        if (commandeForm.getSeanceIds() != null && commandeForm.getPubIds() != null) {
            int n = Math.min(commandeForm.getSeanceIds().size(), commandeForm.getPubIds().size());
            for (int i = 0; i < n; i++) {
                Long idSeance = commandeForm.getSeanceIds().get(i);
                Long idPub = commandeForm.getPubIds().get(i);
                if (idSeance == null || idPub == null) continue;

                Seance seance = seanceRepository.findById(idSeance).orElse(null);
                Pub pub = pubRepository.findById(idPub).orElse(null);
                if (seance == null || pub == null) continue;

                Diffusion diffusion = Diffusion.builder()
                        .seance(seance)
                        .pub(pub)
                        .build();
                diffusion = diffusionRepository.save(diffusion);

                CommandeDetail detail = new CommandeDetail();
                detail.setCommande(commande);
                detail.setDiffusion(diffusion);
                commandeDetailRepository.save(detail);
                resp.add(detail);
            }
        }

        return resp;
    }
        

        

    @Transient
    public Commande save  (CommandeRepository commandeRepository) {
        return commandeRepository.save(this);
    }

}
