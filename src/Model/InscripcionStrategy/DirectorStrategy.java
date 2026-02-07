package Model.InscripcionStrategy;

import Model.Estudiante;
import Model.Materia;
import Model.PlanDeEstudio;

/*
Probar instanciando primero la estretgia concreta y luego setear la estretegia.

 */
public class DirectorStrategy {
    private CondicionInscripcion strategy;

    public DirectorStrategy() {
        // setear estrategia predeterminada?
        // sacar el null
        this.strategy = null;
    }

    public void setEstrategiaInscripcion(CondicionInscripcion strategy) {
        this.strategy = strategy;
    }

    public boolean revisarInscripcion(PlanDeEstudio plan, Materia mat, Estudiante est){
        if(strategy == null){
            // sacar el seteo nulo.
            throw new RuntimeException("No se ha seleccionado una estrategia de inscripcion");
        }
        return strategy.revisarCondicion(plan,mat, est);
    }
}
