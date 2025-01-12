package com.sales.products.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {
    /*
     * Error System
     * */
    SYSTEM_ERROR("system-error", HttpStatus.BAD_REQUEST),
    ACCESS_DENIED("access-denied", HttpStatus.FORBIDDEN),
    UNAUTHORIZED("unauthorized", HttpStatus.UNAUTHORIZED),

    /*
     * Error User
     * */
    INVALID_USERNAME_OR_PASSWORD("invalid-username-or-password", HttpStatus.BAD_REQUEST),
    DURING_REGISTRATION_ERROR("during-registration-error", HttpStatus.BAD_REQUEST),
    EMAIL_EXIST("email-exist", HttpStatus.BAD_REQUEST),

    PRODUCT_NOT_FOUND("product-not-found", HttpStatus.NOT_FOUND),

    CATEGORY_NOT_FOUND("category-not-found", HttpStatus.NOT_FOUND)
    ;

    private final String message;
    private final HttpStatus status;

    ErrorCode(String message, HttpStatus status) {
        this.message = message;
        this.status = status;
    }
}
