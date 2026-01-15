package com.cinema.dev.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.cinema.dev.forms.ClientForm;
import com.cinema.dev.models.Client;
import com.cinema.dev.repositories.ClientRepository;
import com.cinema.dev.repositories.GenreRepository;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/clients")
@RequiredArgsConstructor
public class ClientController {

    private final ClientRepository clientRepository;
    private final GenreRepository genreRepository;

    @GetMapping("/saisie")
    public String getSaisie(Model model) {
        model.addAttribute("genres", genreRepository.findAll());
        model.addAttribute("content", "pages/clients/client-saisie");
        return "admin-layout";
    }

    @PostMapping("/saisie")
    public String postSaisie(ClientForm form, RedirectAttributes rd) {
        try {
            form.control();
            
            Client client = new Client();
            client.setNom(form.getNom());
            client.setGenre(genreRepository.findById(form.getIdGenre())
                    .orElseThrow(() -> new Exception("Genre non trouvé")));
            
            clientRepository.save(client);
            
            rd.addFlashAttribute("toastMessage", "Client créé avec succès!");
            rd.addFlashAttribute("toastType", "success");
            return "redirect:/clients/liste";
        } catch (Exception e) {
            rd.addFlashAttribute("toastMessage", e.getMessage());
            rd.addFlashAttribute("toastType", "error");
            return "redirect:/clients/saisie";
        }
    }

    @GetMapping("/liste")
    public String getListe(Model model) {
        model.addAttribute("clients", clientRepository.findAll());
        model.addAttribute("content", "pages/clients/client-liste");
        return "admin-layout";
    }
}
