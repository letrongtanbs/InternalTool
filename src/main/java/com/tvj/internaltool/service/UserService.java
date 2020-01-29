package com.tvj.internaltool.service;

import org.springframework.security.core.userdetails.UserDetails;

public interface UserService {

    UserDetails processLogin(String username, String password);

    UserDetails getUserDetails(String username);
}
