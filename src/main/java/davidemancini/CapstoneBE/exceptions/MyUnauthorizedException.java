package davidemancini.CapstoneBE.exceptions;

public class MyUnauthorizedException extends RuntimeException {
    public MyUnauthorizedException(String message) {
        super(message);
    }
}
