package jsonParser.exceptions;

public class CircularReferenceException extends RuntimeException {
    public CircularReferenceException(String message) {
        super(message);
    }
    public CircularReferenceException(String message, Throwable cause) {
        super(message, cause);
    }
}
