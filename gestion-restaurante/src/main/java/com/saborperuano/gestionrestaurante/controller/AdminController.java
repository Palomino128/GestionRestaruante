package com.saborperuano.gestionrestaurante.controller;

import com.saborperuano.gestionrestaurante.entity.*;
import com.saborperuano.gestionrestaurante.repository.*;
import com.saborperuano.gestionrestaurante.service.SupabaseStorageService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import org.springframework.transaction.annotation.Transactional;


import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.HashSet;



@Controller
@RequestMapping("/admin")
public class AdminController {
    
    @Autowired
    private PlatoRepository platoRepository;
    
    @Autowired
    private SucursalRepository sucursalRepository;
    
    @Autowired
    private InventarioRepository inventarioRepository;
    
    @Autowired
    private PedidoRepository pedidoRepository;
    
    @Autowired
    private CategoriaRepository categoriaRepository;
    
    @Autowired
    private UsuarioRepository usuarioRepository;
    
    @Autowired
    private InsumoRepository insumoRepository;

    @Autowired
private SupabaseStorageService storageService;


// ===== DASHBOARD =====
@GetMapping("/dashboard")
public String dashboard(Model model) {
    model.addAttribute("titulo", "Dashboard - Sabor Peruano");

    // Estadísticas para el dashboard
    long pedidosPendientes = 0;
    long totalPlatos = 0;
    long platosActivos = 0;

    try {
        pedidosPendientes = pedidoRepository.countByEstado("pendiente");
    } catch (Exception e) {
        pedidosPendientes = 0;
    }

    try {
        totalPlatos = platoRepository.count();
    } catch (Exception e) {
        totalPlatos = 0;
    }

    try {
        platosActivos = platoRepository.countByEstadoTrue();
    } catch (Exception e) {
        platosActivos = 0;
    }

    // Alertas de inventario
    List<Insumo> stockBajoList = new ArrayList<>();
    List<Insumo> stockAgotadoList = new ArrayList<>();
    long inventarioBajo = 0;

    try {
        // Insumos con stock bajo (ej. <= 5)
        stockBajoList = insumoRepository.findByStockLessThanEqual(new BigDecimal("5"));

        // Insumos agotados (estado = false)
        stockAgotadoList = insumoRepository.findByEstadoFalse();

        // Total alertas
        inventarioBajo = (stockBajoList != null ? stockBajoList.size() : 0)
                        + (stockAgotadoList != null ? stockAgotadoList.size() : 0);
    } catch (Exception e) {
        inventarioBajo = 0;
    }

    // Agregar al modelo
    model.addAttribute("pedidosPendientes", pedidosPendientes);
    model.addAttribute("totalPlatos", totalPlatos);
    model.addAttribute("platosActivos", platosActivos);
    model.addAttribute("inventarioBajo", inventarioBajo);
    model.addAttribute("stockBajoList", stockBajoList);
    model.addAttribute("stockAgotadoList", stockAgotadoList);

    return "admin/dashboard";
}





    // ===== GESTIÓN DE PLATOS =====
    @GetMapping("/platos")
    public String gestionPlatos(Model model) {
        model.addAttribute("titulo", "Gestión de Platos - Sabor Peruano");
        
        List<Plato> platos = platoRepository.findAll();
        List<Categoria> categorias = categoriaRepository.findAll();
        
        model.addAttribute("platos", platos);
        model.addAttribute("categorias", categorias);
        
        // Para el formulario de nuevo plato
        if (!model.containsAttribute("nuevoPlato")) {
            model.addAttribute("nuevoPlato", new Plato());
        }
        
        return "admin/gestion-platos";
    }

 @PostMapping("/platos")
public String crearPlato(@ModelAttribute Plato plato,
                         @RequestParam("archivoImagen") MultipartFile archivoImagen,
                         RedirectAttributes redirectAttributes) {

    try {
        if (archivoImagen.isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "Debe subir una imagen.");
            return "redirect:/admin/platos";
        }

        // Subir imagen a supabase storage
        String urlImagen = storageService.uploadImage(archivoImagen, "imagenes-platos");

        plato.setImagen(urlImagen);

        if (plato.getEstado() == null) {
            plato.setEstado(true);
        }

        platoRepository.save(plato);

        redirectAttributes.addFlashAttribute("success", "Plato creado exitosamente");

    } catch (Exception e) {
        redirectAttributes.addFlashAttribute("error", "Error al crear el plato: " + e.getMessage());
    }

