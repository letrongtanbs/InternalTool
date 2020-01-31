package com.tvj.internaltool.service;

import com.tvj.internaltool.dto.req.ForgotPasswordReqDto;
import com.tvj.internaltool.entity.UserEntity;
import org.springframework.security.core.userdetails.UserDetails;

public interface UserService {

    UserDetails processLogin(String username, String password);

    UserDetails getUserDetails(String username);

    UserEntity getUserByUsername(String username);

    boolean processForgotPassword(ForgotPasswordReqDto forgotPasswordReqDto);
}
