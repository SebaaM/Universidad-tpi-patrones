package Model.EstadoState;

import Model.Cursada;

public abstract class EstadoCursada {

    public void cargarParcial(double nota, Cursada cursada){
        System.out.println("no se puede cargar Parcial en el estado actual");
    }

    public void finalizarCursada(Cursada cursada) {
        System.out.println("no se puede finalizar la cursada en el estado actual.");
    }

    public void cargarNotaFinal(double notaFinal, Cursada cursada) {
        System.out.println("no se puede cargar nota en el estado actual.");
    }


    public boolean cursadaTerminada (EstadoCursada estadoActual) {
        // metodo probado.
        if  (estadoActual.getClass().getName().contains("CursadaAprobada")){
        return true;
        }
        else {
            // chequear.
            System.out.println("la materia se encuentra en el estado" + estadoActual.getClass().getName());
            return false;
        }
        /*
        EstadoCursada e = new CursadaAprobada();
        System.out.println(e.cursadaTerminada(e));
        true
        */
    }
}

