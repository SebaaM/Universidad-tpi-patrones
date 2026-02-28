package Exceptions;

/**
 * Excepciones relacionadas con operaciones de carreras
 */
public class CarreraException extends UniversidadException {
    
    public CarreraException(String message) {
        super(message);
    }
    
    public CarreraException(String message, Throwable cause) {
        super(message, cause);
    }
}
