package org.openchat.configure;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    private static final String API_NOT_IMPLEMENTED = "API not implemented.";
    private static final String INTERNAL_SERVER_ERROR = "Internal server error.";

    @ExceptionHandler({Exception.class})
    public ResponseEntity<String> handleDefaultException(Exception ex, WebRequest request) {
        log.error(INTERNAL_SERVER_ERROR + ": " + request.getContextPath());
        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(INTERNAL_SERVER_ERROR);
    }
}
