package fr.kevin.llps.subscriber.bc.api.rest.error;

import fr.kevin.llps.subscriber.bc.exception.SubscriberAlreadyExistsException;
import fr.kevin.llps.subscriber.bc.exception.SubscriberEntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.core.MethodParameter;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.stream.Stream;

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

    @ParameterizedTest
    @MethodSource("provideRuntimeExceptions")
    void shouldHandleCustomRuntimeException(RuntimeException runtimeException) {
        ErrorHandler errorHandler = new ErrorHandler();

        ErrorDto errorDto = errorHandler.handleCustomRuntimeException(runtimeException);

        assertThat(errorDto.message()).isEqualTo(ERROR_MESSAGE);
    }

    static Stream<Arguments> provideRuntimeExceptions() {
        return Stream.of(
                Arguments.of(new SubscriberAlreadyExistsException(ERROR_MESSAGE)),
                Arguments.of(new IllegalArgumentException(ERROR_MESSAGE))
        );
    }

    @Test
    void givenNoFieldError_shouldHandleMethodArgumentNotValidException() {
        ErrorHandler errorHandler = new ErrorHandler();
        MethodArgumentNotValidException methodArgumentNotValidException = new MethodArgumentNotValidException(mock(MethodParameter.class), mock(BindingResult.class));

        ErrorDto errorDto = errorHandler.handleMethodArgumentNotValidException(methodArgumentNotValidException);

        assertThat(errorDto.message()).isEqualTo("At least one of the request field is not valid");
    }

}
