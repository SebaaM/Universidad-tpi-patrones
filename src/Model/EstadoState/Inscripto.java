package Model.EstadoState;

import Model.Cursada;

public class Inscripto extends EstadoCursada{

    public Inscripto() {}

    @Override
    public void cargarParcial(double nota, Cursada cursada){
        if (nota >= 8){
            cursada.setEstado(new Promocionada(cursada));
        }
        else if (nota >= 4){
            cursada.setEstado(new ParcialAprobado());
        }
        else {
            cursada.setEstado(new ParcialDesaprobado(cursada));
        }
    }
}
