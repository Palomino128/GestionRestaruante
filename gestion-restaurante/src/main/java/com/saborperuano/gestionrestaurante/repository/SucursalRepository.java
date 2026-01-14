package com.saborperuano.gestionrestaurante.repository;

import com.saborperuano.gestionrestaurante.entity.Sucursal;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface SucursalRepository extends JpaRepository<Sucursal, Integer> {
    List<Sucursal> findByEstadoTrue();
}