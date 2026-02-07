package Model.InscripcionStrategy;

import Model.Estudiante;
import Model.Materia;
import Model.PlanDeEstudio;

public class CondicionB implements CondicionInscripcion {

    /*
    aprobo todos los finales de las correlativas
     */

    public CondicionB() {
    }

    @Override
    public boolean revisarCondicion(PlanDeEstudio plan, Materia mat, Estudiante est) {
        return false;
    }
}
