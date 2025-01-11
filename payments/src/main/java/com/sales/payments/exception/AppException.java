package com.sales.payments.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class AppException extends RuntimeException {
    private ErrorCode errorCode;
}
