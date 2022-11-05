package com.book.my.show.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.http.HttpStatus;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Accessors(chain = true)
@Setter
@Getter
@AllArgsConstructor
public class ErrorResponse {
    private String code;
    private String message;
    private HttpStatus httpStatus;
}