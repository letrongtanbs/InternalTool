package com.tvj.internaltool.validation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import com.tvj.internaltool.validation.validator.UserEmailValidator;

@Documented
@Constraint(validatedBy = UserEmailValidator.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface UserEmailConstraint {
    String message() default "user email is invalid!!";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
