package com.saborperuano.gestionrestaurante.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "reservas_mesa")
public class ReservaMesa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Relación con el cliente que hace la reserva
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @Column(name = "mesa_numero", nullable = false)
    private Integer mesaNumero;

    @Column(name = "personas", nullable = false)
    private Integer personas;

    // Fecha y hora en la que el cliente quiere venir
    @Column(name = "fecha_reserva", nullable = false)
    private LocalDateTime fechaReserva;

    // PENDIENTE_PAGO, PAGADO, CANCELADA, EXPIRADA
    @Column(name = "estado", length = 20, nullable = false)
    private String estado;

    // Cuándo se creó
    @Column(name = "creado_en", nullable = false)
    private LocalDateTime creadoEn;

    // Límite de pago (ahora + 5 minutos)
    @Column(name = "expira_en", nullable = false)
    private LocalDateTime expiraEn;

    // Getters y setters

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Usuario getUsuario() { return usuario; }
    public void setUsuario(Usuario usuario) { this.usuario = usuario; }

    public Integer getMesaNumero() { return mesaNumero; }
    public void setMesaNumero(Integer mesaNumero) { this.mesaNumero = mesaNumero; }

    public Integer getPersonas() { return personas; }
    public void setPersonas(Integer personas) { this.personas = personas; }

    public LocalDateTime getFechaReserva() { return fechaReserva; }
    public void setFechaReserva(LocalDateTime fechaReserva) { this.fechaReserva = fechaReserva; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public LocalDateTime getCreadoEn() { return creadoEn; }
    public void setCreadoEn(LocalDateTime creadoEn) { this.creadoEn = creadoEn; }

    public LocalDateTime getExpiraEn() { return expiraEn; }
    public void setExpiraEn(LocalDateTime expiraEn) { this.expiraEn = expiraEn; }
}
