package org.pb.controller.exception;

import lombok.Data;

import java.time.LocalDateTime;

import static org.pb.controller.exception.ErrorConstants.DEFAULT_INTERNAL_ERROR_CODE;
import static org.pb.controller.exception.ErrorConstants.DEFAULT_INTERNAL_ERROR_MESSAGE;
import static org.pb.controller.exception.ErrorConstants.ENTITY_NOT_FOUND_CAUSE;
import static org.pb.controller.exception.ErrorConstants.ENTITY_NOT_FOUND_CODE;
import static org.pb.controller.exception.ErrorConstants.ENTITY_NOT_FOUND_MESSAGE;
import static org.pb.controller.exception.ErrorConstants.FAIL_LOGIN_CAUSE;
import static org.pb.controller.exception.ErrorConstants.FAIL_LOGIN_CODE;
import static org.pb.controller.exception.ErrorConstants.FAIL_LOGIN_MESSAGE;

@Data
public class ErrorDetails {
    private String errorCode;
    private String cause;
    private String message;

    public ErrorDetails(String errorCode, String cause, String message) {
        this.errorCode = errorCode;
        this.cause = String.format(ErrorConstants.TIMESTAMP_TEMPLATE, LocalDateTime.now(), cause);
        this.message = message;
    }


    public static ErrorDetails defaultFromMessage(String msg) {
        return new ErrorDetails(DEFAULT_INTERNAL_ERROR_CODE, msg, DEFAULT_INTERNAL_ERROR_MESSAGE);
    }

    public static ErrorDetails entityNotFound() {
        return new ErrorDetails(ENTITY_NOT_FOUND_CODE, ENTITY_NOT_FOUND_CAUSE, ENTITY_NOT_FOUND_MESSAGE);
    }

    public static ErrorDetails loginFailed() {
        return new ErrorDetails(FAIL_LOGIN_CODE, FAIL_LOGIN_CAUSE, FAIL_LOGIN_MESSAGE);
    }
}
