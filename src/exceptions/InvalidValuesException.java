package exceptions;

public class InvalidValuesException extends Exception {
    public InvalidValuesException() {}
    
    public InvalidValuesException(String message) {
        super(message);
    }
}
