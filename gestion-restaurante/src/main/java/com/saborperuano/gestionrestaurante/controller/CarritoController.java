
package com.saborperuano.gestionrestaurante.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Controller
@RequestMapping("/carrito")
@SessionAttributes("carrito")
public class CarritoController {

    @ModelAttribute("carrito")
    public List<Map<String, Object>> carrito() {
        return new ArrayList<>();
    }

    // ✅ Mostrar carrito
    @GetMapping
    public String verCarrito(@ModelAttribute("carrito") List<Map<String, Object>> carrito, Model model) {
        double total = carrito.stream()
                .mapToDouble(item -> (double) item.get("precio"))
                .sum();
        model.addAttribute("total", total);
        return "carrito"; // apunta a carrito.html
    }

    // ✅ Agregar producto al carrito
    @PostMapping("/agregar")
    @ResponseBody
    public String agregarAlCarrito(
            @RequestParam String nombre,
            @RequestParam double precio,
            @ModelAttribute("carrito") List<Map<String, Object>> carrito) {

        Map<String, Object> item = new HashMap<>();
        item.put("nombre", nombre);
        item.put("precio", precio);
        carrito.add(item);

        return "OK"; // el fetch espera un 200 OK
    }
}
