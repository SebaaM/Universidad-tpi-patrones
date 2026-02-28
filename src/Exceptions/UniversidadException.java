package Exceptions;

/**
 * Excepción base para todas las excepciones del sistema universitario
 */
public class UniversidadException extends Exception {
    
    public UniversidadException(String message) {
        super(message);
    }
    
    public UniversidadException(String message, Throwable cause) {
        super(message, cause);
    }
}
