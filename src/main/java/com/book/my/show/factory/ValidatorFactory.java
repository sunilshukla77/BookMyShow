package com.book.my.show.factory;

import com.book.my.show.type.ValidationType;
import com.book.my.show.validator.Validator;

import java.util.HashMap;
import java.util.Map;

public class ValidatorFactory {
    public static Map<ValidationType, Class<? extends Validator>> VALIDATOR_TYPE_CLAS_MAP = new HashMap<>();
}
