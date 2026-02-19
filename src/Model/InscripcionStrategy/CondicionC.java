package Model.InscripcionStrategy;

import Model.Cursada;
import Model.Estudiante;
import Model.Materia;
import Model.PlanDeEstudio;

import java.util.List;

public class CondicionC implements CondicionInscripcion{

    /*
    aprobo todas las cursadas de las correlativas y los finales
     de todas las materias de 5 cuatrimestres previos al que se quiere anotar.
     */

    public CondicionC() {
    }

    @Override
    public boolean revisarCondicion(PlanDeEstudio plan, Materia materia, Estudiante est) {
        //  correlativas de la materia
        List<Materia> correlativas = materia.getCorrelativas();

        // cursadas del estudiante
        List<Cursada> cursadas = est.getCursadasInscriptas();

        // verificar cursadas aprobadas de correlativas
        boolean correlativasOk = correlativas.stream()
                .allMatch(correlativa -> cursadas.stream()
                        .anyMatch(cur -> cur.getMateria().getId().equals(correlativa.getId())
                                && cur.isCursadaAprobada()));


        if (!correlativasOk) return false;

        // obtener cursadas de los últimos 5 cuatrimestres
        List<Cursada> previas = est.cursadasDeUltimosCuatrimestres(materia.getCuatrimestre(), 5);

        // verificar finales aprobados en esas cursadas
        boolean finalesPreviosOk = previas.stream()
                .allMatch(Cursada::isCursadaAprobadaTotal);

        return finalesPreviosOk;
    }

}

