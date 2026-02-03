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

    public void buscarEstudiante (Estudiante estudiante) {
        estudiantes.contains(estudiante);
    }

    public void buscarCarrera (Carrera carrera) {
        carreras.contains(carrera);
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
