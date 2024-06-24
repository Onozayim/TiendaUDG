package com.tienda.tienda.annotations;

import org.springframework.beans.BeanWrapperImpl;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PasswordMatchingValidator implements ConstraintValidator<PasswordMatching, Object> {

  private String password;
  private String confirm_password;

  @Override
  public void initialize(PasswordMatching matching) {
    this.password = matching.password();
    this.confirm_password = matching.confirmPassword();
  }

  @Override
  public boolean isValid(Object value, ConstraintValidatorContext constraintValidatorContext) {
    Object passwordValue = new BeanWrapperImpl(value).getPropertyValue(password);
    Object confirm_passwordValue = new BeanWrapperImpl(value).getPropertyValue(confirm_password);

    return (passwordValue != null) ? passwordValue.equals(confirm_passwordValue) : confirm_passwordValue == null;
  }
}