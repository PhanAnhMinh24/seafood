package com.sales.exception.exception;

import org.springframework.http.HttpStatus;

public interface IErrorCode {
    String getMessage();
    HttpStatus getStatus();
}
