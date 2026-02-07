package Model.EstadoState;

import Model.Cursada;

public class Promocionada extends EstadoCursada{
    public Promocionada(Cursada c) {
        System.out.println("Cursada Promocionada");

    }

    @Override
    public void finalizarCursada(Cursada cursada) {
        cursada.setEstado(new CursadaAprobada(cursada));
        System.out.println("Cursada Finalizada");
    }
}
