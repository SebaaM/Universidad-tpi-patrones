package Model;

import Model.BuilderPlan.Builder;

public class Carrera {
    public PlanDeEstudio planEstudio = new PlanDeEstudio();
    private String nombre;
    private Integer id;

    public Carrera(String nombre, Integer id) {
        this.nombre = nombre;
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public PlanDeEstudio getPlanEstudio() {
        return planEstudio;
    }

    public void setPlanEstudio(PlanDeEstudio planEstudio) {
        this.planEstudio = planEstudio;
    }

    @Override
    public String toString() {
        return "Carrera{" +
                "nombre='" + nombre + '\'' +
                ", id=" + id +
                '}';
    }

    public class DirectorBuilder implements Builder {
        


    }

}


