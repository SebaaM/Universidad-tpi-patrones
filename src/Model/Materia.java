package Model;

import java.util.ArrayList;
import java.util.List;

public class Materia {
    private List <Materia> correlativas;
    private String nombre;
    private Integer id;

    public Materia(String nombre, Integer id) {
        this.nombre = nombre;
        this.id = id;
        this.correlativas = new ArrayList<Materia>();
    }

    public List<Materia> getCorrelativas() {
        return correlativas;
    }

    public void setCorrelativas(List<Materia> correlativas) {
        this.correlativas = correlativas;
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

    public String verCorrelativas(){
        String res= "Correlativas: ";
        for (Materia m : this.correlativas) {
            res = res +"- "+ m.toString();
        }
        return res;
    }

    @Override
    public String toString() {
        //final String s = String.format("Materia{id=%d, nombre='%s', correlativas=[%s]}", id, nombre, correlativas.stream().map(Materia::getNombre).reduce((a, b) -> a + ", " + b).orElse(""));

        return String.format("Materia{id=%d, nombre='%s'}", id, nombre);
    }
}
