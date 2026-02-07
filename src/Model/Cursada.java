package Model;

import Model.EstadoState.EstadoCursada;
import Model.EstadoState.Inscripto;

import java.util.Date;

public class Cursada {

    private Materia materia;
    private int anioCursada;
    private EstadoCursada estado;
    private boolean cursadaAprobada;


    public Cursada(Materia m) {
        this.materia = m;
        this.estado = new Inscripto();
        this.cursadaAprobada = false;
        this.anioCursada = new Date().getYear() + 1900;
    }

    public Cursada(Materia m, int anio){
        this.materia = m;
        this.estado = new Inscripto();
        this.cursadaAprobada = false;
        this.anioCursada = anio;
    }


    // cambio de State
    public void setEstado(EstadoCursada estado) {
        this.estado = estado;
    }

    public Materia getMateria() {
        return materia;
    }

    public void setMateria(Materia materia) {
        this.materia = materia;
    }

    public int getAnioCursada() {
        return anioCursada;
    }

    public void setAnioCursada(int anioCursada) {
        this.anioCursada = anioCursada;
    }

    public EstadoCursada getEstado() {
        return estado;
    }


    public void setCursadaAprobada(boolean cursadaAprobada) {
        this.cursadaAprobada = cursadaAprobada;
    }

    // METODOS

    public void cargarParcial(double nota) {
        this.estado.cargarParcial(nota, this);
    }

    public void finalizarCursada() {
        this.estado.finalizarCursada(this);
    }

    public void cargarNotaFinal(Double nota) {
        this.estado.cargarNotaFinal(nota, this);
    }

    public boolean isCursadaAprobada() {
        return cursadaAprobada;
    }



}
