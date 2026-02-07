package Model.InscripcionStrategy;

import Model.Estudiante;
import Model.Materia;
import Model.PlanDeEstudio;

public interface CondicionInscripcion {
    public boolean revisarCondicion(PlanDeEstudio plan, Materia mat, Estudiante est);
}
