package davidemancini.CapstoneBE.exceptions;

public class MyAlreadyExistingException extends RuntimeException {
    public MyAlreadyExistingException(String message) {
        super(message);
    }
}
