package Model.EstadoState;

public abstract class EstadoCursada {

    public void cargarParcial(){
        System.out.println("no se puede cargar Parcial en el estado actual");
    }

    public void finalizarCursada() {
        System.out.println("no se puede finalizar la cursada en el estado actual.");
    }

    public void cargarNotaFinal() {
        System.out.println("no se puede cargar nota en el estado actual.");
    }


    public boolean cursadaTerminada (EstadoCursada estadoActual) {
        // metodo probado.
        return estadoActual.getClass().getName().contains("CursadaAprobada");
        /*
        EstadoCursada e = new CursadaAprobada();
        System.out.println(e.cursadaTerminada(e));
        true
        */
    }
}

