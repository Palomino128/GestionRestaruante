🍽️ Sistema de Gestión para Restaurantes
📋 Descripción del Proyecto
Sistema web completo para la gestión integral de restaurantes, desarrollado como proyecto del curso Marcos de Desarrollo Web (Ciclo Agosto 2025 - UTP). La aplicación permite administrar todas las operaciones de un restaurante desde una plataforma centralizada.

✨ Características Principales
👥 Gestión de Usuarios y Roles
Administrador: Control total del sistema

Gerente: Gestión operativa y reportes

Mesero: Tomar pedidos y gestionar mesas

Cocinero: Ver y preparar órdenes

Cajero: Facturación y cierre de caja

📊 Módulos del Sistema
✅ Gestión de Mesas: Reservas, asignación y estado en tiempo real

✅ Sistema de Pedidos: Tomar órdenes, modificar y enviar a cocina

✅ Control de Inventario: Stock de ingredientes y alertas de reposición

✅ Menú Digital: Categorías, precios y disponibilidad

✅ Facturación Electrónica: Generación de comprobantes

✅ Reportes Avanzados: Ventas, inventario y rendimiento

✅ Dashboard Interactivo: Métricas clave en tiempo real

🛠️ Stack Tecnológico
Frontend
HTML5 - Estructura semántica

CSS3 - Estilos y diseño responsive

JavaScript (ES6+) - Interactividad

Bootstrap 5 - Framework CSS

Chart.js - Gráficos y visualizaciones

Backend
Python - Lógica principal

Django - Framework web (o Flask según tu implementación)

REST API - Arquitectura de servicios

Base de Datos
PostgreSQL - Base de datos relacional

SQLAlchemy - ORM (si usas Flask)

Django ORM - (si usas Django)

Herramientas Adicionales
Git - Control de versiones

Docker - Contenerización (opcional)

JWT - Autenticación por tokens

Celery - Tareas asíncronas (para reportes)

🚀 Instalación y Configuración
Requisitos Previos
Python 3.8+

PostgreSQL 12+

Node.js 14+ (para assets frontend)

Git

Pasos de Instalación
bash
# 1. Clonar el repositorio
git clone https://github.com/Palomino128/GestionRestaurante.git
cd GestionRestaurante

# 2. Crear entorno virtual (Python)
python -m venv venv

# 3. Activar entorno virtual
# Windows:
venv\Scripts\activate
# Mac/Linux:
source venv/bin/activate

# 4. Instalar dependencias
pip install -r requirements.txt

# 5. Configurar base de datos
# Crear archivo .env con:
DATABASE_URL=postgresql://usuario:contraseña@localhost:5432/gestion_restaurante
SECRET_KEY=tu-clave-secreta-aqui
DEBUG=True

# 6. Ejecutar migraciones
python manage.py migrate  # Para Django
# o
flask db upgrade  # Para Flask

# 7. Crear superusuario
python manage.py createsuperuser  # Django
# o
flask create-admin  # Flask (si está configurado)

# 8. Ejecutar servidor
python manage.py runserver  # Django
# o
flask run  # Flask
Instalación con Docker (Opcional)
bash
# Construir y ejecutar contenedores
docker-compose up -d

# Ver logs
docker-compose logs -f
📁 Estructura del Proyecto
text
gestion-restaurante/
│
├── app/                          # Aplicación principal
│   ├── __init__.py
│   ├── models/                  # Modelos de base de datos
│   │   ├── usuario.py
│   │   ├── mesa.py
│   │   ├── producto.py
│   │   └── pedido.py
│   │
│   ├── routes/                  # Rutas/Endpoints
│   │   ├── auth.py
│   │   ├── mesas.py
│   │   ├── pedidos.py
│   │   └── reportes.py
│   │
│   ├── templates/               # Plantillas HTML
│   │   ├── base.html
│   │   ├── dashboard.html
│   │   ├── mesas/
│   │   └── pedidos/
│   │
│   └── static/                  # Archivos estáticos
│       ├── css/
│       ├── js/
│       └── images/
│
├── tests/                       # Pruebas unitarias
├── docs/                        # Documentación
├── requirements.txt             # Dependencias Python
├── Dockerfile                   # Configuración Docker
├── docker-compose.yml           # Orquestación contenedores
├── .env.example                 # Variables de entorno ejemplo
└── README.md                    # Este archivo
🎯 Funcionalidades por Módulo
1. Gestión de Mesas
python
# Ejemplo de modelo Mesa
class Mesa:
    id: int
    numero: int
    capacidad: int
    estado: str  # 'disponible', 'ocupada', 'reservada'
    ubicacion: str  # 'interior', 'terraza', 'vip'
2. Sistema de Pedidos
Crear nuevo pedido

Añadir/eliminar items

Especificaciones especiales

Dividir cuenta

Enviar a cocina/bar

3. Panel de Cocina
Vista en tiempo real de órdenes

Marcar como "en preparación"

Notificar cuando está listo

Tiempos de preparación

4. Reportes
Ventas por horario

Productos más vendidos

Rendimiento de meseros

Control de inventario

Ganancias netas

🔐 Seguridad
Autenticación JWT con refresh tokens

CORS configurado

Helmet para seguridad HTTP

Rate limiting para prevención de ataques

Validación de entrada en todos los endpoints

📱 Responsive Design
Mobile First approach

Compatible con tablets y móviles

PWA (Progressive Web App) opcional

Modo offline para ciertas funcionalidades

🧪 Testing
bash
# Ejecutar pruebas
python -m pytest tests/

# Cobertura de código
pytest --cov=app tests/

# Pruebas de integración
pytest tests/integration/
