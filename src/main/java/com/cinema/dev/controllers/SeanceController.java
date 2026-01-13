package com.cinema.dev.controllers;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.cinema.dev.repositories.FilmRepository;
import com.cinema.dev.repositories.SalleRepository;
import com.cinema.dev.repositories.SeanceRepository;
import com.cinema.dev.views.SeanceDetail;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/seances")
@RequiredArgsConstructor
public class SeanceController {

    private final SeanceRepository seanceRepository;
    private final SalleRepository salleRepository;
    private final FilmRepository filmRepository;

    @GetMapping("/liste")
    public String getListe(Model model,
                          @RequestParam(required = false) LocalDate debut,
                          @RequestParam(required = false) Long idSalle,
                          @RequestParam(required = false) Long idFilm) {
        List<SeanceDetail> seances = seanceRepository.filterBy(debut, idSalle, idFilm);
        model.addAttribute("seances", seances);
        model.addAttribute("salles", salleRepository.findAll());
        model.addAttribute("films", filmRepository.findAll());
        model.addAttribute("content", "pages/seances/seance-liste"); 
        return "admin-layout";
    }
}

