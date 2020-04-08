package com.tvj.internaltool.validation.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.lang3.StringUtils;

import com.tvj.internaltool.validation.UserEmailConstraint;

public class UserEmailValidator implements ConstraintValidator<UserEmailConstraint, String> {

    private final static String EMAIL_DOMAIN = "tinhvan.com";

    @Override
    public void initialize(UserEmailConstraint constraint) {
    }

    @Override
    public boolean isValid(String email, ConstraintValidatorContext constraintValidatorContext) {

        if (StringUtils.isBlank(email)) {
            return true;
        }

        String inputedEmailDomain = email.substring(email.indexOf("@") + 1);

        if (inputedEmailDomain.equals(EMAIL_DOMAIN)) {
            return true;
        }

        return false;
    }
}
