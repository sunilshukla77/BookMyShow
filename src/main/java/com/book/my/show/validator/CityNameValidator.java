package com.book.my.show.validator;

import com.book.my.show.exception.BadRequestException;
import com.book.my.show.exception.ErrorMapping;
import com.book.my.show.factory.ValidatorFactory;
import com.book.my.show.type.ValidationType;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class CityNameValidator implements Validator<String> {
    static {
        ValidatorFactory.VALIDATOR_TYPE_CLAS_MAP.put(ValidationType.CITYNAME, CityNameValidator.class);
    }

    @Override
    public void validate(String cityName) {
        if(!StringUtils.hasLength(cityName)) {
            throw new BadRequestException(HttpStatus.BAD_REQUEST, ErrorMapping.BMS011);
        }
    }
}
