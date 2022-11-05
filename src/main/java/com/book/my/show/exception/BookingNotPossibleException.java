package com.book.my.show.exception;

import org.springframework.http.HttpStatus;

public class BookingNotPossibleException extends BaseException{
    private static final long serialVersionUID = 1337605777138647185L;

    public BookingNotPossibleException(HttpStatus httpStatus, ErrorMapping errorMapping) {
        super(httpStatus, errorMapping);
    }

    public BookingNotPossibleException(HttpStatus httpStatus, String code, String message, Throwable throwable) {
        super(httpStatus, code, message, throwable);
    }
}
