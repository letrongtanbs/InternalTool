package com.tvj.internaltool.service;

import com.tvj.internaltool.dto.req.RecoverPasswordReqDto;
import com.tvj.internaltool.dto.res.UserLoginResDto;
import com.tvj.internaltool.dto.res.UserSettingResDto;
import com.tvj.internaltool.entity.UserEntity;
import org.springframework.security.core.userdetails.UserDetails;

public interface UserService {

    UserLoginResDto processLogin(String username, String password);

    UserDetails getUserDetails(String username);

    UserEntity getUserByUsername(String username);

    boolean processForgotPassword(String forgotPasswordReqDto);

    String processConfirmForgotPasswordToken(String token);

    boolean processRecoverPassword(RecoverPasswordReqDto recoverPasswordReqDto);

    UserSettingResDto getUserSetting();
}
