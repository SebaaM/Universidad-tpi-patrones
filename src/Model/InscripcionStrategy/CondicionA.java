package Model.InscripcionStrategy;

import Model.PlanDeEstudio;

public class CondicionA implements CondicionInscripcion {

    @Override
    public boolean revisarCondicion(PlanDeEstudio plan) {
        return false;
    }
}
