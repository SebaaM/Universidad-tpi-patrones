package Model.EstadoState;

import Model.Cursada;

public class CursadaAprobada extends EstadoCursada {

    public CursadaAprobada(Cursada cursada){
        cursada.setCursadaAprobada(true);
        cursada.setCursadaAprobadaTotal(true);
    }


}
