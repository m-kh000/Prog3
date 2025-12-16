package exceptions;

public class InvalidDateFormatException extends Exception {
    public InvalidDateFormatException() {
        super("Date format must be: DD-MM-YYYY");
    }

    public InvalidDateFormatException(String message) {
        super(message);
    }
}
