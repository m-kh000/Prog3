package exceptions;

public class EmptyFieldException extends Exception{

    public EmptyFieldException() {
        super("please fill out all fields");
    }
    
}
