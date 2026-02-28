package Model;

import Model.BuilderPlan.Builder;
import Model.InscripcionStrategy.CondicionInscripcion;
import Model.InscripcionStrategy.DirectorStrategy;

import java.util.ArrayList;
import java.util.List;

public class PlanDeEstudio {

    private Integer cantOpcionales;
    private List<Materia> materiasOptativas = new ArrayList<>();
    private List<Materia> materiasObligatorias = new ArrayList<>();
    private DirectorStrategy directorStrategy;
    private DirectorBuilder directorBuilder;

    public PlanDeEstudio() {

        this.directorStrategy = new DirectorStrategy();
    }

    public Integer getCantOpcionales() {
        return cantOpcionales;
    }

    public void setCantOpcionales(Integer cantOpcionales) {
        this.cantOpcionales = cantOpcionales;
    }

    public List<Materia> getMateriasOptativas() {
        return materiasOptativas;
    }

    public void setMateriasOptativas(List<Materia> materiasOptativas) {
        this.materiasOptativas = materiasOptativas;
    }

    public List<Materia> getMateriasObligatorias() {
        return materiasObligatorias;
    }

    public void setMateriasObligatorias(List<Materia> materiasObligatorias) {
        this.materiasObligatorias = materiasObligatorias;
    }

    public CondicionInscripcion getCondicionInscripcion() {
        return this.directorStrategy.getStrategy();
    }

    public void setCondicionInscripcion(CondicionInscripcion cond) {
        directorStrategy.setStrategy(cond);
    }


    // INNER CLASS CONTEXT DEL BUILDER.
    // static para que pueda ser accedido desde fuera de la clase.
    public static class DirectorBuilder{
        // Al escalar la aplicacion acepta distintos builder

        public Builder builder;

        public DirectorBuilder(Builder b) {
            this.builder = b;
        }

        public void cambiarBuilder(Builder b){
            this.builder = b;
        }

        public void devolverPlan(){
            this.builder.devolverPlan();
        }

    }

}
