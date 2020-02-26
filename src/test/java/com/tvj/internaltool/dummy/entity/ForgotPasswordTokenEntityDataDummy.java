package com.tvj.internaltool.dummy.entity;

import com.tvj.internaltool.entity.ForgotPasswordTokenEntity;

import java.time.LocalDateTime;

public class ForgotPasswordTokenEntityDataDummy {

    public ForgotPasswordTokenEntity getForgotPasswordTokenEntity() {
        ForgotPasswordTokenEntity forgotPasswordTokenEntity = new ForgotPasswordTokenEntity();
        forgotPasswordTokenEntity.setTokenId("1");
        forgotPasswordTokenEntity.setTokenString("Token1");
        forgotPasswordTokenEntity.setUserId("admin1");
        forgotPasswordTokenEntity.setTokenExpiredDate(LocalDateTime.now());
        return forgotPasswordTokenEntity;
    }
}
