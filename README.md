# Sistema de Gestión Universitaria - TPI Patrones de Diseño

## 📋 Descripción
Sistema de gestión universitaria implementado en Java utilizando patrones de diseño para la administración de estudiantes, carreras, materias e inscripciones.

## 🏗️ Arquitectura y Patrones de Diseño

### Patrones Implementados:
- **State**: Gestión de estados de cursada (Inscripto, ParcialAprobado, ParcialDesaprobado, Promocionada, CursadaAprobada, CursadaDesaprobada)
- **Strategy**: Diferentes estrategias de inscripción según condiciones académicas (CondiciónA, CondiciónB, CondiciónC, CondiciónD, CondiciónE)
- **Builder**: Construcción flexible de planes de estudio
- **MVC**: Separación clara entre Modelo, Vista y Controladores

### Estructura del Proyecto:
```
src/
├── Controllers/          # Controladores (MVC)
│   ├── CarreraController.java
│   ├── EstudianteController.java
│   ├── MateriaController.java
│   └── CursadaController.java
├── Model/               # Modelo de datos
│   ├── Universidad.java
│   ├── Carrera.java
│   ├── Estudiante.java
│   ├── Materia.java
│   ├── Cursada.java
│   ├── PlanDeEstudio.java
│   ├── EstadoState/     # Patrón State
│   ├── InscripcionStrategy/ # Patrón Strategy
│   └── BuilderPlan/     # Patrón Builder
├── GuiUniversidad.java  # Interfaz gráfica principal
├── GuiUniversidad.form  # Configuración de UI
└── test/               # Pruebas unitarias
```

## 🛠️ Requisitos Técnicos

### Tecnologías:
- **Java**: JDK 8 o superior
- **Swing**: Para la interfaz gráfica
- **JUnit**: Para pruebas unitarias (opcional)

### Dependencias:
- jgoodies-forms-1.9.0.jar - Framework para formularios Swing
- jgoodies-common-1.8.1.jar - Utilidades comunes de JGoodies
- Bibliotecas estándar de Java


## 🎮 Uso del Sistema

### Flujo de Trabajo Típico:
1. **Crear materias**: Alta de materias con sus correlativas
2. **Crear carrera**: Definir plan de estudios y estrategias de inscripción
3. **Registrar estudiantes**: Dar de alta estudiantes en el sistema
4. **Inscribir a carrera**: Asignar estudiantes a sus carreras
5. **Inscribir a materias**: Seleccionar materias según el plan de estudios
6. **Gestionar cursadas**: Cargar notas y seguir el progreso académico

### Características de la Interfaz:
- **Panel de navegación**: Acceso rápido a todas las funcionalidades
- **Consola de eventos**: Registro en tiempo real de todas las operaciones
- **Validaciones automáticas**: Prevención de errores con mensajes claros
- **Filtrado inteligente**: Solo se muestran opciones relevantes



## 👥 Desarrollo y Contribución

### Arquitectura MVC:
- **Model**: Clases de dominio y lógica de negocio
- **View**: Interfaz Swing con archivos .form
- **Controller**: Gestión de eventos y validaciones

### Buenas Prácticas:
- Separación de responsabilidades
- Validaciones robustas
- Manejo de excepciones
- Logging de eventos
