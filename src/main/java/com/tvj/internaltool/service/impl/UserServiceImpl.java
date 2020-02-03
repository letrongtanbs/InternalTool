package com.tvj.internaltool.service.impl;

import com.tvj.internaltool.dto.req.ForgotPasswordReqDto;
import com.tvj.internaltool.entity.UserEntity;
import com.tvj.internaltool.repository.UserRepository;
import com.tvj.internaltool.service.EmailService;
import com.tvj.internaltool.service.UserService;
import com.tvj.internaltool.utils.EnvironmentUtils;
import org.apache.commons.text.CharacterPredicates;
import org.apache.commons.text.RandomStringGenerator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Value("${forgot-password.mail-subject}")
    private String forgotPasswordMailSubject;

    @Value("${forgot-password.mail-template}")
    private String forgotPasswordMailTemplate;

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final EmailService emailService;
    private final EnvironmentUtils environmentUtils;

    public UserServiceImpl(PasswordEncoder passwordEncoder, UserRepository userRepository, EmailService emailService, EnvironmentUtils environmentUtils) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.emailService = emailService;
        this.environmentUtils = environmentUtils;
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

        // Generates a 20 code point string, using only the letters a-z
        RandomStringGenerator generator =
                new RandomStringGenerator.Builder().withinRange('0', 'z').filteredBy(CharacterPredicates.LETTERS, CharacterPredicates.DIGITS).build();
        String randomLetters = generator.generate(99);

        try {
            emailService.sendSimpleMessage(userEntity.getEmail()
                    , forgotPasswordMailSubject
                    , MessageFormat.format(forgotPasswordMailTemplate,
                            userEntity.getFirstName(),
                            userEntity.getLastName(),
                            "192.168.1.2", // InetAddress.getLocalHost().getHostAddress()
                            environmentUtils.getPort(),
                            randomLetters));
        } catch (MessagingException e) {
            e.printStackTrace();
            return false;
        }

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
