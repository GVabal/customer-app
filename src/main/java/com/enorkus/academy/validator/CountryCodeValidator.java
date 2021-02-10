package com.enorkus.academy.validator;

import com.enorkus.academy.entity.CountryCode;
import com.enorkus.academy.exception.ValidationException;
import org.springframework.stereotype.Component;

@Component
public class CountryCodeValidator extends Validator<String> {
    @Override
    public void validate(String attribute, String message) {
        try {
            CountryCode.valueOf(attribute);
        } catch (IllegalArgumentException e) {
            throw new ValidationException(message);
        }
    }
}
