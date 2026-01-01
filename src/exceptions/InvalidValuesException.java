package exceptions;

public class InvalidValuesException extends Exception {
    public InvalidValuesException() {
        super("Invalid values!");
    }
    
    public InvalidValuesException(String message) {
        super(message);
    }
}
