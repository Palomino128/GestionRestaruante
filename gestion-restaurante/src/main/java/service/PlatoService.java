package com.saborperuano.gestionrestaurante.service;

import com.saborperuano.gestionrestaurante.entity.Plato;
import com.saborperuano.gestionrestaurante.repository.PlatoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlatoService {

    @Autowired
    private PlatoRepository platoRepository;

    public List<Plato> listar() {
        return platoRepository.findAll();
    }

    public void guardar(Plato plato) {
        platoRepository.save(plato);
    }

    public Plato buscarPorId(Integer id) {
        return platoRepository.findById(id).orElse(null);
    }

    public void eliminar(Integer id) {
        platoRepository.deleteById(id);
    }

    public void actualizar(Integer id, Plato nuevo) {
        Plato viejo = buscarPorId(id);
        if (viejo != null) {
            viejo.setNombre(nuevo.getNombre());
            viejo.setDescripcion(nuevo.getDescripcion());
            viejo.setPrecio(nuevo.getPrecio());
            viejo.setImagen(nuevo.getImagen());
            viejo.setCategoria(nuevo.getCategoria());
            platoRepository.save(viejo);
        }
    }

    public void cambiarEstado(Integer id) {
        Plato plato = buscarPorId(id);
        if (plato != null) {
            plato.setEstado(!plato.getEstado());
            platoRepository.save(plato);
        }
    }
}
