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
├── Exceptions/           # Manejo de excepciones personalizadas
│   ├── UniversidadException.java
│   ├── CarreraException.java
│   ├── EstudianteException.java
│   ├── MateriaException.java
│   └── ValidacionException.java
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
├── PrecargaMain.java    # Clase de precarga de datos de ejemplo
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


### Manejo de Excepciones:
Sistema robusto de manejo de errores con excepciones personalizadas:
- **UniversidadException**: Clase base para excepciones del sistema
- **CarreraException**: Errores específicos de operaciones de carreras
- **EstudianteException**: Validaciones de datos de estudiantes
- **MateriaException**: Gestión de errores de materias
- **ValidacionException**: Errores de validación de datos de entrada

##  Pruebas Unitarias

### Suite de Tests Implementados:
El sistema incluye un conjunto completo de pruebas unitarias utilizando JUnit 5:

#### CursadaTest.java:
Valida el funcionamiento del patrón **State** en la gestión de cursadas:
- **cargarParcial()**: Verifica transiciones de estados al cargar notas de parciales
- **cargarNotaFinal()**: Testea la carga de notas finales según estado
- **finalizarCursada()**: Comprueba la finalización correcta de cursadas
- **isCursadaAprobada()**: Valida el estado de aprobación de cursadas
- **isCursadaAprobadaTotal()**: Verifica la aprobación completa (cursada + final)
- **setEstado()**: Testea cambios manuales de estado

#### DirectorStrategyTest.java:
Valida el funcionamiento del patrón **Strategy** en inscripciones:
- **revisarInscripcion()**: Comprueba las 5 condiciones de inscripción (CondiciónA-E)
- Validación de correlativas y cuatrimestres
- Verificación de estrategias según estado académico del estudiante

### Cobertura de Pruebas:
- **Patrón State**: 6 métodos testeados con múltiples escenarios
- **Patrón Strategy**: Validación de todas las condiciones de inscripción
- **Transiciones de estado**: Verificación completa de flujos académicos
- **Validaciones**: Testeo de casos límite y condiciones borde


### Arquitectura MVC:
- **Model**: Clases de dominio y lógica de negocio
- **View**: Interfaz Swing con archivos .form
- **Controller**: Gestión de eventos y validaciones

### Buenas Prácticas:
- Separación de responsabilidades
- Validaciones robustas
- Manejo de excepciones
- Logging de eventos
