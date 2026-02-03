package Model.InscripcionStrategy;

import Model.PlanDeEstudio;

/*
Probar instanciando primero la estretgia concreta y luego setear la estretegia.

 */
public class DirectorStrategy {
    private CondicionInscripcion strategy;

    public DirectorStrategy() {
        // setear estrategia predeterminada?
        this.strategy = null;
    }

    public void setEstrategiaInscripcion(CondicionInscripcion strategy) {
        this.strategy = strategy;
    }

    public boolean revisarInscripcion(PlanDeEstudio plan){
        if(strategy == null){
            throw new RuntimeException("No se ha seleccionado una estrategia de inscripcion");
        }
        return strategy.revisarCondicion(plan);
    }
}
