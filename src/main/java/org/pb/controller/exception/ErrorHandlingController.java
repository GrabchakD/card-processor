package org.pb.controller.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.annotation.PostConstruct;
import javax.persistence.EntityNotFoundException;
import java.util.HashMap;
import java.util.Map;

import static org.pb.controller.exception.ErrorConstants.ENTITY_NOT_FOUND_CODE;
import static org.pb.controller.exception.ErrorConstants.FAIL_LOGIN_CODE;
import static org.springframework.http.HttpStatus.CONFLICT;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

@ControllerAdvice
public class ErrorHandlingController {

    private final Map<Class<? extends Throwable>, ErrorDetails> errorDetailsFromErrorsMap = new HashMap<>();

    private final Map<String, ErrorDetails> errorDetailsFromMsgMap = new HashMap<>();

    @PostConstruct
    public void init() {
        this.errorDetailsFromErrorsMap.put(EntityNotFoundException.class, ErrorDetails.entityNotFound());

        this.errorDetailsFromMsgMap.put(ENTITY_NOT_FOUND_CODE, ErrorDetails.entityNotFound());
        this.errorDetailsFromMsgMap.put(FAIL_LOGIN_CODE, ErrorDetails.loginFailed());
    }

    @ExceptionHandler(value = Throwable.class)
    public ResponseEntity<ErrorDetails> internalServerError(Throwable throwable) {
        ErrorDetails details = errorDetailsFromErrorsMap.getOrDefault(throwable.getClass(),
                ErrorDetails.defaultFromMessage(throwable.getMessage()));
        return new ResponseEntity<>(details, CONFLICT);
    }

    @ExceptionHandler(value = {EntityNotFoundException.class})
    public ResponseEntity<ErrorDetails> entityNotFoundException(Throwable t) {
        ErrorDetails details = errorDetailsFromErrorsMap.getOrDefault(t.getClass(), ErrorDetails.defaultFromMessage(t.getMessage()));
        details.setMessage(t.getMessage());
        return new ResponseEntity<>(details, NOT_FOUND);
    }

    @ExceptionHandler(value = {LoginException.class})
    public ResponseEntity<ErrorDetails> loginException(Throwable t) {
        ErrorDetails details = errorDetailsFromErrorsMap.getOrDefault(t.getClass(), ErrorDetails.defaultFromMessage(t.getMessage()));
        details.setMessage(t.getMessage());
        return new ResponseEntity<>(details, UNAUTHORIZED);
    }
}
