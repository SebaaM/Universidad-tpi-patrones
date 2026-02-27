package test;

import Model.*;
import Model.BuilderPlan.PlanBuild;
import Model.EstadoState.*;
import Model.InscripcionStrategy.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class DirectorStrategyTest {
    
    private DirectorStrategy director;
    private PlanDeEstudio plan;
    private Estudiante estudiante;
    private Materia materiaConCorrelativa;
    private Materia materiaCorrelativa;
    private Materia materiaCuatrimestre1;
    private Materia materiaCuatrimestre2;
    private Materia materiaCuatrimestre3;
    private Materia materiaCuatrimestre4;
    private Materia materiaCuatrimestre5;
    
    @BeforeEach
    void setUp() {
        director = new DirectorStrategy();
        estudiante = new Estudiante("Juan", "Perez", 12345678);
        
        // Crear materias con diferentes cuatrimestres
        materiaCorrelativa = new Materia("Algebra", 101, 1);  // Cuatrimestre 1
        materiaCuatrimestre1 = new Materia("Matematica I", 201, 2);  // Cuatrimestre 2
        materiaCuatrimestre2 = new Materia("Matematica II", 202, 3); // Cuatrimestre 3
        materiaCuatrimestre3 = new Materia("Matematica III", 203, 4); // Cuatrimestre 4
        materiaCuatrimestre4 = new Materia("Matematica IV", 204, 5);  // Cuatrimestre 5
        materiaCuatrimestre5 = new Materia("Matematica V", 205, 6);  // Cuatrimestre 6
        materiaConCorrelativa = new Materia("Programacion", 102, 7); // Cuatrimestre 7
        
        // Configurar correlativas
        materiaConCorrelativa.agregarCorrelativa(materiaCorrelativa);
        
        // Crear plan usando Builder Pattern
        plan = new PlanBuild()
                .agregarMateriaObligatoria(materiaCorrelativa)
                .agregarMateriaObligatoria(materiaCuatrimestre1)
                .agregarMateriaObligatoria(materiaCuatrimestre2)
                .agregarMateriaObligatoria(materiaCuatrimestre3)
                .agregarMateriaObligatoria(materiaCuatrimestre4)
                .agregarMateriaObligatoria(materiaCuatrimestre5)
                .agregarMateriaObligatoria(materiaConCorrelativa)
                .devolverPlan();
    }

    @Test
    void revisarInscripcion() {
        // Test 1: CondicionA - aprobo todas las cursadas correlativas (parciales)
        director.setStrategy(new CondicionA());
        
        // sin correlativas aprobadas
        assertFalse(director.revisarInscripcion(plan, materiaConCorrelativa, estudiante));
        
        // con parcial correlativa aprobada
        Cursada cursadaParcialAprobada = new Cursada(materiaCorrelativa);
        cursadaParcialAprobada.setCursadaAprobada(true);
        cursadaParcialAprobada.setEstado(new ParcialAprobado());
        estudiante.getCursadasInscriptas().add(cursadaParcialAprobada);
        
        assertTrue(director.revisarInscripcion(plan, materiaConCorrelativa, estudiante));
        
        // reseteo
        estudiante.getCursadasInscriptas().clear();
        
        // Test 2: CondicionB - aprobo todos los finales de las correlativas
        director.setStrategy(new CondicionB());
        
        // sin correlativas aprobadas (finales)
        assertFalse(director.revisarInscripcion(plan, materiaConCorrelativa, estudiante));
        
        // con fianal correlativa aprobada
        Cursada cursadaFinalAprobada = new Cursada(materiaCorrelativa);
        cursadaFinalAprobada.setCursadaAprobada(true);
        cursadaFinalAprobada.setCursadaAprobadaTotal(true);
        cursadaFinalAprobada.setEstado(new CursadaAprobada(cursadaFinalAprobada));
        estudiante.getCursadasInscriptas().add(cursadaFinalAprobada);
        
        assertTrue(director.revisarInscripcion(plan, materiaConCorrelativa, estudiante));
        
        // reset
        estudiante.getCursadasInscriptas().clear();
        
        // Test 3: CondicionC - aprobo todas las cursadas de correlativas Y los finales de todas las materias de 5 cuatrimestres previos
        director.setStrategy(new CondicionC());
        
        // agregar correlativa parcial aprobado
        Cursada cursadaCorrelativa = new Cursada(materiaCorrelativa);
        cursadaCorrelativa.setCursadaAprobada(true);
        cursadaCorrelativa.setEstado(new ParcialAprobado());
        estudiante.getCursadasInscriptas().add(cursadaCorrelativa);
        
        // agregar cursadas de cuatrimestres 2-6 con finales aprobados
        for (Materia materia : new Materia[]{materiaCuatrimestre1, materiaCuatrimestre2, materiaCuatrimestre3, materiaCuatrimestre4, materiaCuatrimestre5}) {
            Cursada cursadaConFinal = new Cursada(materia);
            cursadaConFinal.setCursadaAprobada(true);
            cursadaConFinal.setCursadaAprobadaTotal(true);
            cursadaConFinal.setEstado(new CursadaAprobada(cursadaConFinal));
            estudiante.getCursadasInscriptas().add(cursadaConFinal);
        }
        
        assertTrue(director.revisarInscripcion(plan, materiaConCorrelativa, estudiante));
        
        //clear
        estudiante.getCursadasInscriptas().clear();
        
        // Test 4: CondicionD - aprobo las cursadas de correlativas Y los finales de todas las materias de 3 cuatrimestres previos
        director.setStrategy(new CondicionD());
        
        // agregar correlativa parcial aprobado
        Cursada cursadaCorrelativaD = new Cursada(materiaCorrelativa);
        cursadaCorrelativaD.setCursadaAprobada(true);
        cursadaCorrelativaD.setEstado(new ParcialAprobado());
        estudiante.getCursadasInscriptas().add(cursadaCorrelativaD);
        
        // agregar cursadas de cuatrimestres 2-4 con finales aprobados
        for (Materia materia : new Materia[]{materiaCuatrimestre1, materiaCuatrimestre2, materiaCuatrimestre3}) {
            Cursada cursadaConFinal = new Cursada(materia);
            cursadaConFinal.setCursadaAprobada(true);
            cursadaConFinal.setCursadaAprobadaTotal(true);
            cursadaConFinal.setEstado(new CursadaAprobada(cursadaConFinal));
            estudiante.getCursadasInscriptas().add(cursadaConFinal);
        }
        
        assertTrue(director.revisarInscripcion(plan, materiaConCorrelativa, estudiante));
        
        // clear
        estudiante.getCursadasInscriptas().clear();
        
        // Test 5: CondicionE - aprobo los finales de correlativas Y los finales de todas las materias de 3 cuatrimestres previos
        director.setStrategy(new CondicionE());
        
        // agregar correlativa final aprobado
        Cursada cursadaCorrelativaE = new Cursada(materiaCorrelativa);
        cursadaCorrelativaE.setCursadaAprobada(true);
        cursadaCorrelativaE.setCursadaAprobadaTotal(true);
        cursadaCorrelativaE.setEstado(new CursadaAprobada(cursadaCorrelativaE));
        estudiante.getCursadasInscriptas().add(cursadaCorrelativaE);
        
        // agregar cursadas de cuatrimestres 2-4 con finales aprobados
        for (Materia materia : new Materia[]{materiaCuatrimestre1, materiaCuatrimestre2, materiaCuatrimestre3}) {
            Cursada cursadaConFinal = new Cursada(materia);
            cursadaConFinal.setCursadaAprobada(true);
            cursadaConFinal.setCursadaAprobadaTotal(true);
            cursadaConFinal.setEstado(new CursadaAprobada(cursadaConFinal));
            estudiante.getCursadasInscriptas().add(cursadaConFinal);
        }
        
        assertTrue(director.revisarInscripcion(plan, materiaConCorrelativa, estudiante));
        
        // Test 6: Estrategia nula - debe lanzar excepción
        director.setStrategy(null);
        assertThrows(RuntimeException.class, () -> {
            director.revisarInscripcion(plan, materiaConCorrelativa, estudiante);
        });
    }
}
