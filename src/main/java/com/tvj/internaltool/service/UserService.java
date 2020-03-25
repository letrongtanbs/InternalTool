package com.tvj.internaltool.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.multipart.MultipartFile;

import com.tvj.internaltool.dto.req.RecoverPasswordReqDto;
import com.tvj.internaltool.dto.req.UpdatePasswordReqDto;
import com.tvj.internaltool.dto.req.UserSettingReqDto;
import com.tvj.internaltool.dto.res.UserSettingResDto;
import com.tvj.internaltool.entity.UserEntity;

public interface UserService {

    Object processLogin(String username, String password);

    UserDetails getUserDetails(String username);

    UserEntity getUserByUsername(String username);

    boolean processForgotPassword(String forgotPasswordReqDto);

    boolean processConfirmForgotPasswordToken(String token);

    boolean processRecoverPassword(RecoverPasswordReqDto recoverPasswordReqDto);

    UserSettingResDto getUserSetting();

    UserSettingResDto updateUserSetting(UserSettingReqDto userSettingReqDto);

    boolean updatePassword(UpdatePasswordReqDto updatePasswordReqDto);

    String uploadAvatar(MultipartFile file);

    boolean removeAvatar();
    
    boolean saveLastLogout();
    
}
