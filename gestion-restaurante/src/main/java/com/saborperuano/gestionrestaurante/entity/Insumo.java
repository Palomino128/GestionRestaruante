package com.saborperuano.gestionrestaurante.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "insumos")
public class Insumo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id; // Cambiar de Long a Integer
    
    @Column(nullable = false, length = 100)
    private String nombre;
    
    @Column(length = 500)
    private String descripcion;
    
    @Column(name = "stock", precision = 10, scale = 2) // Agregar stock
    private BigDecimal stock = BigDecimal.ZERO;
    
    @Column(name = "unidad", length = 20) // Cambiar unidad_medida a unidad
    private String unidad;
    
    // ELIMINAR stockMinimo (no existe en Supabase)
    
    @Column(name = "estado")
    private Boolean estado = true;
    
    @OneToMany(mappedBy = "insumo", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Inventario> inventarios = new ArrayList<>();
}