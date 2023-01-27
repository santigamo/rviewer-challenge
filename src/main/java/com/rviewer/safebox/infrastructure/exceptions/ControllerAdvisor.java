package com.rviewer.safebox.infrastructure.exceptions;

import com.rviewer.safebox.domain.exceptions.AuthorizationException;
import com.rviewer.safebox.domain.exceptions.InvalidSafeboxNameException;
import com.rviewer.safebox.domain.exceptions.InvalidSafeboxPasswordException;
import com.rviewer.safebox.domain.exceptions.SafeboxAlreadyExistsException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

@ControllerAdvice
public class ControllerAdvisor extends ResponseEntityExceptionHandler {

    private static final String MALFORMED_DATA = "Malformed expected data";
    @ExceptionHandler(InvalidSafeboxNameException.class)
    public ResponseEntity<Object> handleInvalidSafeboxNameException(InvalidSafeboxNameException ex, WebRequest request) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("message", MALFORMED_DATA);
        body.put("description", ex.getMessage());
        return new ResponseEntity<>(body, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler(InvalidSafeboxPasswordException.class)
    public ResponseEntity<Object> handleInvalidSafeboxPasswordException(InvalidSafeboxPasswordException ex, WebRequest request) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("message", MALFORMED_DATA);
        body.put("description", ex.getMessage());
        return new ResponseEntity<>(body, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler(SafeboxAlreadyExistsException.class)
    public ResponseEntity<Object> handleSafeboxAlreadyExistsException(SafeboxAlreadyExistsException ex, WebRequest request) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("message", ex.getMessage());
        return new ResponseEntity<>(body, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(AuthorizationException.class)
    public ResponseEntity<Object> handleAuthorizationException(AuthorizationException ex, WebRequest request) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("message", "Specified Basic Auth does not match");
        return new ResponseEntity<>(body, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleAll(Exception ex, WebRequest request) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("message", "Unexpected API error");
        return new ResponseEntity<>(body, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
