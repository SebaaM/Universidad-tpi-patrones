package Model;

import Model.EstadoState.EstadoCursada;
import Model.EstadoState.Inscripto;

import java.util.Date;

public class Cursada {
    private Materia materia;
    private int anioCursada;
    private EstadoCursada estado;

    public Cursada() {
        this.estado = new Inscripto();
        this.anioCursada = new Date().getYear() + 1900;
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
