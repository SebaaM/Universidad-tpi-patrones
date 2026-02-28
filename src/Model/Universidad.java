package Model;

import java.util.ArrayList;
import java.util.List;

public class Universidad {
    private List <Estudiante> estudiantes = new ArrayList();
    private List <Carrera> carreras = new ArrayList();
    private List<Materia> materias = new ArrayList<>();
    private static Universidad universidad = null;

    private Universidad() {

    }

    public static Universidad getInstance() {
        if (universidad == null) {
            universidad = new Universidad();
        }
        return universidad;
    }


    public void agregarMateria(Materia materia) {
        materias.add(materia);
    }

    public void quitarMateria(Materia materia) {
        materias.remove(materia);
    }

    public List<Materia> getMaterias() {
        return materias;
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
