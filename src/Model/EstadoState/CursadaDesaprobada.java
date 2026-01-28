package Model.EstadoState;

import Model.Cursada;

public class CursadaDesaprobada extends EstadoCursada{

    public CursadaDesaprobada() {
        System.out.println("Cursada Desaprobada");
    }

    @Override
    public void finalizarCursada(Cursada cursada) {
        cursada.setEstado(new CursadaDesaprobada());
    }

}
