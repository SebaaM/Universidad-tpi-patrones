import Model.BuilderPlan.PlanBuild;
import Model.*;
import Model.EstadoState.CursadaAprobada;
import Model.EstadoState.Inscripto;
import Model.InscripcionStrategy.CondicionA;
import Model.InscripcionStrategy.CondicionB;
import Model.InscripcionStrategy.CondicionC;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class PrecargaMain {

    public static void precargarDatos(Universidad universidad, JTextArea consola) {
        precargarEstrategias(universidad, consola);
        precargarEstudiantes(universidad, consola);
        precargarCarrerasYMaterias(universidad, consola);
        crearEjemplosCompletos(universidad, consola);
    }

    private static void precargarEstrategias(Universidad universidad, JTextArea consola) {
        consola.append("=== Estrategias de inscripción configuradas ===\n");
    }

    private static void precargarEstudiantes(Universidad universidad, JTextArea consola) {
        // Precargar 8 estudiantes
        String[] nombres = {"Juan", "María", "Carlos", "Ana", "Luis", "Sofía", "Diego", "Laura"};
        String[] apellidos = {"García", "Rodríguez", "Martínez", "López", "González", "Pérez", "Sánchez", "Ramírez"};

        for (int i = 0; i < 8; i++) {
            long dni = 30000000L + i * 1000; // DNI: 30000000, 30001000, 30002000, etc.
            Estudiante estudiante = new Estudiante(nombres[i], apellidos[i], dni);
            universidad.agregarEstudiante(estudiante);
        }

        consola.append("=== Estudiantes precargados ===\n");
        universidad.getEstudiantes().forEach(e -> consola.append(e.toString() + "\n"));
        consola.append("\n");
    }

    private static void precargarCarrerasYMaterias(Universidad universidad, JTextArea consola) {
        // Crear materias base (15 materias para 3 carreras de 5 cada una)
        List<Materia> todasLasMaterias = new ArrayList<>();

        // Materias para Ingeniería (1-5)
        todasLasMaterias.add(new Materia("Matemática I", 101, 1));
        todasLasMaterias.add(new Materia("Física I", 102, 1));
        todasLasMaterias.add(new Materia("Programación I", 103, 1));
        todasLasMaterias.add(new Materia("Álgebra Lineal", 104, 2));
        todasLasMaterias.add(new Materia("Cálculo Diferencial", 105, 2));

        // Materias para Licenciatura en Sistemas (6-10)
        todasLasMaterias.add(new Materia("Base de Datos I", 106, 1));
        todasLasMaterias.add(new Materia("Redes de Datos", 107, 2));
        todasLasMaterias.add(new Materia("Ingeniería de Software", 108, 3));
        todasLasMaterias.add(new Materia("Inteligencia Artificial", 109, 4));
        todasLasMaterias.add(new Materia("Seguridad Informática", 110, 5));

        // Materias para Administración (11-15)
        todasLasMaterias.add(new Materia("Introducción a la Administración", 111, 1));
        todasLasMaterias.add(new Materia("Contabilidad Básica", 112, 1));
        todasLasMaterias.add(new Materia("Economía I", 113, 2));
        todasLasMaterias.add(new Materia("Marketing", 114, 3));
        todasLasMaterias.add(new Materia("Gestión de Proyectos", 115, 4));

        // Agregar correlativas
        todasLasMaterias.get(4).getCorrelativas().add(todasLasMaterias.get(0));
        todasLasMaterias.get(7).getCorrelativas().add(todasLasMaterias.get(2));
        todasLasMaterias.get(9).getCorrelativas().add(todasLasMaterias.get(5));

        // Agregar todas las materias a la universidad
        for (Materia materia : todasLasMaterias) {
            universidad.agregarMateria(materia);
        }

        // Crear las 3 carreras usando el Builder
        crearCarreraIngenieria(universidad, todasLasMaterias.subList(0, 5));
        crearCarreraSistemas(universidad, todasLasMaterias.subList(5, 10));
        crearCarreraAdministracion(universidad, todasLasMaterias.subList(10, 15));

        consola.append("=== Carreras y Materias precargadas ===\n");
        universidad.getCarreras().forEach(c -> {
            consola.append(c.toString() + "\n");
            consola.append("  Materias: " + c.getPlanEstudio().getMateriasObligatorias().size() + "\n");
        });
        consola.append("\n");
    }

    private static void crearCarreraIngenieria(Universidad universidad, List<Materia> materias) {
        PlanBuild builder = new PlanBuild();

        // Agregar 3 obligatorias y 2 optativas
        builder.agregarMateriaObligatoria(materias.get(0)); // Matemática I
        builder.agregarMateriaObligatoria(materias.get(1)); // Física I
        builder.agregarMateriaObligatoria(materias.get(2)); // Programación I
        builder.agregarMateriaOpcional(materias.get(3));     // Álgebra Lineal
        builder.agregarMateriaOpcional(materias.get(4));     // Cálculo Diferencial

        builder.setOptativasMinimas(1);
        builder.setEstrategiaInscripcion(new CondicionA());

        PlanDeEstudio plan = builder.devolverPlan();
        Carrera ingenieria = new Carrera("Ingeniería en Sistemas", 1);
        ingenieria.setPlanEstudio(plan);
        universidad.agregarCarrera(ingenieria);
    }

    private static void crearCarreraSistemas(Universidad universidad, List<Materia> materias) {
        PlanBuild builder = new PlanBuild();

        // Agregar 4 obligatorias y 1 optativa
        builder.agregarMateriaObligatoria(materias.get(0)); // Base de Datos I
        builder.agregarMateriaObligatoria(materias.get(1)); // Redes de Datos
        builder.agregarMateriaObligatoria(materias.get(2)); // Ingeniería de Software
        builder.agregarMateriaObligatoria(materias.get(3)); // Inteligencia Artificial
        builder.agregarMateriaOpcional(materias.get(4));     // Seguridad Informática

        builder.setOptativasMinimas(1);
        builder.setEstrategiaInscripcion(new CondicionB());

        PlanDeEstudio plan = builder.devolverPlan();
        Carrera sistemas = new Carrera("Licenciatura en Sistemas", 2);
        sistemas.setPlanEstudio(plan);
        universidad.agregarCarrera(sistemas);
    }

    private static void crearCarreraAdministracion(Universidad universidad, List<Materia> materias) {
        PlanBuild builder = new PlanBuild();

        // Agregar 2 obligatorias y 3 optativas
        builder.agregarMateriaObligatoria(materias.get(0)); // Introducción a la Administración
        builder.agregarMateriaObligatoria(materias.get(1)); // Contabilidad Básica
        builder.agregarMateriaOpcional(materias.get(2));     // Economía I
        builder.agregarMateriaOpcional(materias.get(3));     // Marketing
        builder.agregarMateriaOpcional(materias.get(4));     // Gestión de Proyectos

        builder.setOptativasMinimas(2);
        builder.setEstrategiaInscripcion(new CondicionC());

        PlanDeEstudio plan = builder.devolverPlan();
        Carrera administracion = new Carrera("Licenciatura en Administración", 3);
        administracion.setPlanEstudio(plan);
        universidad.agregarCarrera(administracion);
    }

    /// EJEMPLOS COMPLETOS DE INSCRIPCIONES Y ESTADOS
    ///

    private static void crearEjemplosCompletos(Universidad universidad, JTextArea consola) {
        // Obtener estudiantes precargados
        List<Estudiante> estudiantes = universidad.getEstudiantes();
        List<Carrera> carreras = universidad.getCarreras();

        // Ejemplo 1: Juan García - Ingeniería en Sistemas (Casi terminado, le falta 1 materia)
        if (estudiantes.size() >= 1 && carreras.size() >= 1) {
            Estudiante juan = estudiantes.get(0); // Juan García
            Carrera ingenieria = carreras.get(0); // Ingeniería en Sistemas
            
            // Inscribir en carrera
            juan.setCarrera(ingenieria);
            
            // Inscribir en todas las materias obligatorias y aprobarlas
            List<Materia> obligatorias = ingenieria.getPlanEstudio().getMateriasObligatorias();
            for (Materia materia : obligatorias) {
                Cursada cursada = new Cursada(materia);
                cursada.setEstado(new CursadaAprobada(cursada));
                juan.getCursadasInscriptas().add(cursada);
            }
            
            // Inscribir en 1 optativa y aprobarla (le falta aprobar la otra optativa)
            List<Materia> optativas = ingenieria.getPlanEstudio().getMateriasOptativas();
            if (optativas.size() >= 1) {
                Cursada cursadaOptativa = new Cursada(optativas.get(0));
                cursadaOptativa.setEstado(new CursadaAprobada(cursadaOptativa));
                juan.getCursadasInscriptas().add(cursadaOptativa);
            }
        }

        // Ejemplo 2: María Rodríguez - Licenciatura en Sistemas (En curso, algunas materias aprobadas)
        if (estudiantes.size() >= 2 && carreras.size() >= 2) {
            Estudiante maria = estudiantes.get(1); // María Rodríguez
            Carrera sistemas = carreras.get(1); // Licenciatura en Sistemas
            
            // Inscribir en carrera
            maria.setCarrera(sistemas);
            
            // Inscribir en algunas materias (no todas)
            List<Materia> obligatorias = sistemas.getPlanEstudio().getMateriasObligatorias();
            if (obligatorias.size() >= 2) {
                // Aprobar 2 materias obligatorias
                for (int i = 0; i < 2; i++) {
                    Cursada cursada = new Cursada(obligatorias.get(i));
                    cursada.setEstado(new CursadaAprobada(cursada));
                    maria.getCursadasInscriptas().add(cursada);
                }
                
                // Inscribir en 1 materia más (sin aprobar aún)
                Cursada cursadaEnCurso = new Cursada(obligatorias.get(2));
                cursadaEnCurso.setEstado(new Inscripto());
                maria.getCursadasInscriptas().add(cursadaEnCurso);
            }
        }

        // Ejemplo 3: Carlos Martínez - Licenciatura en Administración (Recién iniciado)
        if (estudiantes.size() >= 3 && carreras.size() >= 3) {
            Estudiante carlos = estudiantes.get(2); // Carlos Martínez
            Carrera administracion = carreras.get(2); // Licenciatura en Administración
            
            // Inscribir en carrera
            carlos.setCarrera(administracion);
            
            // Inscribir solo en 1 materia (sin aprobar)
            List<Materia> obligatorias = administracion.getPlanEstudio().getMateriasObligatorias();
            if (!obligatorias.isEmpty()) {
                Cursada cursada = new Cursada(obligatorias.get(0));
                cursada.setEstado(new Inscripto());
                carlos.getCursadasInscriptas().add(cursada);
            }
        }

        // Ejemplo 4: Ana López - Ingeniería en Sistemas (Carrera finalizada)
        if (estudiantes.size() >= 4 && carreras.size() >= 1) {
            Estudiante ana = estudiantes.get(3); // Ana López
            Carrera ingenieria = carreras.get(0); // Ingeniería en Sistemas
            
            // Inscribir en carrera
            ana.setCarrera(ingenieria);
            
            // Aprobar todas las materias obligatorias
            List<Materia> obligatorias = ingenieria.getPlanEstudio().getMateriasObligatorias();
            for (Materia materia : obligatorias) {
                Cursada cursada = new Cursada(materia);
                cursada.setEstado(new CursadaAprobada(cursada));
                ana.getCursadasInscriptas().add(cursada);
            }
            
            // Aprobar las optativas necesarias
            List<Materia> optativas = ingenieria.getPlanEstudio().getMateriasOptativas();
            int optativasNecesarias = ingenieria.getPlanEstudio().getCantOpcionales();
            for (int i = 0; i < Math.min(optativasNecesarias, optativas.size()); i++) {
                Cursada cursada = new Cursada(optativas.get(i));
                cursada.setEstado(new CursadaAprobada(cursada));
                ana.getCursadasInscriptas().add(cursada);
            }
        }

        // Mostrar resumen en consola
        consola.append("=== EJEMPLOS COMPLETOS CREADOS ===\n");
        for (int i = 0; i < Math.min(4, estudiantes.size()); i++) {
            Estudiante est = estudiantes.get(i);
            if (est.getCarrera() != null) {
                long aprobadas = est.getCursadasInscriptas().stream()
                        .filter(c -> c.isCursadaAprobadaTotal())
                        .count();
                consola.append(String.format("%s - %s: %d materias aprobadas\n", 
                        est.toString(), est.getCarrera().getNombre(), aprobadas));
            }
        }
        consola.append("\n");
    }

    public static void main(String[] args) {
        // Para testing independiente
        Universidad uni = Universidad.getInstance();
        JTextArea consola = new JTextArea();
        
        precargarDatos(uni, consola);
        
        System.out.println("Datos precargados exitosamente");
        System.out.println(consola.getText());
    }

}
