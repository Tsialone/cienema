package com.cinema.dev.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.cinema.dev.dtos.UtilisateurDTO;
import com.cinema.dev.mappers.UtilisateurMapper;
import com.cinema.dev.models.Utilisateur;
import com.cinema.dev.repositories.UtilisateurRepository;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@Controller
// @RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UtilisateurRepository utilisateurRepository;

    @GetMapping("/")
    public String loginForm() {
        return "pages/auth/login-saisie";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request , RedirectAttributes rd) {
        request.getSession().invalidate();
        rd.addFlashAttribute("toastMessage", "Deconnection reussie !");
        rd.addFlashAttribute("toastType", "info");
        return "redirect:/";
    }

    @PostMapping("/login")
    public String login(HttpServletRequest request, RedirectAttributes rd, @RequestParam(required = false) String email,
            @RequestParam(required = false) String passe) {
        try {
            Utilisateur user = Utilisateur.login(utilisateurRepository, email, passe);
            request.getSession().setAttribute("user", user);
        } catch (Exception e) {
            rd.addFlashAttribute("toastMessage", e.getMessage());
            rd.addFlashAttribute("toastType", "error");
            return "redirect:/";
        }
        return "redirect:/home";
    }

    @GetMapping("/signin")
    public String signinForm(RedirectAttributes rd, @ModelAttribute UtilisateurDTO utilisateurDTO) {

        return "pages/auth/signin-saisie";
    }

    @PostMapping("/signin")
    public String signin(RedirectAttributes rd, @ModelAttribute UtilisateurDTO utilisateurDTO) {
        try {
            Utilisateur utilisateur = UtilisateurMapper.toEntity(utilisateurDTO);
            utilisateur.signin(utilisateurRepository);

        } catch (Exception e) {
            rd.addFlashAttribute("toastMessage", e.getMessage());
            rd.addFlashAttribute("toastType", "error");
        }
        rd.addFlashAttribute("toastMessage", "Inscription reussie ! Veuillez vous connecter.");
        rd.addFlashAttribute("toastType", "success");
        return "redirect:/signin";

    }
}
