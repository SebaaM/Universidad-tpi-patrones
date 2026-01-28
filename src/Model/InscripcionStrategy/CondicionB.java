package Model.InscripcionStrategy;

import Model.PlanDeEstudio;

public class CondicionB implements CondicionInscripcion {
    @Override
    public boolean revisarCondicion(PlanDeEstudio plan) {
        return false;
    }
}
