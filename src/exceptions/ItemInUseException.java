package exceptions;

public class ItemInUseException extends Exception {
    public ItemInUseException() {
        super("The Item Is In Use!");
    }
}
