package Model.EstadoState;

import Model.Cursada;

public class CursadaDesaprobada extends EstadoCursada{

    public CursadaDesaprobada(Cursada c) {
        c.setCursadaAprobada(false);
    }
}
