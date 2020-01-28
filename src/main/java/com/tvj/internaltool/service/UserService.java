package com.tvj.internaltool.service;

import com.tvj.internaltool.entity.UserEntity;
import org.springframework.security.core.userdetails.UserDetails;

public interface UserService {

    UserDetails processLogin(String username, String password);

    UserEntity findByUsername(String username);

    UserDetails getUserDetails(String username);
}
