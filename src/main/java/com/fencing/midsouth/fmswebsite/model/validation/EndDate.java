package com.fencing.midsouth.fmswebsite.model.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@Constraint(validatedBy = EndDateValidator.class)
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface EndDate {

    String message() default "End date must be after start date";

    String fieldName();

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
