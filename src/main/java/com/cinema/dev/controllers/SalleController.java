package com.cinema.dev.controllers;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.cinema.dev.models.Salle;
import com.cinema.dev.repositories.SalleRepository;
import com.cinema.dev.repositories.SeanceRepository;
import com.cinema.dev.views.SalleDetail;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/salles")
@RequiredArgsConstructor
public class SalleController {

    private final SalleRepository salleRepository;
    private final SeanceRepository seanceRepository;

    @GetMapping("/liste")
    public String getListe(Model model,
            @RequestParam(required = false) LocalDate dateDebut,
            @RequestParam(required = false) String heure) {
        
        LocalTime timeFilter = null;
        LocalDateTime dateTimeFilter = null;
        if (heure != null && !heure.isEmpty()) {
            timeFilter = LocalTime.parse(heure);
        }
        if (dateDebut != null  ) {
            dateTimeFilter = LocalDateTime.of(dateDebut, LocalTime.MIDNIGHT);
        }

        if (dateDebut != null && timeFilter != null) {
            dateTimeFilter = LocalDateTime.of(dateDebut, timeFilter);
        }

        List<Salle> salles = salleRepository.findAll();
        List<SalleDetail> salleDetails = new ArrayList<>();

        for (Salle salle : salles) {
            SalleDetail detail = new SalleDetail();
            detail.setIdSalle(String.valueOf(salle.getIdSalle()));
            detail.setCapacite(String.valueOf(salle.getCapacite()));
            // System.out.println("dateTimeFilter: " + dateTimeFilter);
            detail.setRevenuMax(String.valueOf(salle.getMaxPrixPlace(dateTimeFilter)));
            salleDetails.add(detail);
        }

        model.addAttribute("salles", salleDetails);
        model.addAttribute("content", "pages/salles/salle-liste");
        return "admin-layout";
    }
}
