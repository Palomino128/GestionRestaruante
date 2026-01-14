package com.saborperuano.gestionrestaurante.repository;

import com.saborperuano.gestionrestaurante.entity.ReservaMesa;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

public interface ReservaMesaRepository extends JpaRepository<ReservaMesa, Long> {

    List<ReservaMesa> findByEstadoAndExpiraEnBefore(String estado, LocalDateTime fecha);

    List<ReservaMesa> findByEstadoInAndExpiraEnAfter(Collection<String> estados, LocalDateTime fecha);

    boolean existsByMesaNumeroAndEstadoInAndExpiraEnAfter(
            Integer mesaNumero,
            Collection<String> estados,
            LocalDateTime fecha
    );
}