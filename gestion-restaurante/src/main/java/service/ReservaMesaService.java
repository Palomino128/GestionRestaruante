package com.saborperuano.gestionrestaurante.service;

import com.saborperuano.gestionrestaurante.entity.Mesa;
import com.saborperuano.gestionrestaurante.entity.ReservaMesa;
import com.saborperuano.gestionrestaurante.entity.Usuario;
import com.saborperuano.gestionrestaurante.repository.MesaRepository;
import com.saborperuano.gestionrestaurante.repository.ReservaMesaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ReservaMesaService {

    @Autowired
    private ReservaMesaRepository reservaMesaRepository;

    @Autowired
    private MesaRepository mesaRepository;

    public ReservaMesa crearReserva(Usuario usuario,
                                    Integer mesaNumero,
                                    Integer personas,
                                    LocalDateTime fechaReserva) {

        LocalDateTime ahora = LocalDateTime.now();

        ReservaMesa reserva = new ReservaMesa();
        reserva.setUsuario(usuario);

        // IMPORTANTE: aquí mesaNumero va a ser el ID de la mesa
        reserva.setMesaNumero(mesaNumero);

        reserva.setPersonas(personas);
        reserva.setFechaReserva(fechaReserva);
        reserva.setEstado("PENDIENTE_PAGO");
        reserva.setCreadoEn(ahora);
        reserva.setExpiraEn(ahora.plusMinutes(5)); // 5 minutos para pagar

        return reservaMesaRepository.save(reserva);
    }

    public boolean mesaDisponible(Integer mesaNumero) {
        LocalDateTime ahora = LocalDateTime.now();
        List<String> estadosActivos = Arrays.asList("PENDIENTE_PAGO", "PAGADO");

        return !reservaMesaRepository.existsByMesaNumeroAndEstadoInAndExpiraEnAfter(
                mesaNumero,
                estadosActivos,
                ahora
        );
    }

    /**
     * Obtiene los IDs de las mesas activas que NO están ocupadas.
     * El "número" de mesa que manejamos hacia afuera será el ID.
     */
    public List<Integer> obtenerMesasDisponibles() {
        LocalDateTime ahora = LocalDateTime.now();
        List<String> estadosActivos = Arrays.asList("PENDIENTE_PAGO", "PAGADO");

        // Reservas que ocupan mesa
        List<ReservaMesa> activas = reservaMesaRepository
                .findByEstadoInAndExpiraEnAfter(estadosActivos, ahora);

        Set<Integer> ocupadas = activas.stream()
                .map(ReservaMesa::getMesaNumero)
                .collect(Collectors.toSet());

        // Mesas activas desde la BD
        List<Mesa> mesasActivas = mesaRepository.findByActivaTrueOrderByIdAsc();

        // devolvemos el ID como "número" disponible
        return mesasActivas.stream()
                .map(m -> m.getId().intValue())
                .filter(id -> !ocupadas.contains(id))
                .collect(Collectors.toList());
    }

    @Scheduled(fixedDelay = 60_000)
    public void eliminarReservasVencidasNoPagadas() {
        LocalDateTime ahora = LocalDateTime.now();
        List<ReservaMesa> vencidas = reservaMesaRepository
                .findByEstadoAndExpiraEnBefore("PENDIENTE_PAGO", ahora);

        if (!vencidas.isEmpty()) {
            reservaMesaRepository.deleteAll(vencidas);
        }
    }
}