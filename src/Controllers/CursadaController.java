package Controllers;

import Model.Cursada;
import Model.Estudiante;
import Model.Materia;

public class CursadaController {
    
    public static void main(String[] args) {
        new CursadaController().demostracionState();
    }
    
    public void demostracionState() {
        // Demostracion de uso del State.


        System.out.println("Demostracion en CursadaController - Patron State. ");
        // materias
        Materia algebra = new Materia("Algebra Lineal", 101, 1);
        Materia programacion = new Materia("Programacion Orientada a Objetos", 102, 2);
        Materia basesDatos = new Materia("Bases de Datos", 103, 3);
        
        // estudiantes
        Estudiante juan = new Estudiante("Juan", "Perez", 12345678);
        Estudiante Maria = new Estudiante("Maria", "Gonzalez", 87654321);
        
        // cursadas para diferentes materias
        Cursada algebraCursada = new Cursada(algebra);
        Cursada programacionCursada = new Cursada(programacion);
        Cursada BD1Cursada = new Cursada(basesDatos);
        Cursada AlgebraCursada2 = new Cursada(algebra);
        
        // agregar cursadas 
        juan.getCursadasInscriptas().add(algebraCursada);
        juan.getCursadasInscriptas().add(programacionCursada);
        Maria.getCursadasInscriptas().add(BD1Cursada);
        Maria.getCursadasInscriptas().add(AlgebraCursada2);
        
        System.out.println("estado inicial - todos inscriptos");
        System.out.println("=====================================");
        mostrarEstadoCursada(juan, algebraCursada);
        mostrarEstadoCursada(juan, programacionCursada);
        mostrarEstadoCursada(Maria, BD1Cursada);
        mostrarEstadoCursada(Maria, AlgebraCursada2);
        
        System.out.println("\n\ncaso 1: juan rinde parcial de algebra con nota 9 (promocion)");
        System.out.println("================================================================");
        System.out.println("estado antes: " + algebraCursada.getEstado().getClass().getSimpleName());
        algebraCursada.cargarParcial(9.0);
        System.out.println("estado despues: " + algebraCursada.getEstado().getClass().getSimpleName());
        mostrarEstadoCursada(juan, algebraCursada);
        
        System.out.println("\n\ncaso 2: juan rinde parcial de programacion con nota 6 (aprobado)");
        System.out.println("=====================================================================");
        System.out.println("estado antes: " + programacionCursada.getEstado().getClass().getSimpleName());
        programacionCursada.cargarParcial(6.0);
        System.out.println("estado despues: " + programacionCursada.getEstado().getClass().getSimpleName());
        mostrarEstadoCursada(juan, programacionCursada);
        
        System.out.println("\n\ncaso 3: juan rinde final de programacion con nota 7 (aprobado total)");
        System.out.println("=========================================================================");
        System.out.println("estado antes: " + programacionCursada.getEstado().getClass().getSimpleName());
        programacionCursada.cargarNotaFinal(7.0);
        System.out.println("estado despues: " + programacionCursada.getEstado().getClass().getSimpleName());
        mostrarEstadoCursada(juan, programacionCursada);
        
        System.out.println("\n\ncaso 4: maria rinde parcial de bases de datos con nota 3 (desaprobado)");
        System.out.println("===========================================================================");
        System.out.println("estado antes: " + BD1Cursada.getEstado().getClass().getSimpleName());
        BD1Cursada.cargarParcial(3.0);
        System.out.println("estado despues: " + BD1Cursada.getEstado().getClass().getSimpleName());
        mostrarEstadoCursada(Maria, BD1Cursada);
        
        System.out.println("\n\ncaso 5: maria finaliza cursada desaprobada de bases de datos");
        System.out.println("================================================================");
        System.out.println("estado antes: " + BD1Cursada.getEstado().getClass().getSimpleName());
        BD1Cursada.finalizarCursada();
        System.out.println("estado despues: " + BD1Cursada.getEstado().getClass().getSimpleName());
        mostrarEstadoCursada(Maria, BD1Cursada);
        
        System.out.println("\n\ncaso 6: maria rinde parcial de algebra con nota 2 (desaprobado)");
        System.out.println("=====================================================================");
        System.out.println("estado antes: " + AlgebraCursada2.getEstado().getClass().getSimpleName());
        AlgebraCursada2.cargarParcial(2.0);
        System.out.println("estado despues: " + AlgebraCursada2.getEstado().getClass().getSimpleName());
        mostrarEstadoCursada(Maria, AlgebraCursada2);
        
        System.out.println("\n\ncaso 7: intento de cargar nota final en estado inscripto (deberia fallar)");
        System.out.println("=============================================================================");
        Cursada cursadaPrueba = new Cursada(algebra);
        System.out.println("estado actual: " + cursadaPrueba.getEstado().getClass().getSimpleName());
        System.out.println("intentando cargar nota final...");
        cursadaPrueba.cargarNotaFinal(8.0);
        
        System.out.println("\n\ncaso 8: juan finaliza cursada promocionada de algebra");
        System.out.println("==========================================================");
        System.out.println("estado antes: " + algebraCursada.getEstado().getClass().getSimpleName());
        algebraCursada.finalizarCursada();
        System.out.println("estado despues: " + algebraCursada.getEstado().getClass().getSimpleName());
        mostrarEstadoCursada(juan, algebraCursada);
        
        System.out.println("\n\nresumen final de estados");
        System.out.println("=========================");
        System.out.println("Juan Perez:");
        System.out.println("  - Algebra Lineal: " + algebraCursada.getEstado().getClass().getSimpleName() + 
                          " (aprobada: " + algebraCursada.isCursadaAprobada() + 
                          ", aprobada total: " + algebraCursada.isCursadaAprobadaTotal() + ")");
        System.out.println("  - Programacion: " + programacionCursada.getEstado().getClass().getSimpleName() + 
                          " (aprobada: " + programacionCursada.isCursadaAprobada() + 
                          ", aprobada total: " + programacionCursada.isCursadaAprobadaTotal() + ")");
        
        System.out.println("\nMaria Gonzalez:");
        System.out.println("  - Bases de Datos: " + BD1Cursada.getEstado().getClass().getSimpleName() +
                          " (aprobada: " + BD1Cursada.isCursadaAprobada() +
                          ", aprobada total: " + BD1Cursada.isCursadaAprobadaTotal() + ")");
        System.out.println("  - Algebra Lineal: " + AlgebraCursada2.getEstado().getClass().getSimpleName() + 
                          " (aprobada: " + AlgebraCursada2.isCursadaAprobada() + 
                          ", aprobada total: " + AlgebraCursada2.isCursadaAprobadaTotal() + ")");
        
        System.out.println("\n=== fin de la demostracion ===");
    }
    
    private static void mostrarEstadoCursada(Estudiante estudiante, Cursada cursada) {
        System.out.printf("estudiante: %s | materia: %s | estado: %s | aprobada: %s | aprobada total: %s | anio: %d%n",
                estudiante.toString(),
                cursada.getMateria().toString(),
                cursada.getEstado().getClass().getSimpleName(),
                cursada.isCursadaAprobada(),
                cursada.isCursadaAprobadaTotal(),
                cursada.getAnioCursada());
    }
}
