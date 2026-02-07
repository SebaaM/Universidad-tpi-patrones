package Model.EstadoState;

import Model.Cursada;

public class CursadaDesaprobada extends EstadoCursada{

    public CursadaDesaprobada(Cursada c) {
        System.out.println("Cursada Desaprobada");
        c.setCursadaAprobada(false);
    }
}
