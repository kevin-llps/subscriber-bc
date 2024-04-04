package fr.kevin.llps.subscriber.bc.api.rest.error;

import fr.kevin.llps.subscriber.bc.exception.SubscriberAlreadyExistsException;
import fr.kevin.llps.subscriber.bc.exception.SubscriberEntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.core.MethodParameter;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

class ErrorHandlerTest {

    private static final String ERROR_MESSAGE = "message";

    @Test
    void shouldHandleSubscriberEntityNotFoundException() {
        ErrorHandler errorHandler = new ErrorHandler();

        ErrorDto errorDto = errorHandler.handleSubscriberEntityNotFoundException(new SubscriberEntityNotFoundException(ERROR_MESSAGE));

        assertThat(errorDto.message()).isEqualTo(ERROR_MESSAGE);
    }

    @Test
    void shouldHandleSubscriberAlreadyExistsException() {
        ErrorHandler errorHandler = new ErrorHandler();

        ErrorDto errorDto = errorHandler.handleSubscriberAlreadyExistsException(new SubscriberAlreadyExistsException(ERROR_MESSAGE));

        assertThat(errorDto.message()).isEqualTo(ERROR_MESSAGE);
    }

    @Test
    void shouldHandleIllegalArgumentException() {
        ErrorHandler errorHandler = new ErrorHandler();

        ErrorDto errorDto = errorHandler.handleSubscriberAlreadyExistsException(new IllegalArgumentException(ERROR_MESSAGE));

        assertThat(errorDto.message()).isEqualTo(ERROR_MESSAGE);
    }

    @Test
    void givenNoFieldError_shouldHandleMethodArgumentNotValidException() {
        ErrorHandler errorHandler = new ErrorHandler();
        MethodArgumentNotValidException methodArgumentNotValidException = new MethodArgumentNotValidException(mock(MethodParameter.class), mock(BindingResult.class));

        ErrorDto errorDto = errorHandler.handleMethodArgumentNotValidException(methodArgumentNotValidException);

        assertThat(errorDto.message()).isEqualTo("At least one of the request field is not valid");
    }

}
