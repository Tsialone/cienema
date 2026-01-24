package com.cinema.dev.controllers;

import java.util.List;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.cinema.dev.forms.DiffusionForm;
import com.cinema.dev.models.Diffusion;
import com.cinema.dev.models.Pub;
import com.cinema.dev.models.Seance;
import com.cinema.dev.repositories.DiffusionRepository;
import com.cinema.dev.repositories.PubRepository;
import com.cinema.dev.repositories.SeanceRepository;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/diffusions")
@RequiredArgsConstructor
public class DiffusionController {

    private final DiffusionRepository diffusionRepository;
    private final SeanceRepository seanceRepository;
    private final PubRepository pubRepository;

    @GetMapping("/liste")
    public String getListe(@RequestParam(required = false) String dateFiltre, Model model) {
        List<Diffusion> diffusions = diffusionRepository.findAll();
        
        // Filtrer par date si fourni
        Double totalChiffre = 0.0;
        if (dateFiltre != null && !dateFiltre.isEmpty()) {
            LocalDate date = LocalDate.parse(dateFiltre);
            totalChiffre = Diffusion.getCa(date, diffusionRepository);
        }
        
       

        
        model.addAttribute("diffusions", diffusions);
        model.addAttribute("totalChiffre", totalChiffre);
        model.addAttribute("dateFiltre", dateFiltre);
        model.addAttribute("content", "pages/diffusions/diffusion-liste");
        return "admin-layout";
    }

    @GetMapping("/saisie")
    public String getSaisie(Model model) {
        List<Seance> seances = seanceRepository.findAll();
        List<Pub> pubs = pubRepository.findAll();

        model.addAttribute("seances", seances);
        model.addAttribute("pubs", pubs);
        model.addAttribute("content", "pages/diffusions/diffusion-saisie");
        return "admin-layout";
    }

    @PostMapping("/saisie")
    public String postSaisie(@ModelAttribute DiffusionForm form,
            RedirectAttributes redirectAttributes) {
        try {
            form.control();

            Seance seance = seanceRepository.findById(form.getIdSeance())
                    .orElseThrow(() -> new Exception("Séance introuvable"));

            Pub pub = pubRepository.findById(form.getIdPub())
                    .orElseThrow(() -> new Exception("Publicité introuvable"));

            for (int i = 0; i < form.getNbrPub(); i++) {
                Diffusion diffusion = Diffusion.builder()
                        .seance(seance)
                        .pub(pub)
                        .build();
                diffusionRepository.save(diffusion);
            }

            redirectAttributes.addFlashAttribute("toastMessage",
                    form.getNbrPub() + " diffusion(s) créée(s) avec succès !");
            redirectAttributes.addFlashAttribute("toastType", "success");
            return "redirect:/diffusions/liste";

        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("toastMessage", e.getMessage());
            redirectAttributes.addFlashAttribute("toastType", "error");
            return "redirect:/diffusions/saisie";
        }
    }
}
