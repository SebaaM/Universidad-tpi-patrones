package Model.InscripcionStrategy;

import Model.Estudiante;
import Model.Materia;
import Model.PlanDeEstudio;

import java.util.List;

public class CondicionA implements CondicionInscripcion {

    // Aprobo todas las cursadas correlativas
    public CondicionA(){

    }

    @Override
    public boolean revisarCondicion(PlanDeEstudio plan, Materia mat, Estudiante est) {



        return false;
    }
}
