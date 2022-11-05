package com.book.my.show.exception;

import org.springframework.http.HttpStatus;

public class BadRequestException extends BaseException {
    private static final long serialVersionUID = 1167942090753233908L;

    public BadRequestException(HttpStatus httpStatus, ErrorMapping errorMapping) {
        super(httpStatus, errorMapping);
    }

    public BadRequestException(HttpStatus httpStatus, String code, String message, Throwable throwable) {
        super(httpStatus, code, message, throwable);
    }
}
