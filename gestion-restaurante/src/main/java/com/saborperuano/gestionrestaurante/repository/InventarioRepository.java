package com.saborperuano.gestionrestaurante.repository;

import com.saborperuano.gestionrestaurante.entity.Inventario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface InventarioRepository extends JpaRepository<Inventario, Integer> {
    List<Inventario> findBySucursalId(Integer sucursalId);
    
    // CORREGIDO: Usar 'stock' en lugar de 'cantidadDisponible' y eliminar referencia a stockMinimo
    @Query("SELECT i FROM Inventario i WHERE i.stock < 5")
    List<Inventario> findStockBajo();
    
    @Query("SELECT i FROM Inventario i WHERE i.stock = 0")
    List<Inventario> findStockAgotado();
}