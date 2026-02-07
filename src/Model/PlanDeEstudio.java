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
    private CondicionInscripcion condicionInscripcion ;
    //private DirectorStrategy directorStrategy;
    private DirectorBuilder directorBuilder;

    public PlanDeEstudio() {
        // setear estrategia por defecto
        // setear builder por defecto ??
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
        return this.condicionInscripcion;
    }

    public void setCondicionInscripcion(CondicionInscripcion cond) {
        this.condicionInscripcion = cond;

    }

    public void agregarMateriaOptativa(Materia materia) {
        materiasOptativas.add(materia);
    }

    public void agregarMateriaObligatoria(Materia materia) {
        materiasObligatorias.add(materia);
    }

    public void sacarMateriaObligatoria(Materia materia) {
        materiasObligatorias.remove(materia);
    }

    public void sacarMateriaOptativa(Materia materia) {
        materiasOptativas.remove(materia);
    }

    public class DirectorBuilder{
        Builder builder;

        public DirectorBuilder(Builder b) {
            this.builder = b;
        }

        public void cambiarBuilder(Builder b){
            this.builder = b;
        }

        public PlanDeEstudio crearPlan(){

//            builder.setMateriasObligatorias();
//            builder.setEstrategiaInscripcion();
//            builder.setOptativasMinimas();
//            builder.setMateriasOptativas();

            return this.builder.devolverPlan();

        }



    }


}
