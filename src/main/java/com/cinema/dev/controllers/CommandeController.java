package com.cinema.dev.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import lombok.RequiredArgsConstructor;

import com.cinema.dev.forms.CommandeForm;
import com.cinema.dev.models.Commande;
import com.cinema.dev.models.CommandeDetail;
import com.cinema.dev.models.CommandeDetailKey;
import com.cinema.dev.models.Diffusion;
import com.cinema.dev.models.Pub;
import com.cinema.dev.models.Societe;
import com.cinema.dev.repositories.CommandeDetailRepository;
import com.cinema.dev.repositories.CommandeRepository;
import com.cinema.dev.repositories.DiffusionRepository;
import com.cinema.dev.repositories.PubRepository;
import com.cinema.dev.repositories.SeanceRepository;
import com.cinema.dev.repositories.SocieteRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/commandes")
@RequiredArgsConstructor
public class CommandeController {

    private final SocieteRepository societeRepository;
    private final DiffusionRepository diffusionRepository;
    private final SeanceRepository seanceRepository;
    private final PubRepository pubRepository;
    private final CommandeRepository commandeRepository;
    private final CommandeDetailRepository commandeDetailRepository;

    @GetMapping("/liste")
    public String list(@RequestParam(name = "idSociete", required = false) Long idSociete, Model model) {
        List<Commande> commandes;

        if (idSociete != null) {
            System.out.println("idSociete filtre: " + idSociete);
            commandes = commandeRepository.findBySocieteIdSociete(idSociete);
        } else {
            commandes = commandeRepository.findAll();
        }

        model.addAttribute("commandes", commandes);
        model.addAttribute("content", "pages/commandes/commande-liste");
        model.addAttribute("filterIdSociete", idSociete);
        return "admin-layout";
    }

    @GetMapping("/saisie")
    public String getNew(@RequestParam(name = "id", required = true) Long idSociete, Model model) {
        Societe societe = societeRepository.findById(idSociete).orElse(null);
        if (societe == null) {
            model.addAttribute("toastMessage", "Société non trouvée");
            model.addAttribute("toastType", "error");
            return "redirect:/societes/liste";
        }

        // diffusions related to this societe (pub belongs to societe)
        List<Diffusion> diffusions = new ArrayList<>();
        for (Diffusion d : diffusionRepository.findAll()) {
            if (d.getPub() != null && d.getPub().getSociete() != null && d.getPub().getSociete().getIdSociete().equals(idSociete)) {
                diffusions.add(d);
            }
        }

        model.addAttribute("societe", societe);
        model.addAttribute("diffusions", diffusions);
        model.addAttribute("seances", seanceRepository.findAll());
        // filter pubs for this societe
        List<Pub> pubsForSociete = new ArrayList<>();
        for (Pub p : pubRepository.findAll()) {
            if (p.getSociete() != null && p.getSociete().getIdSociete().equals(idSociete)) {
                pubsForSociete.add(p);
            }
        }
        model.addAttribute("pubs", pubsForSociete);
        model.addAttribute("content", "pages/commandes/commande-saisie");
        model.addAttribute("commandeForm", new CommandeForm());

        return "admin-layout";
    }

    @PostMapping("/saisie")
    public String postNew(CommandeForm form, RedirectAttributes rd) {
        try {
            Societe societe = societeRepository.findById(form.getIdSociete()).orElseThrow(() -> new Exception("Société non trouvée"));
            List<CommandeDetail> details =   Commande.saveByForm(form, societeRepository, commandeRepository, commandeDetailRepository, diffusionRepository, seanceRepository, pubRepository);
            
            

            rd.addFlashAttribute("toastMessage", "Commande créée");
            rd.addFlashAttribute("toastType", "success");
            return "redirect:/societes/liste";
        } catch (Exception e) {
            rd.addFlashAttribute("toastMessage", e.getMessage());
            rd.addFlashAttribute("toastType", "error");
            return "redirect:/societes/liste";
        }
    }

}
