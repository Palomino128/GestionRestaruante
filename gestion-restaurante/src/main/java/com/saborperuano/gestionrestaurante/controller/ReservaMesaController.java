package com.saborperuano.gestionrestaurante.controller;

import com.saborperuano.gestionrestaurante.entity.ReservaMesa;
import com.saborperuano.gestionrestaurante.entity.Usuario;
import com.saborperuano.gestionrestaurante.service.ReservaMesaService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping("/reservas")
public class ReservaMesaController {

    @Autowired
    private ReservaMesaService reservaMesaService;

    // DTO sencillo para el formulario
    public static class ReservaForm {
        private Integer mesaNumero;
        private Integer personas;

        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
        private LocalDateTime fechaReserva;

        public Integer getMesaNumero() { return mesaNumero; }
        public void setMesaNumero(Integer mesaNumero) { this.mesaNumero = mesaNumero; }

        public Integer getPersonas() { return personas; }
        public void setPersonas(Integer personas) { this.personas = personas; }

        public LocalDateTime getFechaReserva() { return fechaReserva; }
        public void setFechaReserva(LocalDateTime fechaReserva) { this.fechaReserva = fechaReserva; }
    }

    @GetMapping
    public String mostrarFormularioReserva(HttpSession session, Model model) {
        Usuario usuario = (Usuario) session.getAttribute("usuarioLogeado");

        // 1) Validar que esté logueado
        if (usuario == null) {
            return "redirect:/login?origen=reservas";
        }

        // 2) Validar que sea rol CLIENTE
        if (!"CLIENTE".equalsIgnoreCase(usuario.getRol())) {
            return "redirect:/login?errorRol=true";
        }

        List<Integer> mesasDisponibles = reservaMesaService.obtenerMesasDisponibles();

        model.addAttribute("mesasDisponibles", mesasDisponibles);
        model.addAttribute("reservaForm", new ReservaForm());

        return "reservas"; // templates/reservas.html
    }

    @PostMapping
    public String crearReserva(@ModelAttribute("reservaForm") ReservaForm form,
                               HttpSession session,
                               RedirectAttributes redirectAttributes) {

        Usuario usuario = (Usuario) session.getAttribute("usuarioLogeado");

        if (usuario == null) {
            return "redirect:/login?origen=reservas";
        }

        if (!"CLIENTE".equalsIgnoreCase(usuario.getRol())) {
            return "redirect:/login?errorRol=true";
        }

        if (!reservaMesaService.mesaDisponible(form.getMesaNumero())) {
            redirectAttributes.addFlashAttribute("error",
                    "La mesa seleccionada ya no está disponible.");
            return "redirect:/reservas";
        }

        ReservaMesa reserva = reservaMesaService.crearReserva(
                usuario,
                form.getMesaNumero(),
                form.getPersonas(),
                form.getFechaReserva()
        );

        redirectAttributes.addFlashAttribute("success",
                "Reserva creada. Tienes 5 minutos para completar el pago. Código: " + reserva.getId());

        // Aquí luego puedes redirigir a una página de pago: /pago/{id}
        return "redirect:/reservas";
    }

    // Endpoint para consultar mesas disponibles vía AJAX si quieres
    @GetMapping("/disponibles")
    @ResponseBody
    public List<Integer> mesasDisponibles() {
        return reservaMesaService.obtenerMesasDisponibles();
    }
}
