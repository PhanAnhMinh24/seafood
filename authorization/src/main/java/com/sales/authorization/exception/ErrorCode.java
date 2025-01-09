package com.sales.authorization.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {
    /*
     * Error System
     * */
    SYSTEM_ERROR("system-error", HttpStatus.BAD_REQUEST),
    UNAUTHORIZED("unauthorized", HttpStatus.UNAUTHORIZED),

    /*
     * Error User
     * */
    INVALID_USERNAME_OR_PASSWORD("invalid-username-or-password", HttpStatus.BAD_REQUEST),
    DURING_REGISTRATION_ERROR("during-registration-error", HttpStatus.BAD_REQUEST),
    EMAIL_EXIST("email-exist", HttpStatus.BAD_REQUEST),
    ;

    private final String message;
    private final HttpStatus status;

    ErrorCode(String message, HttpStatus status) {
        this.message = message;
        this.status = status;
    }
}