    return "redirect:/admin/platos";
}


    @GetMapping("/platos/editar/{id}")
    public String editarPlatoForm(@PathVariable Integer id, Model model) {
        Optional<Plato> platoOpt = platoRepository.findById(id);
        
        if (platoOpt.isPresent()) {
            Plato plato = platoOpt.get();
            List<Categoria> categorias = categoriaRepository.findAll();
            
            model.addAttribute("titulo", "Editar Plato - Sabor Peruano");
            model.addAttribute("plato", plato);
            model.addAttribute("categorias", categorias);
            model.addAttribute("esEdicion", true);
            
            return "admin/gestion-platos";
        } else {
            return "redirect:/admin/platos";
        }
    }

    @PostMapping("/platos/editar/{id}")
    public String actualizarPlato(@PathVariable Integer id, @ModelAttribute Plato plato, 
                                 RedirectAttributes redirectAttributes) {
        try {
            Optional<Plato> platoExistenteOpt = platoRepository.findById(id);
            
            if (platoExistenteOpt.isPresent()) {
                Plato platoExistente = platoExistenteOpt.get();
                
                // Actualizar propiedades (usar los nombres correctos de tu entidad)
                platoExistente.setNombre(plato.getNombre());
                platoExistente.setDescripcion(plato.getDescripcion());
                platoExistente.setPrecio(plato.getPrecio());
                platoExistente.setImagen(plato.getImagen()); // asegúrate que la entidad tenga setImagen
                platoExistente.setCategoria(plato.getCategoria());
                platoExistente.setEstado(plato.getEstado());
                
                platoRepository.save(platoExistente);
                redirectAttributes.addFlashAttribute("success", "Plato actualizado exitosamente");
            } else {
                redirectAttributes.addFlashAttribute("error", "Plato no encontrado");
            }
            
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error al actualizar el plato: " + e.getMessage());
        }
        
        return "redirect:/admin/platos";
    }

    @PostMapping("/platos/eliminar/{id}")
    public String eliminarPlato(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
        try {
            Optional<Plato> platoOpt = platoRepository.findById(id);
            
            if (platoOpt.isPresent()) {
                platoRepository.deleteById(id);
                redirectAttributes.addFlashAttribute("success", "Plato eliminado exitosamente");
            } else {
                redirectAttributes.addFlashAttribute("error", "Plato no encontrado");
            }
            
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error al eliminar el plato: " + e.getMessage());
        }
        
        return "redirect:/admin/platos";
    }

    @PostMapping("/platos/toggle-estado/{id}")
    public String toggleEstadoPlato(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
        try {
            Optional<Plato> platoOpt = platoRepository.findById(id);
            
            if (platoOpt.isPresent()) {
                Plato plato = platoOpt.get();
                plato.setEstado(!plato.getEstado());
                platoRepository.save(plato);
                
                String estado = plato.getEstado() ? "activado" : "desactivado";
                redirectAttributes.addFlashAttribute("success", "Plato " + estado + " exitosamente");
            } else {
                redirectAttributes.addFlashAttribute("error", "Plato no encontrado");
            }
            
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error al cambiar estado del plato: " + e.getMessage());
        }
        
        return "redirect:/admin/platos";
    }

    // ===== GESTIÓN DE INVENTARIO =====
    @GetMapping("/inventario")
    public String gestionInventario(Model model) {
        model.addAttribute("titulo", "Gestión de Inventario - Sabor Peruano");
        
        List<Inventario> inventario = inventarioRepository.findAll();
        List<Insumo> insumos = insumoRepository.findAll();
        List<Sucursal> sucursales = sucursalRepository.findAll();
        
        // Contar stock bajo y agotado para las alertas
        List<Inventario> stockBajo = inventarioRepository.findStockBajo();
        List<Inventario> stockAgotado = inventarioRepository.findStockAgotado();
        
        model.addAttribute("inventario", inventario);
        model.addAttribute("insumos", insumos);
        model.addAttribute("sucursales", sucursales);
        model.addAttribute("stockBajo", stockBajo == null ? 0 : stockBajo.size());
        model.addAttribute("stockAgotado", stockAgotado == null ? 0 : stockAgotado.size());
        
        // Para el formulario de nuevo inventario
        if (!model.containsAttribute("nuevoInsumo")) {
        model.addAttribute("nuevoInsumo", new Insumo());
    }

    return "admin/gestion-inventario";
}

    @PostMapping("/inventario")
    public String agregarInventario(@ModelAttribute Inventario inventario, RedirectAttributes redirectAttributes) {
        try {
            // Validaciones
            if (inventario.getInsumo() == null) {
                redirectAttributes.addFlashAttribute("error", "Debe seleccionar un insumo");
                return "redirect:/admin/inventario";
            }
            
            if (inventario.getSucursal() == null) {
                redirectAttributes.addFlashAttribute("error", "Debe seleccionar una sucursal");
                return "redirect:/admin/inventario";
            }
            
            if (inventario.getStock() == null || inventario.getStock().compareTo(BigDecimal.ZERO) < 0) {
                redirectAttributes.addFlashAttribute("error", "El stock debe ser mayor o igual a cero");
                return "redirect:/admin/inventario";
            }
            
            inventarioRepository.save(inventario);
            redirectAttributes.addFlashAttribute("success", "Registro de inventario agregado exitosamente");
            
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error al agregar inventario: " + e.getMessage());
        }
        
        return "redirect:/admin/inventario";
    }

    @PostMapping("/inventario/actualizar/{id}")
    public String actualizarInventario(@PathVariable Integer id, 
                                      @RequestParam BigDecimal cantidad, 
                                      RedirectAttributes redirectAttributes) {
        try {
            Optional<Inventario> inventarioOpt = inventarioRepository.findById(id);
            
            if (inventarioOpt.isPresent()) {
                Inventario inventario = inventarioOpt.get();
                
                if (cantidad.compareTo(BigDecimal.ZERO) < 0) {
                    redirectAttributes.addFlashAttribute("error", "La cantidad no puede ser negativa");
                    return "redirect:/admin/inventario";
                }
                
                inventario.setStock(cantidad);
                inventarioRepository.save(inventario);
                
                redirectAttributes.addFlashAttribute("success", "Stock actualizado exitosamente");
            } else {
                redirectAttributes.addFlashAttribute("error", "Registro de inventario no encontrado");
            }
            
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error al actualizar stock: " + e.getMessage());
        }
        
        return "redirect:/admin/inventario";
    }

