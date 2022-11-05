package com.book.my.show.validator;

import com.book.my.show.factory.ValidatorFactory;
import com.book.my.show.type.ValidationType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

@Slf4j
public class ValidationUtility {
    public static void validate(HttpServletRequest servletRequest) throws IllegalAccessException, InstantiationException {
        validatePathVariables(servletRequest);
        validateRequestParameters(servletRequest);
    }

    private static void validateRequestParameters(HttpServletRequest servletRequest) throws InstantiationException, IllegalAccessException {
        for(Map.Entry<String, String[]> entry : servletRequest.getParameterMap().entrySet()) {
            ValidationType key = null;
            try {
                key = ValidationType.valueOf(entry.getKey().toUpperCase());
            } catch(Exception e) {
                log.info("Validation enum {} does not exist and error : {}", entry.getKey().toUpperCase(), e.getMessage());
            }
            if(Objects.nonNull(key) && ValidatorFactory.VALIDATOR_TYPE_CLAS_MAP.containsKey(key)) {
                ValidatorFactory.VALIDATOR_TYPE_CLAS_MAP.get(key).newInstance().validate(entry.getValue()[0]);
            }
        }
    }

    private static void validatePathVariables(HttpServletRequest servletRequest) throws InstantiationException, IllegalAccessException {
        Map<String, String> pathVariableMap = (LinkedHashMap<String, String>) servletRequest.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
        for(Map.Entry<String, String> entry : pathVariableMap.entrySet()) {
            ValidationType key = null;
            try {
                key = ValidationType.valueOf(entry.getKey().toUpperCase());
            } catch(Exception e) {
                log.info("Validation enum {} does not exist and error : {}", entry.getKey().toUpperCase(), e.getMessage());
            }
            if(Objects.nonNull(key) && ValidatorFactory.VALIDATOR_TYPE_CLAS_MAP.containsKey(key)) {
                ValidatorFactory.VALIDATOR_TYPE_CLAS_MAP.get(key).newInstance().validate(entry.getValue());
            }
        }
    }
}
