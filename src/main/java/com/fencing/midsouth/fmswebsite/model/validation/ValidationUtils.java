package com.fencing.midsouth.fmswebsite.model.validation;

import jakarta.validation.ConstraintValidatorContext;

public class ValidationUtils {
    public static void configureContext(ConstraintValidatorContext context,
                                        String message,
                                        String fieldName) {
        context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate(message)
                .addPropertyNode(fieldName)
                .addConstraintViolation();
    }
}
