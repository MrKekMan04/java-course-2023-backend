package edu.java.bot.handler;

import edu.java.bot.entity.dto.ApiErrorResponse;
import java.util.Arrays;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ApiExceptionHandler {
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiErrorResponse> httpMessageNotReadableException(HttpMessageNotReadableException exception) {
        return ResponseEntity.badRequest()
            .body(new ApiErrorResponse("Incorrect body was received",
                "400",
                exception.getClass().getSimpleName(),
                exception.getMessage(),
                Arrays.stream(exception.getStackTrace()).map(StackTraceElement::toString).toList()
            ));
    }
}
