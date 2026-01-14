package com.saborperuano.gestionrestaurante.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "platos")
public class Plato {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id; // Cambiar de Long a Integer
    
    @Column(nullable = false, length = 100)
    private String nombre;
    
    @Column(length = 500)
    private String descripcion;
    
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal precio;
    
    @Column(name = "imagen") // Cambiar imagen_url a imagen
    private String imagen;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_categoria") // Cambiar categoria_id a id_categoria
    private Categoria categoria;
    
    // ELIMINAR la relación con Sucursal (no existe en Supabase)
    // @ManyToOne(fetch = FetchType.LAZY)
    // @JoinColumn(name = "sucursal_id")
    // private Sucursal sucursal;
    
    @Column(name = "estado") // Cambiar disponible a estado
    private Boolean estado = true;
    
    @OneToMany(mappedBy = "plato", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<PedidoDetalle> pedidoDetalles = new ArrayList<>();
    
    // ELIMINAR relación con Inventario (no aplica según Supabase)
}