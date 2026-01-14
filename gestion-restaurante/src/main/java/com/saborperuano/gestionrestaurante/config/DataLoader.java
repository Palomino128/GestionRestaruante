package com.saborperuano.gestionrestaurante.config;

import com.saborperuano.gestionrestaurante.entity.*;
import com.saborperuano.gestionrestaurante.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Component
public class DataLoader implements CommandLineRunner {
    
    @Autowired
    private SucursalRepository sucursalRepository;
    
    @Autowired
    private CategoriaRepository categoriaRepository;
    
    @Autowired
    private PlatoRepository platoRepository;
    
    @Autowired
    private InsumoRepository insumoRepository;
    
    @Autowired
    private UsuarioRepository usuarioRepository;
    
    @Autowired
    private InventarioRepository inventarioRepository;
    
    @Override
    public void run(String... args) throws Exception {
        // Verificar si ya existen datos para no duplicar
        if (sucursalRepository.count() == 0) {
            cargarDatosIniciales();
        }
    }
    
    private void cargarDatosIniciales() {
        System.out.println("Cargando datos iniciales...");
        
        // Crear sucursales
        Sucursal sucursal1 = new Sucursal();
        sucursal1.setNombre("Sabor Peruano - Cerro Colorado");
        sucursal1.setDireccion("Av. Ejército 123");
        sucursal1.setDistrito("Cerro Colorado");
        sucursal1.setTelefono("054123456");
        sucursal1.setEstado(true);
        sucursal1 = sucursalRepository.save(sucursal1);
        
        Sucursal sucursal2 = new Sucursal();
        sucursal2.setNombre("Sabor Peruano - Yanahuara");
        sucursal2.setDireccion("Calle San Juan 456");
        sucursal2.setDistrito("Yanahuara");
        sucursal2.setTelefono("054654321");
        sucursal2.setEstado(true);
        sucursalRepository.save(sucursal2);
        
        // Crear categorías
        Categoria entradas = new Categoria();
        entradas.setNombre("Entradas");
        entradas.setDescripcion("Platos de entrada tradicionales");
        entradas.setEstado(true);
        entradas = categoriaRepository.save(entradas);
        
        Categoria platosPrincipales = new Categoria();
        platosPrincipales.setNombre("Platos Principales");
        platosPrincipales.setDescripcion("Platos fuertes de la cocina peruana");
        platosPrincipales.setEstado(true);
        platosPrincipales = categoriaRepository.save(platosPrincipales);
        
        Categoria bebidas = new Categoria();
        bebidas.setNombre("Bebidas");
        bebidas.setDescripcion("Bebidas tradicionales y refrescos");
        bebidas.setEstado(true);
        bebidas = categoriaRepository.save(bebidas);
        
        // Crear platos (CORREGIDO: usar 'estado' en lugar de 'disponible' y 'imagen' en lugar de 'imagenUrl')
        Plato lomoSaltado = new Plato();
        lomoSaltado.setNombre("Lomo Saltado");
        lomoSaltado.setDescripcion("Clásico lomo saltado con arroz y papas fritas");
        lomoSaltado.setPrecio(new BigDecimal("25.00"));
        lomoSaltado.setImagen("/images/lomo-saltado.jpg");
        lomoSaltado.setCategoria(platosPrincipales);
        lomoSaltado.setEstado(true);
        platoRepository.save(lomoSaltado);
        
        Plato polloBrasa = new Plato();
        polloBrasa.setNombre("Pollo a la Brasa 1/4");
        polloBrasa.setDescripcion("Pollo a la brasa con papas fritas y ensalada");
        polloBrasa.setPrecio(new BigDecimal("18.00"));
        polloBrasa.setImagen("/images/pollo-brasa.jpg");
        polloBrasa.setCategoria(platosPrincipales);
        polloBrasa.setEstado(true);
        platoRepository.save(polloBrasa);
        
        Plato ceviche = new Plato();
        ceviche.setNombre("Ceviche Clásico");
        ceviche.setDescripcion("Ceviche de pescado con cebolla, camote y choclo");
        ceviche.setPrecio(new BigDecimal("22.00"));
        ceviche.setImagen("/images/ceviche.jpg");
        ceviche.setCategoria(platosPrincipales);
        ceviche.setEstado(true);
        platoRepository.save(ceviche);
        
        Plato causa = new Plato();
        causa.setNombre("Causa Limeña");
        causa.setDescripcion("Causa rellena de pollo o atún");
        causa.setPrecio(new BigDecimal("16.00"));
        causa.setImagen("/images/causa.jpg");
        causa.setCategoria(entradas);
        causa.setEstado(true);
        platoRepository.save(causa);
        
        Plato incaKola = new Plato();
        incaKola.setNombre("Inca Kola");
        incaKola.setDescripcion("Bebida gaseosa peruana");
        incaKola.setPrecio(new BigDecimal("5.00"));
        incaKola.setImagen("/images/inca-kola.jpg");
        incaKola.setCategoria(bebidas);
        incaKola.setEstado(true);
        platoRepository.save(incaKola);
        
        // Crear insumos (CORREGIDO: usar 'unidad' en lugar de 'unidadMedida' y 'stock' en lugar de 'stockMinimo')
        Insumo arroz = new Insumo();
        arroz.setNombre("Arroz");
        arroz.setDescripcion("Arroz blanco de grano largo");
        arroz.setUnidad("kg");
        arroz.setStock(new BigDecimal("10.00"));
        arroz.setEstado(true);
        arroz = insumoRepository.save(arroz);
        
        Insumo pollo = new Insumo();
        pollo.setNombre("Pollo");
        pollo.setDescripcion("Pollo fresco");
        pollo.setUnidad("kg");
        pollo.setStock(new BigDecimal("5.00"));
        pollo.setEstado(true);
        pollo = insumoRepository.save(pollo);
        
        Insumo carne = new Insumo();
        carne.setNombre("Carne de res");
        carne.setDescripcion("Lomo de res para saltado");
        carne.setUnidad("kg");
        carne.setStock(new BigDecimal("8.00"));
        carne.setEstado(true);
        carne = insumoRepository.save(carne);
        
        Insumo cebolla = new Insumo();
        cebolla.setNombre("Cebolla");
        cebolla.setDescripcion("Cebolla roja");
        cebolla.setUnidad("kg");
        cebolla.setStock(new BigDecimal("3.00"));
        cebolla.setEstado(true);
        cebolla = insumoRepository.save(cebolla);
        
        Insumo papa = new Insumo();
        papa.setNombre("Papa");
        papa.setDescripcion("Papa amarilla");
        papa.setUnidad("kg");
        papa.setStock(new BigDecimal("12.00"));
        papa.setEstado(true);
        papa = insumoRepository.save(papa);
        
        Insumo tomate = new Insumo();
        tomate.setNombre("Tomate");
        tomate.setDescripcion("Tomate fresco");
        tomate.setUnidad("kg");
        tomate.setStock(new BigDecimal("4.00"));
        tomate.setEstado(true);
        tomate = insumoRepository.save(tomate);
        
        Insumo aceite = new Insumo();
        aceite.setNombre("Aceite Vegetal");
        aceite.setDescripcion("Aceite para cocinar");
        aceite.setUnidad("lt");
        aceite.setStock(new BigDecimal("2.00"));
        aceite.setEstado(true);
        aceite = insumoRepository.save(aceite);
        
        // Crear inventario (CORREGIDO: usar 'stock' en lugar de 'cantidadDisponible' y agregar 'actualizado')
        Inventario invArroz = new Inventario();
        invArroz.setInsumo(arroz);
        invArroz.setSucursal(sucursal1);
        invArroz.setStock(new BigDecimal("10.00"));
        invArroz.setActualizado(LocalDateTime.now());
        inventarioRepository.save(invArroz);
        
        Inventario invPollo = new Inventario();
        invPollo.setInsumo(pollo);
        invPollo.setSucursal(sucursal1);
        invPollo.setStock(new BigDecimal("5.00"));
        invPollo.setActualizado(LocalDateTime.now());
        inventarioRepository.save(invPollo);
        
        Inventario invCarne = new Inventario();
        invCarne.setInsumo(carne);
        invCarne.setSucursal(sucursal1);
        invCarne.setStock(new BigDecimal("8.00"));
        invCarne.setActualizado(LocalDateTime.now());
        inventarioRepository.save(invCarne);
        
        Inventario invCebolla = new Inventario();
        invCebolla.setInsumo(cebolla);
        invCebolla.setSucursal(sucursal1);
        invCebolla.setStock(new BigDecimal("3.00"));
        invCebolla.setActualizado(LocalDateTime.now());
        inventarioRepository.save(invCebolla);
        
        Inventario invPapa = new Inventario();
        invPapa.setInsumo(papa);
        invPapa.setSucursal(sucursal1);
        invPapa.setStock(new BigDecimal("12.00"));
        invPapa.setActualizado(LocalDateTime.now());
        inventarioRepository.save(invPapa);
        
        Inventario invTomate = new Inventario();
        invTomate.setInsumo(tomate);
        invTomate.setSucursal(sucursal1);
        invTomate.setStock(new BigDecimal("4.00"));
        invTomate.setActualizado(LocalDateTime.now());
        inventarioRepository.save(invTomate);
        
        Inventario invAceite = new Inventario();
        invAceite.setInsumo(aceite);
        invAceite.setSucursal(sucursal1);
        invAceite.setStock(new BigDecimal("2.00"));
        invAceite.setActualizado(LocalDateTime.now());
        inventarioRepository.save(invAceite);
        
        // Crear usuarios (CORREGIDO: usar nombres nuevos según entidad Usuario)
        Usuario admin = new Usuario();
        admin.setNombres("Juan");
        admin.setApellidos("Pérez García");
        admin.setUsuario("admin");
        admin.setClave("admin123"); // En producción usar BCrypt
        admin.setEmail("admin@saborperuano.com");
        admin.setRol("ADMIN");
        admin.setSucursal(sucursal1);
        admin.setEstado(true);
        usuarioRepository.save(admin);
        
        Usuario cocina = new Usuario();
        cocina.setNombres("María");
        cocina.setApellidos("López Torres");
        cocina.setUsuario("cocina");
        cocina.setClave("cocina123");
        cocina.setEmail("cocina@saborperuano.com");
        cocina.setRol("EMPLEADO");
        cocina.setSucursal(sucursal1);
        cocina.setEstado(true);
        usuarioRepository.save(cocina);
        
        // Crear usuario para sucursal 2
        Usuario usuarioSucursal2 = new Usuario();
        usuarioSucursal2.setNombres("Carlos");
        usuarioSucursal2.setApellidos("Rodríguez Mendoza");
        usuarioSucursal2.setUsuario("yanahuara");
        usuarioSucursal2.setClave("yanahuara123");
        usuarioSucursal2.setEmail("yanahuara@saborperuano.com");
        usuarioSucursal2.setRol("EMPLEADO");
        usuarioSucursal2.setSucursal(sucursal2);
        usuarioSucursal2.setEstado(true);
        usuarioRepository.save(usuarioSucursal2);
        
        System.out.println("✅ Datos iniciales cargados correctamente");
    }
}