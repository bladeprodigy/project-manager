package com.project_manager.project_manager.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

public class EmailValidator implements ConstraintValidator<ValidEmail, String> {

  private static final Pattern EMAIL_PATTERN =
      Pattern.compile("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$");

  @Override
  public boolean isValid(String email, ConstraintValidatorContext context) {
    return email != null &&
        email.length() >= 7 &&
        email.length() <= 50 &&
        EMAIL_PATTERN.matcher(email).matches();
  }
}