// ===== GESTIÓN DE INSUMOS =====
@GetMapping("/insumos")
public String gestionInsumos(Model model) {

    model.addAttribute("titulo", "Gestión de Insumos - Sabor Peruano");

    List<Insumo> insumos = insumoRepository.findAll();
    model.addAttribute("insumos", insumos);

    if (!model.containsAttribute("nuevoInsumo")) {
        model.addAttribute("nuevoInsumo", new Insumo());
    }

    return "admin/gestion-inventario";
}

@PostMapping("/insumos")
public String crearInsumo(@ModelAttribute("nuevoInsumo") Insumo insumo,
                          RedirectAttributes redirectAttributes) {

    try {
        // VALIDACIONES
        if (insumo.getNombre() == null || insumo.getNombre().trim().isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "El nombre es obligatorio");
            return "redirect:/admin/insumos";
        }

        if (insumo.getStock() == null || insumo.getStock().compareTo(BigDecimal.ZERO) < 0) {
            redirectAttributes.addFlashAttribute("error", "El stock debe ser ≥ 0");
            return "redirect:/admin/insumos";
        }

        if (insumo.getUnidad() == null || insumo.getUnidad().trim().isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "La unidad es obligatoria");
            return "redirect:/admin/insumos";
        }

        if (insumo.getEstado() == null) {
            insumo.setEstado(true);
        }

        insumoRepository.save(insumo);

        redirectAttributes.addFlashAttribute("success", "Insumo creado correctamente");

    } catch (Exception e) {
        redirectAttributes.addFlashAttribute("error",
                "Error al crear insumo: " + e.getMessage());
    }

    return "redirect:/admin/insumos";
}

// Actualizar stock del insumo
@PostMapping("/insumos/actualizar/{id}")  // <-- ruta cambiada
public String actualizarStock(@PathVariable Integer id,
                              @RequestParam("cantidad") BigDecimal cantidad,
                              RedirectAttributes redirectAttributes) {
    try {
        Insumo insumo = insumoRepository.findById(id).orElse(null);

        if (insumo == null) {
            redirectAttributes.addFlashAttribute("error", "El insumo no existe.");
            return "redirect:/admin/insumos";
        }

        if (cantidad.compareTo(BigDecimal.ZERO) < 0) {
            redirectAttributes.addFlashAttribute("error", "El stock no puede ser negativo.");
            return "redirect:/admin/insumos";
        }

        insumo.setStock(cantidad);
        insumoRepository.save(insumo);

        redirectAttributes.addFlashAttribute("success", "Stock actualizado correctamente.");
    } catch (Exception e) {
        redirectAttributes.addFlashAttribute("error", "Error al actualizar stock: " + e.getMessage());
    }

    return "redirect:/admin/insumos";
}

