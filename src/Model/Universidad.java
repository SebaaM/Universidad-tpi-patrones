package Model;

import java.util.ArrayList;
import java.util.List;

public class Universidad {
    private List <Estudiante> estudiantes = new ArrayList();
    private List <Carrera> carreras = new ArrayList();

    public Universidad() {

    }

    public void agregarEstudiante(Estudiante estudiante) {
        estudiantes.add(estudiante);
    }

    public void agregarCarrera(Carrera carrera) {
        carreras.add(carrera);
    }

    public void quitarEstudiante (Estudiante estudiante) {

        estudiantes.remove(estudiante);
    }

    public void quitarCarrera (Carrera carrera) {
        carreras.remove(carrera);
    }

    public void listarEstudiantes () {
        for (Estudiante estudiante : estudiantes) {
            System.out.println(estudiante);
        }
    }

    public void listarCarreras () {
        for (Carrera carrera : carreras) {
            System.out.println(carrera);
        }
    }


    public Estudiante buscarEstudiante (Estudiante estudiante) {
        if (estudiantes.contains(estudiante))
            return estudiantes.get(estudiantes.indexOf(estudiante));
        return null;
    }

    public Carrera buscarCarrera (Carrera carrera) {
        if (carreras.contains(carrera))
            return carreras.get(carreras.indexOf(carrera));
        return null;
    }

    public List<Estudiante> getEstudiantes() {
        return estudiantes;
    }

    public void setEstudiantes(List<Estudiante> estudiantes) {
        this.estudiantes = estudiantes;
    }

    public List<Carrera> getCarreras() {
        return carreras;
    }

    public void setCarreras(List<Carrera> carreras) {
        this.carreras = carreras;
    }

    
}
