package exceptions;

public class StorageInitializationException extends RuntimeException{
    public StorageInitializationException() {}

    public StorageInitializationException(String message) {
        super(message);
    }
}
