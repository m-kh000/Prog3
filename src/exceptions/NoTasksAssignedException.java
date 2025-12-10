package exceptions;

public class NoTasksAssignedException extends Exception {
    public NoTasksAssignedException() {}

    public NoTasksAssignedException(String message) {
        super(message);
    }
}
