package com.saborperuano.gestionrestaurante.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "mesas")
public class Mesa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // nombre: "Mesa 1", "Mesa 2", etc.
    @Column(name = "nombre", nullable = false)
    private String nombre;

    // asientos: número de sillas
    @Column(name = "asientos", nullable = false)
    private Integer asientos;

    @Column(name = "activa", nullable = false)
    private Boolean activa;

    // ===== GETTERS y SETTERS =====

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getAsientos() {
        return asientos;
    }

    public void setAsientos(Integer asientos) {
        this.asientos = asientos;
    }

    public Boolean getActiva() {
        return activa;
    }

    public void setActiva(Boolean activa) {
        this.activa = activa;
    }
}