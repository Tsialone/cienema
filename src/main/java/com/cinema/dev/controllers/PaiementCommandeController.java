package com.cinema.dev.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import lombok.RequiredArgsConstructor;

import com.cinema.dev.models.Commande;
import com.cinema.dev.models.CommandeDetail;
import com.cinema.dev.models.Diffusion;
import com.cinema.dev.models.PaiementCommande;
import com.cinema.dev.repositories.CommandeRepository;
import com.cinema.dev.repositories.CommandeDetailRepository;
import com.cinema.dev.repositories.PaiementCommandeRepository;

import java.math.BigDecimal;
import java.util.List;

@Controller
@RequestMapping("/paiement-commande")
@RequiredArgsConstructor
public class PaiementCommandeController {

    private final CommandeRepository commandeRepository;
    private final PaiementCommandeRepository paiementCommandeRepository;
    private final CommandeDetailRepository commandeDetailRepository;

    @GetMapping("/saisie")
    public String getSaisie(@RequestParam(name = "idCd", required = false) Long idCd,
            @RequestParam(name = "idCommande", required = false) Long idCommande,
            Model model) {
        CommandeDetail detail = null;
        if (idCd != null) {
            detail = commandeDetailRepository.findById(idCd).orElse(null);
        } else if (idCommande != null) {
            Commande commande = commandeRepository.findById(idCommande).orElse(null);
            if (commande != null && commande.getDetails() != null && !commande.getDetails().isEmpty()) {
                detail = commande.getDetails().get(0);
            }
        }

        if (detail == null) {
            model.addAttribute("toastMessage", "Détail commande non trouvé");
            model.addAttribute("toastType", "error");
            return "redirect:/commandes/liste";
        }

        model.addAttribute("detail", detail);
        String montant = "";
        if (detail.getDiffusion() != null && detail.getDiffusion().getSeance() != null
                && detail.getDiffusion().getSeance().getPrixPub() != null) {
            montant = detail.getDiffusion().getSeance().getPrixPub().toPlainString();
        }
        model.addAttribute("montant", montant);
        model.addAttribute("content", "pages/commandes/paiement-saisie");
        return "admin-layout";
    }

    @PostMapping("/saisie")
    public String postSaisie(
            @RequestParam(name = "idCommande", required = false) Long idCommande,
            @RequestParam(name = "montant", required = true) String montantStr,
            RedirectAttributes rd) {
        try {
            BigDecimal montant = new BigDecimal(montantStr.replaceAll("[^0-9\\.-]", ""));
            if (idCommande != null) {
                System.out.println("cccccccccccccccccccccccccccc");
                Commande commande = commandeRepository.findById(idCommande)
                        .orElseThrow(() -> new Exception("Commande non trouvée"));
                List<CommandeDetail> details = commande.getDetails();
                double restePayer = commande.restePayer();
                System.out.println("pourcentage: " + " montant: " + montant + " restePayer: " + restePayer);
                if (restePayer < montant.doubleValue()) {
                    throw new Exception("Le montant du paiement dépasse le reste à payer de la commande. Reste: "
                            + restePayer);
                }
                double pourcentage = (montant.doubleValue() / restePayer);

                for (CommandeDetail detail : details) {
                    Diffusion diffusion = detail.getDiffusion();
                    double montantDiffusion = diffusion.getSeance().getPrixPub().doubleValue() * (pourcentage);
                    PaiementCommande paiement = new PaiementCommande();
                    paiement.setCommandeDetail(detail);
                    paiement.setMontant(BigDecimal.valueOf(montantDiffusion));
                    paiement = paiementCommandeRepository.save(paiement);
                }
            } else {
                throw new Exception("Paramètre idCd ou idCommande requis");
            }

            rd.addFlashAttribute("toastMessage", "Paiement enregistré");
            rd.addFlashAttribute("toastType", "success");
            return "redirect:/commandes/liste";
        } catch (Exception e) {
            rd.addFlashAttribute("toastMessage", e.getMessage());
            rd.addFlashAttribute("toastType", "error");
            return "redirect:/commandes/liste";
        }
    }

}
