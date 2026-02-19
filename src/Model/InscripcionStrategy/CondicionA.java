package Model.InscripcionStrategy;

import Model.Cursada;
import Model.Estudiante;
import Model.Materia;
import Model.PlanDeEstudio;

import java.util.List;

public class CondicionA implements CondicionInscripcion {

    // Aprobo todas las cursadas correlativas
    public CondicionA(){

    }

    @Override
    public boolean revisarCondicion(PlanDeEstudio plan, Materia materia, Estudiante est) {
        //  obtener correlativas de la materia
        List<Materia> correlativas = materia.getCorrelativas();

        // obtener todas las cursadas del estudiante
        List<Cursada> cursadas = est.getCursadasInscriptas();

        //  verificar que cada correlativa tenga cursada aprobada
        boolean correlativasConCursadaAprobada = correlativas.stream()
                .allMatch(correlativa -> cursadas.stream()
                        .anyMatch(cur -> cur.getMateria().getId().equals(correlativa.getId())
                                && cur.isCursadaAprobada()));

        return correlativasConCursadaAprobada;
    }




}
