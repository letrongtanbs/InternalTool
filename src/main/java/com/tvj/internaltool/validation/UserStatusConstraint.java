package com.tvj.internaltool.validation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import com.tvj.internaltool.validation.validator.UserStatusValidator;

@Documented
@Constraint(validatedBy = UserStatusValidator.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface UserStatusConstraint {
    String message() default "user status is invalid!!";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}

