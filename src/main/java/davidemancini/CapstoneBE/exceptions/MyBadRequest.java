package davidemancini.CapstoneBE.exceptions;

public class MyBadRequest extends RuntimeException {
    public MyBadRequest(String message) {
        super(message);
    }
}
