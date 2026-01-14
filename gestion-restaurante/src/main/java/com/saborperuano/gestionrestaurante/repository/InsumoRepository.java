package com.saborperuano.gestionrestaurante.repository;

import com.saborperuano.gestionrestaurante.entity.Insumo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;
import java.util.List;

public interface InsumoRepository extends JpaRepository<Insumo, Integer> {
    List<Insumo> findByNombreContainingIgnoreCase(String nombre);
    List<Insumo> findByEstadoTrue();
    List<Insumo> findByEstadoFalse(); // Devuelve insumos agotados

    List<Insumo> findByStockLessThanEqual(BigDecimal cantidad);
        List<Insumo> findByStock(BigDecimal stock); // Stock agotado (0)
        List<Insumo> findByStockEquals(BigDecimal cantidad);



}