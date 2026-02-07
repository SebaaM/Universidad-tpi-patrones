package Model.EstadoState;

import Model.Cursada;

public class CursadaAprobada extends EstadoCursada {

    public CursadaAprobada(Cursada cursada){
        cursada.setCursadaAprobada(true);
        // imprimir datos de la materia?
        System.out.println("Cursada aprobada");
    }


}
