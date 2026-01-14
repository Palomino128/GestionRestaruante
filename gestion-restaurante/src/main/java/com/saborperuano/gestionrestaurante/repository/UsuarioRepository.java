package com.saborperuano.gestionrestaurante.repository;

import com.saborperuano.gestionrestaurante.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
    Optional<Usuario> findByUsuario(String usuario);
    // opción recomendable para login case-insensitive
    Optional<Usuario> findByUsuarioIgnoreCase(String usuario);

    List<Usuario> findByRol(String rol);

    // CORREGIR: buscar por id de sucursal (dos alternativas válidas)
    List<Usuario> findBySucursalId(Integer sucursalId);
    // o (ambas funcionan, elige una)
    // List<Usuario> findBySucursal_Id(Integer sucursalId);

    boolean existsByUsuario(String usuario);
    boolean existsByEmail(String email);
}