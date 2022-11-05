package com.book.my.show.validator;

import com.book.my.show.exception.BadRequestException;
import com.book.my.show.exception.ErrorMapping;
import com.book.my.show.factory.ValidatorFactory;
import com.book.my.show.type.ValidationType;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
public class OffsetValidator implements Validator<String> {
    static {
        ValidatorFactory.VALIDATOR_TYPE_CLAS_MAP.put(ValidationType.OFFSET, OffsetValidator.class);
    }

    @Override
    public void validate(String offset) {
        if(Integer.parseInt(offset) < 0) {
            throw new BadRequestException(HttpStatus.BAD_REQUEST, ErrorMapping.BMS010);
        }
    }
}
