package com.saborperuano.gestionrestaurante.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "sucursales")
public class Sucursal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @Column(nullable = false, length = 100)
    private String nombre;
    
    @Column(length = 200)
    private String direccion;
    
    @Column(length = 15)
    private String telefono;
    
    @Column(length = 50)
    private String distrito;
    
    @OneToMany(mappedBy = "sucursal", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Usuario> usuarios = new ArrayList<>();
    
    @OneToMany(mappedBy = "sucursal", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Inventario> inventarios = new ArrayList<>();
    
    // ELIMINAR esta relación (los pedidos no tienen sucursal en Supabase)
    // @OneToMany(mappedBy = "sucursal", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    // private List<Pedido> pedidos = new ArrayList<>();
    
    // ELIMINAR esta relación (los platos no tienen sucursal en Supabase)  
    // @OneToMany(mappedBy = "sucursal", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    // private List<Plato> platos = new ArrayList<>();
    
    @Column(name = "estado")
    private Boolean estado = true;
}