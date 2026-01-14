package com.saborperuano.gestionrestaurante.controller;

import com.saborperuano.gestionrestaurante.entity.Usuario;
import com.saborperuano.gestionrestaurante.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class AuthController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    // ---------- FORMULARIO DE REGISTRO (GET) ----------
    @GetMapping("/registro")
    public String mostrarFormularioRegistro(Model model) {
        model.addAttribute("titulo", "Registro de Cliente - Sabor Peruano");
        return "registro";
    }

    // ---------- PROCESAR REGISTRO (POST) SOLO CLIENTES ----------
    @PostMapping("/registro")
    public String registrarCliente(@RequestParam String nombres,
                                   @RequestParam String apellidos,
                                   @RequestParam String usuario,
                                   @RequestParam String email,
                                   @RequestParam String clave,
                                   Model model) {

        // Validaciones básicas
        if (usuarioRepository.existsByUsuario(usuario)) {
            model.addAttribute("error", "El nombre de usuario ya está en uso");
            return "registro";
        }

        if (usuarioRepository.existsByEmail(email)) {
            model.addAttribute("error", "El correo electrónico ya está registrado");
            return "registro";
        }

        Usuario nuevo = new Usuario();
        nuevo.setNombres(nombres);
        nuevo.setApellidos(apellidos);
        nuevo.setUsuario(usuario);
        nuevo.setEmail(email);
        nuevo.setClave(clave);      // texto plano (para trabajo académico)
        nuevo.setRol("CLIENTE");    // solo se registran clientes
        nuevo.setEstado(true);      // activo
        // si id_sucursal puede ser null, no hace falta setearla

        usuarioRepository.save(nuevo);

        // Redirige al login con mensaje de éxito
        return "redirect:/login?registroOk=true";
    }
}