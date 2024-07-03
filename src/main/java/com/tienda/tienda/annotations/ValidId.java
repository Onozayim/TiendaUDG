package com.tienda.tienda.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ValidIdValidator.class)
@Documented
public @interface ValidId {
    String message() default "Porfavor ingrese un id valido";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
