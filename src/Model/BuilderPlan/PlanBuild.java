package Model.BuilderPlan;

import Model.InscripcionStrategy.CondicionInscripcion;
import Model.Materia;
import Model.PlanDeEstudio;

public class PlanBuild implements Builder {

    private PlanDeEstudio plan;

    public PlanBuild() {
        this.plan = new PlanDeEstudio();
    }


    /**
     * @param materiaObligatoria
     * @return
     */
    @Override
    public Builder agregarMateriaObligatoria(Materia materiaObligatoria) {
        plan.getMateriasObligatorias().add(materiaObligatoria

);
        return this;
    }

    /**
     * @param materiaOptativa
     * @return
     */
    @Override
    public Builder agregarMateriaOpcional(Materia materiaOptativa) {
        plan.getMateriasOptativas().add(materiaOptativa);
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
