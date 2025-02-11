package com.project_manager.project_manager.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Constraint(validatedBy = PasswordValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidPassword {

  String message() default "Password must contain at least one digit, one lowercase letter, one uppercase letter, one special character, and be between 8 and 20 characters";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};
}
