package Model.BuilderPlan;

import Model.InscripcionStrategy.CondicionInscripcion;
import Model.Materia;
import Model.PlanDeEstudio;

import java.util.List;

public class PlanBuildA implements Builder {

    private PlanDeEstudio plan;

    @Override
    public Builder setMateriasObligatorias(List <Materia> materiasObligatoria) {
        this.plan.setMateriasObligatorias(materiasObligatoria);
        return this;
    }

    @Override
    public Builder setMateriasOptativas(List<Materia> materiasOpt) {
        this.plan.setMateriasOptativas(materiasOpt);
        return this;
    }

    @Override
    public Builder setOptativasMinimas(int i) {
        this.plan.setCantOpcionales(i);
        return this;
    }

    @Override
    public Builder setEstrategiaInscripcion(CondicionInscripcion cond) {
        this.plan.setCondicionInscripcion(cond);
        return this;
    }

    public void reset(){
        this.plan = new PlanDeEstudio();
    }


    @Override
    public PlanDeEstudio devolverPlan(){
        PlanDeEstudio planCreado = this.plan;
        this.reset();
        return planCreado;
    }
}
