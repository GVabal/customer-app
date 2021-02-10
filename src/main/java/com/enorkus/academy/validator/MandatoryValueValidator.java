package com.enorkus.academy.validator;

import com.enorkus.academy.exception.ValidationException;
import org.springframework.stereotype.Component;

@Component
public class MandatoryValueValidator extends Validator {
    @Override
    public void validate(Object attribute, String message) {
        if ((attribute instanceof String && ((String) attribute).trim().isEmpty())
             || attribute == null) {
            throw new ValidationException(message);
        }
    }
}
