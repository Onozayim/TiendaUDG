package com.tienda.tienda.annotations;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ValidIdValidator implements ConstraintValidator<ValidId, String>{
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if(value.isEmpty()) return false;
        if(!value.matches("\\d+")) return false;


        return true;
    }
}
