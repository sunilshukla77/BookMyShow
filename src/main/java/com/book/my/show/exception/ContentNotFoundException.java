package com.book.my.show.exception;

import org.springframework.http.HttpStatus;

public class ContentNotFoundException extends BaseException {
    private static final long serialVersionUID = 6058864515561277206L;

    public ContentNotFoundException(HttpStatus httpStatus, ErrorMapping errorMapping) {
        super(httpStatus, errorMapping);
    }

    public ContentNotFoundException(HttpStatus httpStatus, String code, String message, Throwable throwable) {
        super(httpStatus, code, message, throwable);
    }
}
