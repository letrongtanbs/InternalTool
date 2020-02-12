package com.tvj.internaltool.validation;

import com.tvj.internaltool.validation.validator.UserStatusValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = UserStatusValidator.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface UserStatusConstraint {
    String message() default "Invalid User Status!!";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}

