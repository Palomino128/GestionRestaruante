package com.saborperuano.gestionrestaurante.repository;

import com.saborperuano.gestionrestaurante.entity.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PedidoRepository extends JpaRepository<Pedido, Integer> {
    long countByEstado(String estado);
}