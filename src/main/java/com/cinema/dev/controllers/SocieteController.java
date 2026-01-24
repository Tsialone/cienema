package com.cinema.dev.controllers;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.cinema.dev.models.Societe;
import com.cinema.dev.repositories.DiffusionRepository;
import com.cinema.dev.repositories.SocieteRepository;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/societes")
@RequiredArgsConstructor
public class SocieteController {

    private final SocieteRepository societeRepository;
    private final DiffusionRepository diffusionRepository;

    @GetMapping("/liste")
    public String getListe(@RequestParam(value = "date", required = false) String dateStr, Model model) {
        LocalDate dateFiltre = dateStr != null && !dateStr.isBlank() ? LocalDate.parse(dateStr) : LocalDate.now();

        List<Societe> societes = societeRepository.findAll();

        Map<Long, Double> resteBySociete = new HashMap<>();
        for (Societe societe : societes) {
            double reste = societe.getRestePayeByDate(dateFiltre, diffusionRepository);
            resteBySociete.put(societe.getIdSociete(), reste);
        }

        model.addAttribute("societes", societes);
        model.addAttribute("resteBySociete", resteBySociete);
        model.addAttribute("dateFiltre", dateFiltre);
        model.addAttribute("content", "pages/societes/societe-liste");
        return "admin-layout";
    }
}
