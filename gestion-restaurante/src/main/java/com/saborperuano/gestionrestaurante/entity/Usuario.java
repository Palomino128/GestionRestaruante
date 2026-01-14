package com.saborperuano.gestionrestaurante.entity;

import jakarta.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "usuarios")
public class Usuario implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "nombres", length = 150)
    private String nombres;

    @Column(name = "apellidos", length = 150)
    private String apellidos;

    @Column(name = "usuario", length = 100, unique = true)
    private String usuario;

    @Column(name = "clave", columnDefinition = "text")
    private String clave;

    @Column(name = "estado")
    private Boolean estado;

    @Column(name = "email", length = 200)
    private String email;

    @Column(name = "rol", length = 50)
    private String rol;

    // --- RELACIÓN CORRECTA A SUCURSAL ---
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_sucursal") // coincide con tu columna en la tabla usuarios
    private Sucursal sucursal;

    public Usuario() {}

    // getters y setters

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getNombres() { return nombres; }
    public void setNombres(String nombres) { this.nombres = nombres; }

    public String getApellidos() { return apellidos; }
    public void setApellidos(String apellidos) { this.apellidos = apellidos; }

    public String getUsuario() { return usuario; }
    public void setUsuario(String usuario) { this.usuario = usuario; }

    public String getClave() { return clave; }
    public void setClave(String clave) { this.clave = clave; }

    public Boolean getEstado() { return estado; }
    public void setEstado(Boolean estado) { this.estado = estado; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getRol() { return rol; }
    public void setRol(String rol) { this.rol = rol; }

    public Sucursal getSucursal() { return sucursal; }
    public void setSucursal(Sucursal sucursal) { this.sucursal = sucursal; }

    @Override
    public String toString() {
        return "Usuario{" +
                "id=" + id +
                ", usuario='" + usuario + '\'' +
                ", nombres='" + nombres + '\'' +
                ", apellidos='" + apellidos + '\'' +
                ", email='" + email + '\'' +
                ", rol='" + rol + '\'' +
                ", estado=" + estado +
                '}';
    }
}