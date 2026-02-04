package Model.BuilderPlan;

import Model.InscripcionStrategy.CondicionInscripcion;
import Model.Materia;
import Model.PlanDeEstudio;

import java.util.List;

public interface Builder {
    public Builder setMateriasObligatorias(List<Materia> materiasObligatorias);
    public Builder setMateriasOptativas(List<Materia> materiasOptativas);
    public Builder setOptativasMinimas(int cant);
    public Builder setEstrategiaInscripcion(CondicionInscripcion cond);
    public PlanDeEstudio devolverPlan();
}
