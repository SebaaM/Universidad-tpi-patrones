package Model.EstadoState;

import Model.Cursada;

public class ParcialAprobado extends EstadoCursada{
    public ParcialAprobado() {
        System.out.println("parcial aprobado");
    }

    @Override
    public void cargarNotaFinal(double notaFinal, Cursada cursada) {
        if (notaFinal >= 4) {
            cursada.setCursadaAprobadaTotal(true);
            cursada.setEstado(new CursadaAprobada(cursada));
        } else {
            
            cursada.setEstado(new CursadaDesaprobada(cursada));
        }
    }

}
