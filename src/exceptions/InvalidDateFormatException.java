package exceptions;

public class InvalidDateFormatException extends Exception {
    public InvalidDateFormatException() {
        super("Date format must be: YYYY-MM-DD");
    }

    public InvalidDateFormatException(String message) {
        super(message);
    }
}
