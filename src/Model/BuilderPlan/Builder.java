package Model.BuilderPlan;

import Model.InscripcionStrategy.CondicionInscripcion;
import Model.Materia;
import Model.PlanDeEstudio;

public interface Builder {
    public Builder agregarMateriaObligatoria(Materia materiasObligatorias);
    public Builder agregarMateriaOpcional(Materia materiasOptativas);
    public Builder setOptativasMinimas(int cant);
    public Builder setEstrategiaInscripcion(CondicionInscripcion cond);
    public PlanDeEstudio devolverPlan();

}
