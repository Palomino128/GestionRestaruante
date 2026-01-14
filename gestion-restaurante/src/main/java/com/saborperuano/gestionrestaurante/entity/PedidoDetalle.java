package com.saborperuano.gestionrestaurante.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;

@Data
@Entity
@Table(name = "pedido_detalles")
public class PedidoDetalle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id; // Cambiar de Long a Integer
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_pedido", nullable = false) // Cambiar pedido_id a id_pedido
    private Pedido pedido;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_plato", nullable = false) // Cambiar plato_id a id_plato
    private Plato plato;
    
    @Column(nullable = false)
    private Integer cantidad;
    
    @Column(name = "precio_unitario", nullable = false, precision = 10, scale = 2)
    private BigDecimal precioUnitario;
    
    // ELIMINAR subtotal (no existe en Supabase)
    // private BigDecimal subtotal;
    
    // ELIMINAR @PrePersist y @PreUpdate
}