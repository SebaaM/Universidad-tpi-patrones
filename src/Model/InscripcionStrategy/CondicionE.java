package Model.InscripcionStrategy;

import Model.Cursada;
import Model.Estudiante;
import Model.Materia;
import Model.PlanDeEstudio;

import java.util.List;

public class CondicionE implements CondicionInscripcion  {
    /*
    aprobo los finales de las correlativas y los finales de todas
     las materias de 3 cuatrimestres previos.
     */

    public CondicionE() {
    }

    @Override
    public boolean revisarCondicion(PlanDeEstudio plan, Materia materia, Estudiante est) {
        // correlativas de la materia
        List<Materia> correlativas = materia.getCorrelativas();

        // cursadas del estudiante
        List<Cursada> cursadas = est.getCursadasInscriptas();

        // verificar finales aprobados de correlativas
        boolean correlativasOk = correlativas.stream()
                .allMatch(correlativa -> cursadas.stream()
                        .anyMatch(cur -> cur.getMateria().getId().equals(correlativa.getId())
                                && cur.isCursadaAprobadaTotal()));

        if (!correlativasOk) return false;

        // cursadas de los últimos 3 cuatrimestres
        List<Cursada> previas = est.cursadasDeUltimosCuatrimestres(materia.getCuatrimestre(), 3);

        // verificar finales aprobados en esas cursadas
        boolean finalesPreviosOk = previas.stream()
                .allMatch(Cursada::isCursadaAprobadaTotal);

        return finalesPreviosOk;
    }


}


