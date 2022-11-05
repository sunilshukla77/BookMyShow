package com.book.my.show.exception;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Setter
@Getter
@NoArgsConstructor
public class BaseException extends RuntimeException {

    private static final long serialVersionUID = -8426651156862539933L;

    private HttpStatus httpStatus;
    private String code;
    private String message;
    private Throwable throwable;

    public BaseException(HttpStatus httpStatus, ErrorMapping errorMapping) {
        super(errorMapping.getMessage());
        this.httpStatus = httpStatus;
        this.code = errorMapping.getCode();
        this.message = errorMapping.getMessage();
    }

    public BaseException(HttpStatus httpStatus, String code, String message, Throwable throwable) {
        super(message, throwable);
        this.httpStatus = httpStatus;
        this.code = code;
        this.message = message;
        this.throwable = throwable;
    }

}
