package src.application.controller.interceptor;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import src.domain.exceptions.InvalidUserDocumentException;
import src.domain.exceptions.UserNotFoundException;

import javax.servlet.http.HttpServletRequest;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
@RequiredArgsConstructor
public class ExceptionHandlerAdvice {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final MessageSource messageSource;

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity handleValidationError(
            MethodArgumentNotValidException exception,
            HttpServletRequest request
    ) {
        logger.warn("Exception Handler - Method Argument Not Valid");

        Map<String, String> errors = new HashMap<>();

        exception.getBindingResult()
                .getFieldErrors()
                .forEach(fieldError -> {
                    String message = messageSource.getMessage(fieldError, LocaleContextHolder.getLocale());

                    errors.put(fieldError.getField(), message);
                });

        var status = HttpStatus.BAD_REQUEST;

        var standardError = StandardError
                .builder()
                .timestamp(Instant.now())
                .status(status.value())
                .error("Validation Error")
                .path(request.getRequestURI())
                .errors(errors)
                .build();

        return ResponseEntity.status(status).body(standardError);
    }

    @ExceptionHandler(InvalidUserDocumentException.class)
    public ResponseEntity invalidUserDocumentExceptionHandler(
            InvalidUserDocumentException exception,
            HttpServletRequest request
    ) {
        logger.warn("Exception Handler - Invalid User Document");

        var errors = Map.of("document", exception.getMessage());

        var status = HttpStatus.BAD_REQUEST;

        var standardError = StandardError
                .builder()
                .timestamp(Instant.now())
                .status(status.value())
                .error("Invalid document")
                .message(exception.getMessage())
                .path(request.getRequestURI())
                .errors(errors)
                .build();

        return ResponseEntity.status(status).body(standardError);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<?> UserNotFoundExceptionHandler(
            UserNotFoundException exception,
            HttpServletRequest request
    ) {
        var errors = Map.of("UserId", exception.getMessage());

        var status = HttpStatus.NOT_FOUND;

        var standardError = StandardError
                .builder()
                .timestamp(Instant.now())
                .status(status.value())
                .error("Resource Not Found")
                .message(exception.getMessage())
                .path(request.getRequestURI())
                .errors(errors)
                .build();

        return ResponseEntity.status(status).body(standardError);
    }

}
