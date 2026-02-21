import Model.*;

public class PrecargaMain {


    public static void main(String[] args) {

        //Instancia de Universidad
        Universidad uni = new Universidad();

        // Estudiantes:
        Estudiante est1 = new Estudiante("Sebastian","Martinez", 12345);
        Estudiante est2 = new Estudiante("Elias","Gatica", 654321);
        Estudiante est3 = new Estudiante("Victoria","Bartolelli", 321321);

        //Carreras:
        Carrera sistemas = new Carrera("Sistemas",11);
        Carrera  contador= new Carrera("Contador",22);
        Carrera turismo = new Carrera("Turismo",33);

        // Materias

        // Agregar estudiantes y carreras a la universidad

        // setear materias a un plan de estudios.

        //Agregar Materias obligatorias y optativas

        // crear y setear una estrategia de inscripcion

        //Buildear un nuevo plan de estudio

        // inscribir estudiantes a la carrera

        // cargar notas de estudiantes.

        // finalizar materias de estudiantes con final.

        // chequear si un estudiante termino la carrera.


    }
}
