package Exceptions;

/**
 * Excepciones relacionadas con operaciones de estudiantes
 */
public class EstudianteException extends UniversidadException {
    
    public EstudianteException(String message) {
        super(message);
    }
    
    public EstudianteException(String message, Throwable cause) {
        super(message, cause);
    }
}
