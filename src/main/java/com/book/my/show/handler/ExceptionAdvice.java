package com.book.my.show.handler;

import com.book.my.show.exception.BadRequestException;
import com.book.my.show.exception.BookingNotPossibleException;
import com.book.my.show.exception.ContentNotFoundException;
import com.book.my.show.response.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@Slf4j
public class ExceptionAdvice extends ResponseEntityExceptionHandler {
    @ExceptionHandler(Exception.class)
    protected ResponseEntity<Object> handleAllExceptions(Exception ex, WebRequest request) {
        log.error("Generic exception found with message : {}", ex.getMessage());
        ErrorResponse error = new ErrorResponse("Generic Exception", ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        return handleExceptionInternal(ex, error, new HttpHeaders(),HttpStatus.INTERNAL_SERVER_ERROR, request);
    }

    @ExceptionHandler(BadRequestException.class)
    protected ResponseEntity<Object> handleBadRequestException(BadRequestException ex, WebRequest request) {
        log.error("Bad request with message : {} and code : {}", ex.getMessage(), ex.getCode());
        ErrorResponse error = new ErrorResponse(ex.getCode(), ex.getMessage(), ex.getHttpStatus());
        return handleExceptionInternal(ex, error, new HttpHeaders(), ex.getHttpStatus(), request);
    }

    @ExceptionHandler(ContentNotFoundException.class)
    protected ResponseEntity<Object> handleContentNotFoundException(ContentNotFoundException ex, WebRequest request) {
        log.error("Content not found with message : {} and code : {}", ex.getMessage(), ex.getCode());
        ErrorResponse error = new ErrorResponse(ex.getCode(), ex.getMessage(), ex.getHttpStatus());
        return handleExceptionInternal(ex, error, new HttpHeaders(), ex.getHttpStatus(), request);
    }

    @ExceptionHandler(BookingNotPossibleException.class)
    protected ResponseEntity<Object> handleBookingNotPossibleException(BookingNotPossibleException ex, WebRequest request) {
        log.error("Booking not possible with message : {} and code : {}", ex.getMessage(), ex.getCode());
        ErrorResponse error = new ErrorResponse(ex.getCode(), ex.getMessage(), ex.getHttpStatus());
        return handleExceptionInternal(ex, error, new HttpHeaders(), ex.getHttpStatus(), request);
    }

    @ExceptionHandler(value = ConstraintViolationException.class)
    protected ResponseEntity<Object> handleConstraintViolationException(ConstraintViolationException ex, WebRequest request) {
        log.error("Constraint violation exception occurred while processing the request");
        return handleExceptionInternal(ex, ex.getMessage(),
                new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler(value = DataIntegrityViolationException.class)
    protected ResponseEntity<Object> handleDataIntegrityViolationException(DataIntegrityViolationException ex, WebRequest request) {
        log.error("Data integrity violation exception occurred while processing the request");
        return handleExceptionInternal(ex, ex.getMessage(),
                new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }
}
