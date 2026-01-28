package Model;

import Model.EstadoState.EstadoCursada;
import Model.EstadoState.Inscripto;

public class Cursada {

    private EstadoCursada estado;

    public Cursada() {
        this.estado = new Inscripto();
    }

    public void setEstado(EstadoCursada estado) {
        this.estado = estado;
    }

    public void cargarParcial(double nota) {
        this.estado.cargarParcial(nota, this);
    }

    public void finalizarCursada() {
        this.estado.finalizarCursada(this);
    }

    public void cargarNotaFinal(Double nota) {
        this.estado.cargarNotaFinal(nota, this);
    }

    public boolean cursadaTerminada() {
        return this.estado.cursadaTerminada(this.estado);
    }
}
