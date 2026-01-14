package com.saborperuano.gestionrestaurante.repository;

import com.saborperuano.gestionrestaurante.entity.Mesa;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MesaRepository extends JpaRepository<Mesa, Long> {

    // mesas activas ordenadas por id (1,2,3,...)
    List<Mesa> findByActivaTrueOrderByIdAsc();
}