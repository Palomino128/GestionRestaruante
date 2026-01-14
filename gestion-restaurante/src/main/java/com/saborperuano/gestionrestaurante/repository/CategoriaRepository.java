package com.saborperuano.gestionrestaurante.repository;

import com.saborperuano.gestionrestaurante.entity.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CategoriaRepository extends JpaRepository<Categoria, Integer> {
    List<Categoria> findByEstadoTrue();
}