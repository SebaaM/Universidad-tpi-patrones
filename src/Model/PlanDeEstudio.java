package Model;

import Model.InscripcionStrategy.DirectorStrategy;

import java.util.ArrayList;
import java.util.List;

public class PlanDeEstudio {

    private Integer cantObligatorias;
    private Integer cantOpcionales;
    private List<Materia> materiasOptativas = new ArrayList<>();
    private List<Materia> materiasObligatorias = new ArrayList<>();
    private DirectorStrategy directorStrategy;

    public PlanDeEstudio() {
    }

    public Integer getCantObligatorias() {
        return cantObligatorias;
    }

    public void setCantObligatorias(Integer cantObligatorias) {
        this.cantObligatorias = cantObligatorias;
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

    public DirectorStrategy getDirectorStrategy() {
        return directorStrategy;
    }

    public void setDirectorStrategy(DirectorStrategy directorStrategy) {
        this.directorStrategy = directorStrategy;
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




}
