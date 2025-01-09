package com.sales.generic.pojo;

import com.sales.generic.utils.CommonConstants;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ApiResult<T> {
    T results;
    String message;
    HttpStatus httpStatus;

    public ApiResult(T results, String message) {
        this.results = results;
        this.message = message;
    }

    public ApiResult(T results, String message, HttpStatus httpStatus) {
        this.results = results;
        this.message = message;
        this.httpStatus = httpStatus;
    }

    public ApiResult(T results) {
        this.results = results;
    }

    public static <T> ApiResult<T> success(T data) {
        return new ApiResult<>(data, CommonConstants.SUCCESS, HttpStatus.OK);
    }

    public static <T> ApiResult<T> success(T data, String message) {
        return new ApiResult<>(data, message, HttpStatus.OK);
    }

    public static <T> ApiResult<T> error(T data) {
        return new ApiResult<>(data);
    }
}
