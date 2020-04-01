package com.tvj.internaltool.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.multipart.MultipartFile;

import com.tvj.internaltool.dto.req.PasswordRecoverUpdatePasswordReqDto;
import com.tvj.internaltool.dto.req.UserSettingUpdatePasswordReqDto;
import com.tvj.internaltool.dto.req.UserSettingUpdateReqDto;
import com.tvj.internaltool.dto.res.UserSettingResDto;
import com.tvj.internaltool.entity.UserEntity;

public interface UserService {

    Object processLogin(String username, String password);

    UserDetails getUserDetails(String username);

    UserEntity findActivatedUserByUsername(String username);

    boolean processForgotPassword(String username);

    boolean processConfirmForgotPasswordToken(String token);

    boolean processRecoverPassword(PasswordRecoverUpdatePasswordReqDto passwordRecoverUpdatePasswordReqDto);

    UserSettingResDto getUserSetting();

    UserSettingResDto updateUserSetting(UserSettingUpdateReqDto userSettingUpdateReqDto);

    boolean updatePassword(UserSettingUpdatePasswordReqDto userSettingUpdatePasswordReqDto);

    String uploadAvatar(MultipartFile file);

    boolean removeAvatar();
    
    boolean saveLastLogout();
    
}
