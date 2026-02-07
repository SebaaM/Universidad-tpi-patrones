package Model.EstadoState;

import Model.Cursada;

public class ParcialDesaprobado extends EstadoCursada{
    public ParcialDesaprobado() {
        System.out.println("Parcial Desaprobado");
    }

    @Override
    public void finalizarCursada(Cursada cursada){
        cursada.setEstado(new CursadaDesaprobada(cursada));
    }
}
