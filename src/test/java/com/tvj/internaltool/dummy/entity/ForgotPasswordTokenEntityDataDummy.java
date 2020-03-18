package com.tvj.internaltool.dummy.entity;

import java.time.LocalDateTime;

import com.tvj.internaltool.entity.ForgotPasswordTokenEntity;

public class ForgotPasswordTokenEntityDataDummy {

    public ForgotPasswordTokenEntity getForgotPasswordTokenEntity() {
        ForgotPasswordTokenEntity forgotPasswordTokenEntity = new ForgotPasswordTokenEntity();
        forgotPasswordTokenEntity.setTokenId("1");
        forgotPasswordTokenEntity.setTokenString("Token1");
        forgotPasswordTokenEntity.setUsername("admin1");
        forgotPasswordTokenEntity.setTokenExpiredDate(LocalDateTime.now());
        return forgotPasswordTokenEntity;
    }
}
