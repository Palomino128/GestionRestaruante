package com.saborperuano.gestionrestaurante.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "inventarios")
public class Inventario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id; // Cambiar de Long a Integer
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_sucursal") // Relación con sucursal
    private Sucursal sucursal;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_insumo") // Relación con insumo
    private Insumo insumo;
    
    @Column(name = "stock", precision = 10, scale = 2) // Cambiar cantidad_disponible a stock
    private BigDecimal stock = BigDecimal.ZERO;
    
    @Column(name = "actualizado")
    private LocalDateTime actualizado = LocalDateTime.now();
    
    // ELIMINAR relación con Plato
    // ELIMINAR métodos getEstado() y getEstadoColor()
}