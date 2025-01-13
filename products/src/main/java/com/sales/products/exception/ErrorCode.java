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

    PRODUCT_NOT_FOUND("product-not-found", HttpStatus.NOT_FOUND),

    CATEGORY_NOT_FOUND("category-not-found", HttpStatus.NOT_FOUND),

    REVIEW_NOT_FOUND("review-not-found", HttpStatus.NOT_FOUND),
    REVIEW_NOT_YOURS("review-not-yours", HttpStatus.BAD_REQUEST)
    ;

    private final String message;
    private final HttpStatus status;

    ErrorCode(String message, HttpStatus status) {
        this.message = message;
        this.status = status;
    }
}
