package Model.InscripcionStrategy;

import Model.Estudiante;
import Model.Materia;
import Model.PlanDeEstudio;

public class CondicionC implements CondicionInscripcion{

    /*
    aprobo todas las cursadas de las correlativas y los finales
     de todas las materias de 5 cuatrimestres previos al que se quiere anotar.
     */

    public CondicionC() {
    }

    @Override
    public boolean revisarCondicion(PlanDeEstudio plan, Materia mat, Estudiante est) {
        return false;
    }

}
