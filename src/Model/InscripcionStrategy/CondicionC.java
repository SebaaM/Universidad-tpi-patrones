package Model.InscripcionStrategy;

import Model.PlanDeEstudio;

public class CondicionC implements CondicionInscripcion{
    @Override
    public boolean revisarCondicion(PlanDeEstudio plan) {
        return false;
    }
}
