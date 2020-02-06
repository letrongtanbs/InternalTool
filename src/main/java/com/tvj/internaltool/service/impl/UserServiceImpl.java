package com.tvj.internaltool.service.impl;

import com.tvj.internaltool.dto.req.RecoverPasswordReqDto;
import com.tvj.internaltool.entity.ForgotPasswordTokenEntity;
import com.tvj.internaltool.entity.UserEntity;
import com.tvj.internaltool.repository.ForgotPasswordTokenRepository;
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
import org.springframework.transaction.annotation.Transactional;

import javax.mail.MessagingException;
import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    @Value("${forgot-password.mail-subject}")
    private String forgotPasswordMailSubject;

    @Value("${forgot-password.mail-template}")
    private String forgotPasswordMailTemplate;

    @Value("${forgot-password.token-expired-duration-in-hour}")
    private long forgotPasswordDurationInHour;

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final EmailService emailService;
    private final EnvironmentUtils environmentUtils;
    private final ForgotPasswordTokenRepository forgotPasswordTokenRepository;

    public UserServiceImpl(PasswordEncoder passwordEncoder, UserRepository userRepository, EmailService emailService, EnvironmentUtils environmentUtils, ForgotPasswordTokenRepository forgotPasswordTokenRepository) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.emailService = emailService;
        this.environmentUtils = environmentUtils;
        this.forgotPasswordTokenRepository = forgotPasswordTokenRepository;
    }

    public UserDetails processLogin(String username, String password) throws UsernameNotFoundException, DataAccessException {

        // Check if user exists
        UserEntity user = userRepository.findByUsername(username);
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

        // Check if user exists
        UserEntity user = userRepository.findByUsername(username);
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
    @Transactional
    public boolean processForgotPassword(String username) {

        // Check if user exits
        UserEntity userEntity = userRepository.findByUsername(username);
        if (userEntity == null) {
            return false;
        }

        // Generates token
        RandomStringGenerator generator =
                new RandomStringGenerator.Builder().withinRange('0', 'z').filteredBy(CharacterPredicates.LETTERS, CharacterPredicates.DIGITS).build();
        String randomLetters = generator.generate(99);

        // Remove old token
        forgotPasswordTokenRepository.deleteTokenByUserId(userEntity.getUserId());

        // Save new token
        ForgotPasswordTokenEntity newToken = new ForgotPasswordTokenEntity();
        newToken.setTokenId(UUID.randomUUID().toString());
        newToken.setUserId(userEntity.getUserId());
        newToken.setTokenString(randomLetters);
        newToken.setTokenExpiredDate(LocalDateTime.now().plusHours(forgotPasswordDurationInHour));
        forgotPasswordTokenRepository.save(newToken);

        try {
            // Send confirmation email
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

    @Override
    public String processConfirmForgotPasswordToken(String token) {

        // Check if token exists
        ForgotPasswordTokenEntity forgotPasswordTokenEntity = forgotPasswordTokenRepository.findByTokenString(token);
        if (forgotPasswordTokenEntity == null) {
            return null;
        }

        // Check if token expired
        if (forgotPasswordTokenEntity.getTokenExpiredDate().isBefore(LocalDateTime.now())) {
            return null;
        }

        return token;
    }

    @Transactional
    @Override
    public boolean processRecoverPassword(RecoverPasswordReqDto recoverPasswordReqDto) {

        // Check if token exists
        ForgotPasswordTokenEntity forgotPasswordTokenEntity = forgotPasswordTokenRepository.findByTokenString(recoverPasswordReqDto.getToken());
        if (forgotPasswordTokenEntity == null) {
            return false;
        }

        // Check if token expired
        if (forgotPasswordTokenEntity.getTokenExpiredDate().isBefore(LocalDateTime.now())) {
            return false;
        }

        Optional<UserEntity> userEntity = userRepository.findById(forgotPasswordTokenEntity.getUserId());
        if (userEntity.isPresent()) {
            UserEntity updatedUser = userEntity.get();
            updatedUser.setPassword(passwordEncoder.encode(recoverPasswordReqDto.getNewPassword()));
            userRepository.save(updatedUser);
            forgotPasswordTokenRepository.delete(forgotPasswordTokenEntity);
            return true;
        }

        return false;
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
