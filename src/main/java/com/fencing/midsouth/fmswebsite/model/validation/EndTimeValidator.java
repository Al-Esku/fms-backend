package com.fencing.midsouth.fmswebsite.model.validation;

import com.fencing.midsouth.fmswebsite.model.dto.SessionForm;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.sql.Time;

public class EndTimeValidator implements ConstraintValidator<EndTime, SessionForm> {

    private String fieldName;

    private String message;

    @Override
    public void initialize(EndTime constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
        this.fieldName = constraintAnnotation.fieldName();
        this.message = constraintAnnotation.message();
    }

    @Override
    public boolean isValid(SessionForm sessionForm, ConstraintValidatorContext context) {
        try {
            Time startTime = Time.valueOf(sessionForm.getStartTime());
            Time endTime = Time.valueOf(sessionForm.getEndTime());
            if (!endTime.after(startTime)) {
                ValidationUtils.configureContext(context, message, fieldName);
                return false;
            }
        } catch (IllegalArgumentException ignored) {
        }

        return true;
    }
}
