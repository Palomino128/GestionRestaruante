package com.saborperuano.gestionrestaurante.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("titulo", "Sabor Peruano - Inicio");
        return "index";
    }

    @GetMapping("/contacto")
    public String contacto() {
        return "contacto"; // templates/contacto.html
    }

    @GetMapping("/nosotros")
    public String nosotros() {
        return "nosotros"; // templates/nosotros.html
    }

    /**
     * Página principal para clientes después del login manual (no Spring Security).
     * Redirige a /login si no hay sesión.
     */
    @GetMapping("/home")
    public String home(HttpSession session) {
        if (session.getAttribute("usuarioLogeado") == null) {
            return "redirect:/login";
        }
        return "home"; // templates/home.html
    }
}