package com.saborperuano.gestionrestaurante.controller;

import com.saborperuano.gestionrestaurante.entity.Usuario;
import com.saborperuano.gestionrestaurante.repository.UsuarioRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
public class LoginController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    /**
     * Muestra el formulario de login.
     * - Si ya hay sesión activa redirige según el rol.
     * - Acepta query params ?error=true y ?logout=true para mostrar mensajes.
     */
   @GetMapping("/login")
public String mostrarLogin(Model model,
                           HttpSession session,
                           @RequestParam(required = false) String error,
                           @RequestParam(required = false) String logout,
                           @RequestParam(required = false) String registroOk) {

        // Si ya hay usuario en sesión redirigimos según su rol
        Object u = session.getAttribute("usuarioLogeado");
        if (u instanceof Usuario) {
            Usuario usuarioSesion = (Usuario) u;
            String role = usuarioSesion.getRol() == null ? "" : usuarioSesion.getRol().toUpperCase();
            switch (role) {
                case "ADMIN":
                    return "redirect:/admin/dashboard";
                case "EMPLEADO":
                    return "redirect:/empleado/dashboard";
                case "CLIENTE":
                    return "redirect:/home";
                default:
                    // no reconocido -> invalida sesión por seguridad
                    session.invalidate();
            }
        }

        if (error != null) {
            model.addAttribute("error", "Usuario o contraseña incorrectos");
        }
        if (logout != null) {
            model.addAttribute("success", "Cerraste sesión correctamente");
        }

        model.addAttribute("titulo", "Iniciar Sesión - Sabor Peruano");
        return "login";
    }

    /**
     * Procesa el formulario de login.
     * No se confía en el 'rol' enviado desde el cliente: la redirección se basa en el rol en la BD.
     */
    @PostMapping("/login")
    public String procesarLogin(@RequestParam String username,
                                @RequestParam String password,
                                HttpSession session) {

        if (username == null || password == null) {
            return "redirect:/login?error=true";
        }

        String userTrim = username.trim();
        String passTrim = password.trim();

        if (userTrim.isEmpty() || passTrim.isEmpty()) {
            return "redirect:/login?error=true";
        }

        // Intento buscar por usuario exacto
        Optional<Usuario> usuarioOpt = usuarioRepository.findByUsuario(userTrim);

        // Si no lo encuentra, pruebo buscando ignorando mayúsculas (por si el valor en BD difiere en case)
        if (usuarioOpt.isEmpty()) {
            // busca en todos y compara ignorando case (solo si hay pocos usuarios; si la tabla es grande, crear un método repo adecuado)
            usuarioOpt = usuarioRepository.findAll()
                    .stream()
                    .filter(us -> us.getUsuario() != null && us.getUsuario().equalsIgnoreCase(userTrim))
                    .findFirst();
        }

        if (usuarioOpt.isEmpty()) {
            return "redirect:/login?error=true";
        }

        Usuario usuario = usuarioOpt.get();

        // validar estado
        if (usuario.getEstado() == null || !usuario.getEstado()) {
            return "redirect:/login?error=true";
        }

        // validar clave en texto plano (si usas hashes, reemplaza esto por BCrypt.match)
        if (usuario.getClave() == null || !usuario.getClave().equals(passTrim)) {
            return "redirect:/login?error=true";
        }

        // Guardar en sesión
        session.setAttribute("usuarioLogeado", usuario);

        // Redirigir según rol desde BD
        String role = usuario.getRol() == null ? "" : usuario.getRol().toUpperCase();

        switch (role) {
            case "ADMIN":
                return "redirect:/admin/dashboard";
            case "EMPLEADO":
                return "redirect:/empleado/dashboard";
            case "CLIENTE":
                return "redirect:/home";
            default:
                // rol desconocido -> invalida sesión por seguridad
                session.invalidate();
                return "redirect:/login?error=true";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login?logout=true";
    }
}