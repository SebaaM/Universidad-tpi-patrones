package Model.InscripcionStrategy;

import Model.PlanDeEstudio;

public class CondicionD implements CondicionInscripcion {
    @Override
    public boolean revisarCondicion(PlanDeEstudio plan) {
        return false;
    }
}
