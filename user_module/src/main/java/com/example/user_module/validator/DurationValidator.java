package com.example.user_module.validator;

import com.example.user_module.annotation.Duration;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;


import java.time.LocalDate;

public class DurationValidator implements ConstraintValidator<Duration, LocalDate> {

    @Override
    public void initialize(Duration constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(LocalDate localDate, ConstraintValidatorContext constraintValidatorContext) {
        if (localDate == null) {
            return false;
        }
        return localDate.isAfter(LocalDate.of(1900, 1,1))  && localDate.isBefore(LocalDate.of(2100, 12, 31)) ;
    }
}