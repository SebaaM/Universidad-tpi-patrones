package Exceptions;

/**
 * Excepciones relacionadas con operaciones de materias
 */
public class MateriaException extends UniversidadException {
    
    public MateriaException(String message) {
        super(message);
    }
    
    public MateriaException(String message, Throwable cause) {
        super(message, cause);
    }
}
