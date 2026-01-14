package com.saborperuano.gestionrestaurante.controller;

import com.saborperuano.gestionrestaurante.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller
public class ContactoController {

    private final EmailService emailService;
    private final Logger logger = LoggerFactory.getLogger(ContactoController.class);

    // Inyección por constructor (recomendada)
    @Autowired
    public ContactoController(EmailService emailService) {
        this.emailService = emailService;
    }

    @PostMapping("/enviar-mensaje")
    public String enviarMensaje(
            @RequestParam(name = "nombre") String nombre,
            @RequestParam(name = "correo") String correo,
            @RequestParam(name = "mensaje") String mensaje,
            RedirectAttributes redirectAttributes) {

        try {
            logger.info("Recibiendo mensaje de contacto: nombre={}, correo={}", nombre, correo);
            emailService.enviarCorreo(nombre, correo, mensaje);
            redirectAttributes.addFlashAttribute("exito", "Tu mensaje fue enviado correctamente.");
        } catch (Exception e) {
            logger.error("Error al enviar correo de contacto", e);
            redirectAttributes.addFlashAttribute("error", "Hubo un problema al enviar el mensaje. Intenta más tarde.");
        }

        return "redirect:/contacto";
    }
}
