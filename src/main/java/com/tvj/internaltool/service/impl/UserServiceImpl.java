package com.tvj.internaltool.service.impl;

import com.tvj.internaltool.dto.req.ForgotPasswordReqDto;
import com.tvj.internaltool.entity.UserEntity;
import com.tvj.internaltool.repository.UserRepository;
import com.tvj.internaltool.service.EmailService;
import com.tvj.internaltool.service.UserService;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final EmailService emailService;

    public UserServiceImpl(PasswordEncoder passwordEncoder, UserRepository userRepository, EmailService emailService) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.emailService = emailService;
    }

    public UserDetails processLogin(String username, String password) throws UsernameNotFoundException, DataAccessException {
        UserEntity user = userRepository.findByUsername(username);

        // Check if user exists
        if (user == null) {
            return null;
        }

        // Compare password
        if (!passwordEncoder.matches(password, user.getPassword())) {
            return null;
        }

        return buildUserDetails(user);
    }

    @Override
    public UserDetails getUserDetails(String username) {
        UserEntity user = userRepository.findByUsername(username);

        // Check if user exists
        if (user == null) {
            return null;
        }

        return buildUserDetails(user);
    }

    @Override
    public UserEntity getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public boolean processForgotPassword(ForgotPasswordReqDto forgotPasswordReqDto) {
        UserEntity userEntity = userRepository.findByUsername(forgotPasswordReqDto.getUsername());
        if (userEntity == null) {
            return false;
        }

        emailService.sendSimpleMessage(userEntity.getEmail(), "Forgot password mail verification", "This is token");
        return true;
    }

    private User buildUserDetails(UserEntity user) {
        boolean enabled = true;
        boolean accountNonExpired = true;
        boolean credentialsNonExpired = true;
        boolean accountNonLocked = true;

        // Get role of current login user
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(user.getRole().getRoleName()));

        // By this way, UserEntity does not need to implement UserDetails
        return new User(user.getUsername(), user.getPassword(), enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
    }

}
