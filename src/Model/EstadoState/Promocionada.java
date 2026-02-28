package Model.EstadoState;

import Model.Cursada;

public class Promocionada extends EstadoCursada{
    public Promocionada() {

    }

    @Override
    public void finalizarCursada(Cursada cursada) {
        //el estado AprobadoTotal ya esta en true.
        cursada.setEstado(new CursadaAprobada(cursada));
    }
}