// Marcar insumo como agotado
@PostMapping("/insumos/agotado/{id}")  // <-- ruta cambiada
public String marcarAgotado(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
    try {
        Insumo insumo = insumoRepository.findById(id).orElse(null);

        if (insumo == null) {
            redirectAttributes.addFlashAttribute("error", "El insumo no existe.");
            return "redirect:/admin/insumos";
        }

        insumo.setEstado(false); // marcar como agotado
        insumoRepository.save(insumo);

        redirectAttributes.addFlashAttribute("success", "Insumo marcado como agotado.");
    } catch (Exception e) {
        redirectAttributes.addFlashAttribute("error", "Error al actualizar el estado: " + e.getMessage());
    }

    return "redirect:/admin/insumos";
}

// Marcar insumo como disponible
@PostMapping("/insumos/disponible/{id}")  // <-- ruta cambiada
public String marcarDisponible(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
    try {
        Insumo insumo = insumoRepository.findById(id).orElse(null);

        if (insumo == null) {
            redirectAttributes.addFlashAttribute("error", "El insumo no existe.");
            return "redirect:/admin/insumos";
        }

        insumo.setEstado(true); // marcar como disponible
        insumoRepository.save(insumo);

        redirectAttributes.addFlashAttribute("success", "Insumo marcado como disponible.");
    } catch (Exception e) {
        redirectAttributes.addFlashAttribute("error", "Error al actualizar el estado: " + e.getMessage());
    }

    return "redirect:/admin/insumos";
}

// Eliminar insumo
@PostMapping("/insumos/eliminar/{id}")
public String eliminarInsumo(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
    try {
        Insumo insumo = insumoRepository.findById(id).orElse(null);

        if (insumo == null) {
            redirectAttributes.addFlashAttribute("error", "El insumo no existe.");
            return "redirect:/admin/insumos";
        }

        insumoRepository.delete(insumo);
        redirectAttributes.addFlashAttribute("success", "Insumo eliminado correctamente.");

    } catch (Exception e) {
        redirectAttributes.addFlashAttribute("error", "Error al eliminar insumo: " + e.getMessage());
    }

    return "redirect:/admin/insumos";
}





    // ===== GESTIÓN DE SUCURSALES =====
    @GetMapping("/sucursales")
    public String gestionSucursales(Model model) {
        model.addAttribute("titulo", "Gestión de Sucursales - Sabor Peruano");
        
        List<Sucursal> sucursales = sucursalRepository.findAll();
        model.addAttribute("sucursales", sucursales);
        
        if (!model.containsAttribute("nuevaSucursal")) {
            model.addAttribute("nuevaSucursal", new Sucursal());
        }
        
        return "admin/gestion-sucursales";
    }

    @PostMapping("/sucursales")
    public String crearSucursal(@ModelAttribute Sucursal sucursal, RedirectAttributes redirectAttributes) {
        try {
            // Validaciones
            if (sucursal.getNombre() == null || sucursal.getNombre().trim().isEmpty()) {
                redirectAttributes.addFlashAttribute("error", "El nombre de la sucursal es requerido");
                return "redirect:/admin/sucursales";
            }
            
            if (sucursal.getDireccion() == null || sucursal.getDireccion().trim().isEmpty()) {
                redirectAttributes.addFlashAttribute("error", "La dirección de la sucursal es requerida");
                return "redirect:/admin/sucursales";
            }
            
            // Asegurar que el estado sea true por defecto
            if (sucursal.getEstado() == null) {
                sucursal.setEstado(true);
            }
            
            sucursalRepository.save(sucursal);
            redirectAttributes.addFlashAttribute("success", "Sucursal creada exitosamente");
            
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error al crear la sucursal: " + e.getMessage());
        }
        
        return "redirect:/admin/sucursales";
    }

    // Cambiar estado de la sucursal (activo/inactivo)
