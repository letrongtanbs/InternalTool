package com.tvj.internaltool.validation.validator;

import com.tvj.internaltool.enums.UserStatus;
import com.tvj.internaltool.validation.UserStatusConstraint;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UserStatusValidator implements ConstraintValidator<UserStatusConstraint, Integer> {

    @Override
    public void initialize(UserStatusConstraint constraint) {
    }

    @Override
    public boolean isValid(Integer status, ConstraintValidatorContext constraintValidatorContext) {
        UserStatus[] allStatuses = UserStatus.values();

        if (status == null) {
            return true;
        }

        for (UserStatus activateStatus : allStatuses) {
            if (activateStatus.getStatus() == status) {
                return true;
            }
        }

        return false;
    }
}
