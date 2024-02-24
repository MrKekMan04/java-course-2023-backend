package edu.java.handler;

import edu.java.entity.dto.ApiErrorResponse;
import edu.java.exception.LinkAlreadyTrackingException;
import edu.java.exception.LinkNotTrackingException;
import edu.java.exception.TelegramChatAlreadyRegistered;
import edu.java.exception.TelegramChatNotExistsException;
import edu.java.util.ExceptionConverter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ApiExceptionHandler {
    @ExceptionHandler(TelegramChatNotExistsException.class)
    public ResponseEntity<ApiErrorResponse> telegramChatNotExistsException(TelegramChatNotExistsException exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
            .body(ExceptionConverter.fromResponseStatus(exception, "Telegram Chat Not Exists"));
    }

    @ExceptionHandler(TelegramChatAlreadyRegistered.class)
    public ResponseEntity<ApiErrorResponse> telegramChatAlreadyRegistered(TelegramChatAlreadyRegistered exception) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
            .body(ExceptionConverter.fromResponseStatus(exception, "Telegram Chat Already Registered"));
    }

    @ExceptionHandler(LinkAlreadyTrackingException.class)
    public ResponseEntity<ApiErrorResponse> linkAlreadyTrackingException(LinkAlreadyTrackingException exception) {
        return ResponseEntity.badRequest()
            .body(ExceptionConverter.fromResponseStatus(exception, "Link Already Tracking"));
    }

    @ExceptionHandler(LinkNotTrackingException.class)
    public ResponseEntity<ApiErrorResponse> linkNotTrackingException(LinkNotTrackingException exception) {
        return ResponseEntity.badRequest()
            .body(ExceptionConverter.fromResponseStatus(exception, "Link Not Tracking"));
    }
}