@PostMapping("/sucursales/toggle-estado/{id}")
public String toggleEstadoSucursal(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
    try {
        Sucursal sucursal = sucursalRepository.findById(id).orElse(null);
        if (sucursal == null) {
            redirectAttributes.addFlashAttribute("error", "Sucursal no encontrada.");
            return "redirect:/admin/sucursales";
        }

        // Cambiar estado de true a false o viceversa
        sucursal.setEstado(!sucursal.getEstado());
        sucursalRepository.save(sucursal);

        redirectAttributes.addFlashAttribute("success", "Estado de la sucursal actualizado correctamente.");
    } catch (Exception e) {
        redirectAttributes.addFlashAttribute("error", "Error al actualizar estado: " + e.getMessage());
    }
    return "redirect:/admin/sucursales";
}


    // ===== GESTIÓN DE USUARIOS =====
    @GetMapping("/usuarios")
    public String gestionUsuarios(Model model) {
        model.addAttribute("titulo", "Gestión de Usuarios - Sabor Peruano");
        
        List<Usuario> usuarios = usuarioRepository.findAll();
        List<Sucursal> sucursales = sucursalRepository.findAll();
        
        model.addAttribute("usuarios", usuarios);
        model.addAttribute("sucursales", sucursales);
        
        if (!model.containsAttribute("nuevoUsuario")) {
            model.addAttribute("nuevoUsuario", new Usuario());
        }
        
        return "admin/gestion-usuarios";
    }

    @PostMapping("/usuarios")
    public String crearUsuario(@ModelAttribute Usuario usuario, RedirectAttributes redirectAttributes) {
        try {
            // Validaciones (usar los nombres correctos)
            if (usuario.getUsuario() == null || usuario.getUsuario().trim().isEmpty()) {
                redirectAttributes.addFlashAttribute("error", "El nombre de usuario es requerido");
                return "redirect:/admin/usuarios";
            }
            
            if (usuario.getClave() == null || usuario.getClave().trim().isEmpty()) {
                redirectAttributes.addFlashAttribute("error", "La contraseña es requerida");
                return "redirect:/admin/usuarios";
            }
            
            if (usuario.getNombres() == null || usuario.getNombres().trim().isEmpty()) {
                redirectAttributes.addFlashAttribute("error", "Los nombres son requeridos");
                return "redirect:/admin/usuarios";
            }
            
            if (usuario.getApellidos() == null || usuario.getApellidos().trim().isEmpty()) {
                redirectAttributes.addFlashAttribute("error", "Los apellidos son requeridos");
                return "redirect:/admin/usuarios";
            }
            
            if (usuario.getRol() == null || usuario.getRol().trim().isEmpty()) {
                redirectAttributes.addFlashAttribute("error", "El rol es requerido");
                return "redirect:/admin/usuarios";
            }
            
            // Verificar si el usuario ya existe
            if (usuarioRepository.findByUsuario(usuario.getUsuario()).isPresent()) {
                redirectAttributes.addFlashAttribute("error", "El nombre de usuario ya existe");
                return "redirect:/admin/usuarios";
            }
            
            // Asegurar que el estado sea true por defecto
            if (usuario.getEstado() == null) {
                usuario.setEstado(true);
            }
            
            usuarioRepository.save(usuario);
            redirectAttributes.addFlashAttribute("success", "Usuario creado exitosamente");
            
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error al crear el usuario: " + e.getMessage());
        }
        
        return "redirect:/admin/usuarios";
    }
    
    //gestion menu//
    @GetMapping("/menu")
public String gestionMenu(Model model) {
    model.addAttribute("titulo", "Gestión de Menú - Sabor Peruano");
    return "admin/gestion-menu";
}

// ===== GESTIÓN DE PEDIDOS =====
@GetMapping("/pedidos")
@Transactional(readOnly = true)
public String gestionPedidos(Model model) {
    model.addAttribute("titulo", "Gestión de Pedidos - Sabor Peruano");

    List<Pedido> pedidos = new ArrayList<>();
    try {
        pedidos = pedidoRepository.findAll();

        // Forzar inicialización de los datos de usuario y detalles
        for (Pedido pedido : pedidos) {
            if (pedido.getUsuario() != null) {
                pedido.getUsuario().getNombres(); // inicializa nombres
                pedido.getUsuario().getApellidos(); // inicializa apellidos
            }

            // Inicializa detalles y platos
            if (pedido.getDetalles() != null) {
                pedido.getDetalles().forEach(detalle -> {
                    if (detalle.getPlato() != null) {
                        detalle.getPlato().getNombre(); // inicializa nombre del plato
                    }
                });
            }
        }
    } catch (Exception e) {
        model.addAttribute("error", "No se pudieron cargar los pedidos");
    }

    model.addAttribute("pedidos", pedidos);
    return "admin/pedidos";
}





    // ===== CONFIGURACIÓN =====
    @GetMapping("/configuracion")
    public String configuracion(Model model) {
        model.addAttribute("titulo", "Configuración - Sabor Peruano");
        return "admin/configuracion";
    }
}