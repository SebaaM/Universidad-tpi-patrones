package Model;

import java.util.ArrayList;

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

    @Override

    public String toString() {
        return nombre + " " + apellido;
    }
}
