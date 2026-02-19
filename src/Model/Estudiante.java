package Model;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Estudiante {
    private String nombre;
    private String apellido;
    private long dni;
    private ArrayList<Cursada> cursadasInscriptas;

    public Estudiante(String nombre, String apellido, long dni) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.dni = dni;
        this.cursadasInscriptas = new ArrayList<Cursada>();
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public long getDni() {
        return dni;
    }

    public void setDni(long dni) {
        this.dni = dni;
    }

    public ArrayList<Cursada> getCursadasInscriptas() {
        return cursadasInscriptas;
    }

    public void setCursadasInscriptas(ArrayList<Cursada> cursadasInscriptas) {
        this.cursadasInscriptas = cursadasInscriptas;
    }

    // retorna las cursadas de los ultimos cuatrimestres
    // cuatrimestreActual es el cuatrimestre de la materia que se quiere inscribir
    // cantidad es la cantidad de cuatrimestres previos a la materia que se quiere inscribir
    public List<Cursada> cursadasDeUltimosCuatrimestres(int cuatrimestreActual, int cantidad) {
        int cuatrimestreMinino = cuatrimestreActual - cantidad;
        return cursadasInscriptas.stream()
                .filter(c -> c.getMateria().getCuatrimestre() >= cuatrimestreMinino
                        && c.getMateria().getCuatrimestre() < cuatrimestreActual)
                .collect(Collectors.toList());
    }



    @Override
    public String toString() {
        return nombre + " " + apellido;
    }
}
