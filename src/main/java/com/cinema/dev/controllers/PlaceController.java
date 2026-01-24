package com.cinema.dev.controllers;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.cinema.dev.forms.PlaceForm;
import com.cinema.dev.models.CategoriePlace;
import com.cinema.dev.models.Place;
import com.cinema.dev.models.Salle;
import com.cinema.dev.repositories.CategoriePlaceRepository;
import com.cinema.dev.repositories.PlaceRepository;
import com.cinema.dev.repositories.SalleRepository;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/places")
@RequiredArgsConstructor
public class PlaceController {

    private final PlaceRepository placeRepository;
    private final SalleRepository salleRepository;
    private final CategoriePlaceRepository categoriePlaceRepository;

    @GetMapping("/liste")
    public String getListe(Model model) {
        List<Place> places = placeRepository.findAll();
        model.addAttribute("places", places);
        model.addAttribute("content", "pages/places/place-liste");
        return "admin-layout";
    }

    @GetMapping("/saisie")
    public String getSaisie(Model model) {
        List<Salle> salles = salleRepository.findAll();
        List<CategoriePlace> categoriesPlace = categoriePlaceRepository.findAll();

        model.addAttribute("salles", salles);
        model.addAttribute("categoriesPlace", categoriesPlace);
        model.addAttribute("content", "pages/places/place-saisie");
        return "admin-layout";
    }

    @PostMapping("/saisie")
    public String postSaisie(@ModelAttribute PlaceForm form,
            RedirectAttributes redirectAttributes) {
        try {
            form.control();

            Salle salle = salleRepository.findById(form.getSalleId())
                    .orElseThrow(() -> new Exception("Salle introuvable"));

            CategoriePlace categoriePlace = categoriePlaceRepository.findById(form.getIdCategoriePlace())
                    .orElseThrow(() -> new Exception("Catégorie de place introuvable"));

            // Récupérer le dernier numéro de place pour cette salle
            List<Place> placesExistantes = placeRepository.findBySalle(salle);
            int dernierNumero = placesExistantes.stream()
                    .mapToInt(Place::getNumero)
                    .max()
                    .orElse(0);

            // Créer les places selon la quantité demandée
            for (int i = 1; i <= form.getQuantite(); i++) {
                Place place = Place.builder()
                        .numero(dernierNumero + i)
                        .categoriePlace(categoriePlace)
                        .salle(salle)
                        .build();
                placeRepository.save(place);
            }

            redirectAttributes.addFlashAttribute("toastMessage",
                    form.getQuantite() + " place(s) créée(s) avec succès !");
            redirectAttributes.addFlashAttribute("toastType", "success");
            return "redirect:/places/liste";

        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("toastMessage", e.getMessage());
            redirectAttributes.addFlashAttribute("toastType", "error");
            return "redirect:/places/saisie";
        }
    }
}
