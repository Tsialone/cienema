package com.cinema.dev.controllers;

import java.lang.ProcessBuilder.Redirect;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.cinema.dev.models.Reservation;
import com.cinema.dev.models.Salle;
import com.cinema.dev.models.Ticket;
import com.cinema.dev.repositories.CaisseRepository;
import com.cinema.dev.repositories.ClientRepository;
import com.cinema.dev.repositories.FilmRepository;
import com.cinema.dev.repositories.MouvementCaisseRepository;
import com.cinema.dev.repositories.PaiementRepository;
import com.cinema.dev.repositories.PlaceRepository;
import com.cinema.dev.repositories.SalleRepository;
import com.cinema.dev.repositories.SeanceRepository;
import com.cinema.dev.repositories.TicketRepository;
import com.cinema.dev.repositories.ReservationRepository;
import com.cinema.dev.forms.ReservationForm;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/reservations")
@RequiredArgsConstructor
public class ReservationController {

    private final SeanceRepository seanceRepository;
    private final SalleRepository salleRepository;
    private final FilmRepository filmRepository;
    private final PlaceRepository placeRepository;
    private final ClientRepository clientRepository;
    private final TicketRepository ticketRepository;
    private final ReservationRepository reservationRepository;
    private final PaiementRepository paiementRepository;
    private final MouvementCaisseRepository mouvementCaisseRepository;
    private final CaisseRepository caisseRepository;

    @GetMapping("/saisie")
    public String getSaisie(Model model,
            @RequestParam(required = true) String dateSeance,
            @RequestParam(required = true) Long idSalle,
            @RequestParam(required = true) String heure,
            @RequestParam(required = true) Long idFilm,
            @RequestParam(required = true) Long idSeance
        ) {

        LocalDateTime dateTime = LocalDateTime.parse(dateSeance + "T" + heure);

        if (idSalle != null) {
            Salle salle = salleRepository.findById(idSalle).orElse(null);
            model.addAttribute("salle", salle);
            model.addAttribute("places", salle.getPlaceDispo(dateTime, ticketRepository));
        }

        if (idFilm != null) {
            model.addAttribute("film", filmRepository.findById(idFilm).orElse(null));
        }

        model.addAttribute("clients", clientRepository.findAll());
        model.addAttribute("heure", heure);
        model.addAttribute("idSeance", idSeance);
        model.addAttribute("dateSeance", LocalDate.parse(dateSeance, DateTimeFormatter.ISO_DATE));
        model.addAttribute("content", "pages/reservations/reservation-saisie");

        return "admin-layout";
    }

    @GetMapping("/liste")
    public String getListe(Model model) {
        model.addAttribute("reservations", reservationRepository.findAll());
        model.addAttribute("content", "pages/reservations/reservation-liste");
        return "admin-layout";
    }

    @PostMapping("/saisie")
    public String postSaisie(ReservationForm form, Model model, RedirectAttributes rd) {
        try {
            form.control();
            Reservation.save(form, reservationRepository, placeRepository, ticketRepository, clientRepository,
                    filmRepository, seanceRepository , paiementRepository , mouvementCaisseRepository , caisseRepository);
            return "redirect:/reservations/liste";
        } catch (Exception e) {
            rd.addFlashAttribute("toastMessage", e.getMessage());
            rd.addFlashAttribute("toastType", "error");
            model.addAttribute("clients", clientRepository.findAll());
            e.printStackTrace();
            return "redirect:/reservations/saisie?dateSeance=" + form.getDateSeance() + "&idSalle=" + form.getIdSalle()
                    + "&heure=" + form.getHeure() + "&idFilm=" + form.getIdFilm() + "&idSeance=" + form.getIdSeance();
        }
    }
}
