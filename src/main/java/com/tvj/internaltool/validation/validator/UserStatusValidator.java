package com.tvj.internaltool.validation.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.lang3.StringUtils;

import com.tvj.internaltool.enums.UserStatus;
import com.tvj.internaltool.validation.UserStatusConstraint;

public class UserStatusValidator implements ConstraintValidator<UserStatusConstraint, String> {

    @Override
    public void initialize(UserStatusConstraint constraint) {
    }

    @Override
    public boolean isValid(String status, ConstraintValidatorContext constraintValidatorContext) {
        UserStatus[] allStatuses = UserStatus.values();

        if (StringUtils.isBlank(status)) {
            return true;
        }

        for (UserStatus activateStatus : allStatuses) {
            if (activateStatus.getStatus().equals(status)) {
                return true;
            }
        }

        return false;
    }
}
