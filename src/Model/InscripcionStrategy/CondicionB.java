package Model.InscripcionStrategy;

import Model.Cursada;
import Model.Estudiante;
import Model.Materia;
import Model.PlanDeEstudio;

import java.util.List;

public class CondicionB implements CondicionInscripcion {

    /*
    aprobo todos los finales de las correlativas
     */

    public CondicionB() {
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
                                && cur.isCursadaAprobadaTotal()));

        return correlativasConCursadaAprobada;
    }
}
