package com.tvj.internaltool.validation.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;

import com.tvj.internaltool.validation.UserEmailConstraint;

public class UserEmailValidator implements ConstraintValidator<UserEmailConstraint, String> {

    @Value("${tinhvan-email-domain}")
    private String emailDomain;

    @Override
    public void initialize(UserEmailConstraint constraint) {
    }

    @Override
    public boolean isValid(String email, ConstraintValidatorContext constraintValidatorContext) {

        if (StringUtils.isBlank(email)) {
            return true;
        }

        String inputedEmailDomain = email.substring(email.indexOf("@") + 1);

        if (inputedEmailDomain.equals(emailDomain)) {
            return true;
        }

        return false;
    }
}
