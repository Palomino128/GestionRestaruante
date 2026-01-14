-- Insertar datos iniciales
INSERT IGNORE INTO sucursales (nombre, direccion, telefono, distrito, activa) VALUES 
('Sucursal Central', 'Av. Ejemplo 123, Arequipa', '054123456', 'Centro', true),
('Sucursal Cerro Colorado', 'Av. Cerro Colorado 456, Arequipa', '054654321', 'Cerro Colorado', true);

INSERT IGNORE INTO categorias (nombre, descripcion, activa) VALUES 
('Entradas', 'Platos de entrada y aperitivos', true),
('Platos de Fondo', 'Platos principales', true),
('Bebidas', 'Bebidas y refrescos', true),
('Postres', 'Postres y dulces', true);

INSERT IGNORE INTO platos (nombre, descripcion, precio, disponible, categoria_id, imagen_url) VALUES 
('Lomo Saltado', 'Delicioso lomo saltado con arroz y papas fritas', 25.00, true, 2, 'https://images.unsplash.com/photo-1588168333986-5078d3ae3976?ixlib=rb-4.0.3&w=400'),
('Pollo a la Brasa 1/4', 'Pollo a la brasa con papas fritas y ensalada', 18.00, true, 2, 'https://images.unsplash.com/photo-1604503468506-a8da13d82791?ixlib=rb-4.0.3&w=400'),
('Ceviche Clásico', 'Ceviche de pescado con camote y choclo', 22.00, true, 1, 'https://images.unsplash.com/photo-1626803775157-8e81645ed0c9?ixlib=rb-4.0.3&w=400'),
('Arroz Chaufa', 'Arroz chaufa de pollo con verduras', 20.00, true, 2, 'https://images.unsplash.com/photo-1563245372-f21724e3856d?ixlib=rb-4.0.3&w=400'),
('Tallarines Verdes', 'Tallarines verdes con bistec', 19.00, true, 2, 'https://images.unsplash.com/photo-1551183053-bf91a1d81141?ixlib=rb-4.0.3&w=400');

INSERT IGNORE INTO insumos (nombre, descripcion, stock_minimo, unidad_medida) VALUES 
('Arroz', 'Arroz extra', 5, 'kg'),
('Pollo', 'Pollo fresco', 10, 'kg'),
('Cebolla', 'Cebolla roja', 8, 'kg'),
('Aceite', 'Aceite vegetal', 5, 'litro'),
('Cilantro', 'Cilantro fresco', 5, 'atado'),
('Sal', 'Sal marina', 10, 'kg');

INSERT IGNORE INTO inventarios (cantidad_disponible, insumo_id, sucursal_id) VALUES 
(0, 1, 1),
(3, 2, 1),
(5, 3, 1),
(10, 4, 1),
(2, 5, 1),
(50, 6, 1);

INSERT IGNORE INTO usuarios (username, password, email, nombre, rol, activo, sucursal_id) VALUES 
('admin', 'admin123', 'admin@saborperuano.com', 'Administrador Principal', 'ADMIN', true, 1),
('empleado1', 'empleado123', 'empleado1@saborperuano.com', 'Empleado Sucursal', 'EMPLEADO', true, 1);