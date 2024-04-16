package com.fencing.midsouth.fmswebsite.model.validation;

import com.fencing.midsouth.fmswebsite.model.dto.EventForm;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.ZonedDateTime;
import java.time.format.DateTimeParseException;

public class EndDateValidator implements ConstraintValidator<EndDate, EventForm> {

    private String fieldName;

    private String message;

    @Override
    public void initialize(EndDate constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
        this.fieldName = constraintAnnotation.fieldName();
        this.message = constraintAnnotation.message();
    }

    @Override
    public boolean isValid(EventForm eventForm, ConstraintValidatorContext context) {
        try {
            ZonedDateTime startDate = ZonedDateTime.parse(eventForm.getStartDate());
            ZonedDateTime endDate = ZonedDateTime.parse(eventForm.getEndDate());
            if (!endDate.isAfter(startDate)) {
                ValidationUtils.configureContext(context, message, fieldName);
                return false;
            }
        } catch (DateTimeParseException | NullPointerException ignored) {
        }

        return true;
    }
}
