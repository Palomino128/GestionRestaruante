package com.saborperuano.gestionrestaurante.repository;

import com.saborperuano.gestionrestaurante.entity.Plato;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PlatoRepository extends JpaRepository<Plato, Integer> {
    // CORREGIDO: Los platos ya no tienen sucursal, eliminar métodos relacionados
    List<Plato> findByEstadoTrue();
    long countByEstadoTrue();
    
    // Método adicional útil
    List<Plato> findByCategoriaId(Integer categoriaId);
}