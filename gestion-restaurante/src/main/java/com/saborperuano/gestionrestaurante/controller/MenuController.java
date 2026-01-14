package com.saborperuano.gestionrestaurante.controller;

import com.saborperuano.gestionrestaurante.entity.Plato;
import com.saborperuano.gestionrestaurante.entity.Sucursal;
import com.saborperuano.gestionrestaurante.repository.PlatoRepository;
import com.saborperuano.gestionrestaurante.repository.SucursalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/menu")
public class MenuController {

    @Autowired
    private PlatoRepository platoRepository;

    @Autowired
    private SucursalRepository sucursalRepository;

    /**
     * Mostrar menú. 
     * Nota: la entidad Plato en este proyecto no tiene relación con Sucursal (según el código),
     * por eso no intentamos filtrar platos por sucursal. Si en tu diseño quieres filtrar por sucursal
     * será necesario añadir la relación en la entidad Plato y crear el método correspondiente en el repo.
     */
    @GetMapping
    public String mostrarMenu(@RequestParam(value = "sucursalId", required = false) Integer sucursalId,
                              Model model) {
        model.addAttribute("titulo", "Menú - Sabor Peruano");

        // Obtener todos los platos disponibles (estado = true)
        List<Plato> platos = platoRepository.findByEstadoTrue();

        // Si nos pasan una sucursalId, buscamos la sucursal y la ponemos en el modelo
        if (sucursalId != null) {
            try {
                Optional<Sucursal> sucursalOpt = sucursalRepository.findById(sucursalId);
                sucursalOpt.ifPresent(sucursal -> model.addAttribute("sucursalSeleccionada", sucursal));
            } catch (Exception e) {
                // si el findById falla por tipos o por otra razón, lo ignoramos para no romper la vista
            }
        }

        model.addAttribute("platos", platos);
        return "menu";
    }
}