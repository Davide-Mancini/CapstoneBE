package davidemancini.CapstoneBE.exceptions;

import davidemancini.CapstoneBE.payloads.errors.ErrorsDTO;
import davidemancini.CapstoneBE.payloads.errors.ErrorsListDTO;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class ExceptionsHandler extends RuntimeException {

    @ExceptionHandler(MyNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorsDTO notFoundHandler(MyNotFoundException ex){
        return new ErrorsDTO("Elemento non trovato", LocalDateTime.now());
    }

    @ExceptionHandler(MyValidationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorsListDTO validationHandler(MyValidationException ex){
        return new ErrorsListDTO(ex.getMessage(),LocalDateTime.now(),ex.getErrors());
    }

    @ExceptionHandler(MyAlreadyExistingException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorsDTO alreadyExistHandler(MyAlreadyExistingException ex){
        return new ErrorsDTO(ex.getMessage(),LocalDateTime.now());
    }

    @ExceptionHandler(MyUnauthorizedException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ErrorsDTO unauthorizedHandler(MyUnauthorizedException ex){
        return new ErrorsDTO(ex.getMessage(),LocalDateTime.now());
    }

    @ExceptionHandler(MyBadRequest.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorsDTO badRequestHandler(MyBadRequest ex){
        return new ErrorsDTO(ex.getMessage(),LocalDateTime.now());
    }
}
