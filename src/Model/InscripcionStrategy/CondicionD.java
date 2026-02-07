package Model.InscripcionStrategy;

import Model.Estudiante;
import Model.Materia;
import Model.PlanDeEstudio;

public class CondicionD implements CondicionInscripcion {
    /*
    aprobo las cursadas de las correlativa y los finales
    de todas las materias de 3 cuatrimestres prevuos al que se quiere anotar.
     */

    public CondicionD() {
    }

    @Override
    public boolean revisarCondicion(PlanDeEstudio plan, Materia mat, Estudiante est) {
        return false;
    }
}
