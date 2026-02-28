package Exceptions;

/**
 * Excepciones relacionadas con validación de datos
 */
public class ValidacionException extends UniversidadException {
    
    public ValidacionException(String message) {
        super(message);
    }
    
    public ValidacionException(String message, Throwable cause) {
        super(message, cause);
    }
}
