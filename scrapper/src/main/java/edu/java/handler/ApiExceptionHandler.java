package edu.java.handler;

import edu.java.entity.dto.ApiErrorResponse;
import edu.java.exception.LinkAlreadyTrackingException;
import edu.java.exception.LinkNotSupportedException;
import edu.java.exception.LinkNotTrackingException;
import edu.java.exception.TelegramChatAlreadyRegistered;
import edu.java.exception.TelegramChatNotExistsException;
import edu.java.util.ExceptionConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@RequiredArgsConstructor
public class ApiExceptionHandler {
    private final ExceptionConverter exceptionConverter;

    @ExceptionHandler(TelegramChatNotExistsException.class)
    public ResponseEntity<ApiErrorResponse> handleTelegramChatNotExistsException(
        TelegramChatNotExistsException exception
    ) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
            .body(exceptionConverter.fromResponseStatus(exception, "Telegram Chat Not Exists"));
    }

    @ExceptionHandler(TelegramChatAlreadyRegistered.class)
    public ResponseEntity<ApiErrorResponse> handleTelegramChatAlreadyRegistered(
        TelegramChatAlreadyRegistered exception
    ) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
            .body(exceptionConverter.fromResponseStatus(exception, "Telegram Chat Already Registered"));
    }

    @ExceptionHandler(LinkAlreadyTrackingException.class)
    public ResponseEntity<ApiErrorResponse> handleLinkAlreadyTrackingException(LinkAlreadyTrackingException exception) {
        return ResponseEntity.badRequest()
            .body(exceptionConverter.fromResponseStatus(exception, "Link Already Tracking"));
    }

    @ExceptionHandler(LinkNotTrackingException.class)
    public ResponseEntity<ApiErrorResponse> handleLinkNotTrackingException(LinkNotTrackingException exception) {
        return ResponseEntity.badRequest()
            .body(exceptionConverter.fromResponseStatus(exception, "Link Not Tracking"));
    }

    @ExceptionHandler(LinkNotSupportedException.class)
    public ResponseEntity<ApiErrorResponse> handleLinkNotSupportedException(LinkNotSupportedException exception) {
        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE)
            .body(exceptionConverter.fromResponseStatus(exception, "Link Not Supported"));
    }
}
