package davidemancini.CapstoneBE.exceptions;

import lombok.Getter;

import java.util.List;

@Getter
public class MyValidationException extends RuntimeException {
    private List<String> errors;
    public MyValidationException(List<String> errors) {
        super("Trovati i seguenti errori "+ errors);
        this.errors = errors;
    }
}
