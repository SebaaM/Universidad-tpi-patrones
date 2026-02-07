package Model.InscripcionStrategy;

import Model.Estudiante;
import Model.Materia;
import Model.PlanDeEstudio;

public class CondicionE implements CondicionInscripcion  {
    /*
    aprobo los finales de las correlativas y los finales de todas
     las materias de 3 cuatrimestres previos.
     */

    public CondicionE() {
    }

    @Override
    public boolean revisarCondicion(PlanDeEstudio plan, Materia mat, Estudiante est) {
        return false;
    }
}
