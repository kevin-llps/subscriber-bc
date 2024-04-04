package fr.kevin.llps.subscriber.bc.api.rest.error;

import fr.kevin.llps.subscriber.bc.exception.SubscriberAlreadyExistsException;
import fr.kevin.llps.subscriber.bc.exception.SubscriberEntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ErrorHandler {

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(SubscriberEntityNotFoundException.class)
    public ErrorDto handleSubscriberEntityNotFoundException(SubscriberEntityNotFoundException subscriberEntityNotFoundException) {
        return new ErrorDto(subscriberEntityNotFoundException.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IllegalArgumentException.class)
    public ErrorDto handleIllegalArgumentException(RuntimeException runtimeException) {
        return new ErrorDto(runtimeException.getMessage());
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler({SubscriberAlreadyExistsException.class})
    public ErrorDto handleSubscriberAlreadyExistsException(RuntimeException runtimeException) {
        return new ErrorDto(runtimeException.getMessage());
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
