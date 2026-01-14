package com.saborperuano.gestionrestaurante.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class EmpleadoController {

    @GetMapping("/empleado/dashboard")
    public String empleadoDashboard(HttpSession session, Model model) {
        if (session.getAttribute("usuarioLogeado") == null) {
            return "redirect:/login";
        }
        model.addAttribute("titulo", "Dashboard Empleado - Sabor Peruano");
        return "empleado/dashboard"; // templates/empleado/dashboard.html
    }
}