package com.cinema.dev.controllers;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.cinema.dev.forms.PaiementSocieteForm;
import com.cinema.dev.models.PaiementSociete;
import com.cinema.dev.models.Societe;
import com.cinema.dev.repositories.DiffusionRepository;
import com.cinema.dev.repositories.PaiementSocieteRepository;
import com.cinema.dev.repositories.SocieteRepository;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/paiements-societe")
@RequiredArgsConstructor
public class PaiementSocieteController {

    private final PaiementSocieteRepository paiementSocieteRepository;
    private final SocieteRepository societeRepository;
    private final PaiementSocieteRepository paiementRepository;
    private final DiffusionRepository diffusionRepository;
    @GetMapping("/liste")
    public String getListe(Model model) {
        List<PaiementSociete> paiements = paiementSocieteRepository.findAll();
        model.addAttribute("paiements", paiements);
        model.addAttribute("content", "pages/paiements-societe/paiement-societe-liste");
        return "admin-layout";
    }

    @GetMapping("/saisie")
    public String getSaisie(Model model) {
        List<Societe> societes = societeRepository.findAll();
        
        model.addAttribute("societes", societes);
        model.addAttribute("content", "pages/paiements-societe/paiement-societe-saisie");
        return "admin-layout";
    }

    @PostMapping("/saisie")
    public String postSaisie(@ModelAttribute PaiementSocieteForm form,
            RedirectAttributes redirectAttributes) {
        try {
            form.control();

            Societe societe = societeRepository.findById(form.getIdSociete())
                    .orElseThrow(() -> new Exception("Société introuvable"));

            Double restPayer = societe.getRestePayeByDate(form.getCreated().toLocalDate(), diffusionRepository);
            if (restPayer < form.getMontant().doubleValue()) {
                throw new Exception("Le montant du paiement dépasse le reste à payer de la société. Reste: " + restPayer);
            }

            PaiementSociete paiement = PaiementSociete.builder()
                    .societe(societe)
                    .montant(form.getMontant())
                    .created(form.getCreated())
                    .build();
            
            paiementSocieteRepository.save(paiement);

            redirectAttributes.addFlashAttribute("toastMessage", "Paiement enregistré avec succès !");
            redirectAttributes.addFlashAttribute("toastType", "success");
            return "redirect:/paiements-societe/liste";

        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("toastMessage", e.getMessage());
            redirectAttributes.addFlashAttribute("toastType", "error");
            return "redirect:/paiements-societe/saisie";
        }
    }
}
