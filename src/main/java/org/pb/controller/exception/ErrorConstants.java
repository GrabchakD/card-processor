package org.pb.controller.exception;

public interface ErrorConstants {
    String TIMESTAMP_TEMPLATE = "[%s] %s";

    String DEFAULT_INTERNAL_ERROR_CODE = "500";
    String DEFAULT_INTERNAL_ERROR_MESSAGE = "Internal error.";

    String ENTITY_NOT_FOUND_CODE = "404";
    String ENTITY_NOT_FOUND_CAUSE = "Entity not found";
    String ENTITY_NOT_FOUND_MESSAGE = "Entity not found";


    String FAIL_LOGIN_CODE = "403";
    String FAIL_LOGIN_CAUSE = "Login failed";
    String FAIL_LOGIN_MESSAGE = "Login failed";
}
