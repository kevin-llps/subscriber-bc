package fr.kevin.llps.subscriber.bc.api.rest.error;

import fr.kevin.llps.subscriber.bc.exception.SubscriberAlreadyExistsException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ErrorHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(SubscriberAlreadyExistsException.class)
    public ErrorDto handleSubscriberAlreadyExistsException(SubscriberAlreadyExistsException subscriberAlreadyExistsException) {
        return new ErrorDto(subscriberAlreadyExistsException.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ErrorDto handleMethodArgumentNotValidException(MethodArgumentNotValidException methodArgumentNotValidException) {
        FieldError fieldError = methodArgumentNotValidException.getFieldError();

        if (fieldError != null) {
            return new ErrorDto(fieldError.getDefaultMessage());
        }
        return new ErrorDto("At least one of the request field is not valid");
    }

}
