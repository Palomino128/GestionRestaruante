package com.saborperuano.gestionrestaurante.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "pedidos")
public class Pedido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id; // Cambiar de Long a Integer
    
    // ELIMINAR codigoPedido (no existe en Supabase)
    // private String codigoPedido;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_usuario") // Cambiar cliente_id a id_usuario
    private Usuario usuario;
    
    // ELIMINAR relación con Sucursal (no existe en Supabase)
    // private Sucursal sucursal;
    
    // ELIMINAR campos adicionales (no existen en Supabase)
    // private String tipoPedido;
    // private String direccionEntrega;
    // private String telefonoCliente;
    
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal total;
    
    @Column(nullable = false, length = 20)
    private String estado = "pendiente"; // Usar valores de Supabase
    
    @Column(name = "fecha") // Cambiar fechaCreacion a fecha
    private LocalDateTime fecha = LocalDateTime.now();
    
    // ELIMINAR fechaActualizacion (no existe en Supabase)
    
    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<PedidoDetalle> detalles = new ArrayList<>();
    
    // ELIMINAR @PreUpdate
}