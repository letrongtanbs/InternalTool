package com.tvj.internaltool.service.impl;

import com.tvj.internaltool.dto.req.RecoverPasswordReqDto;
import com.tvj.internaltool.dto.req.UpdatePasswordReqDto;
import com.tvj.internaltool.dto.req.UserSettingReqDto;
import com.tvj.internaltool.dto.res.UserLoginResDto;
import com.tvj.internaltool.dto.res.UserSettingResDto;
import com.tvj.internaltool.entity.ForgotPasswordTokenEntity;
import com.tvj.internaltool.entity.UserEntity;
import com.tvj.internaltool.entity.UserSettingEntity;
import com.tvj.internaltool.enums.AvatarFileType;
import com.tvj.internaltool.repository.ForgotPasswordTokenRepository;
import com.tvj.internaltool.repository.UserRepository;
import com.tvj.internaltool.repository.UserSettingRepository;
import com.tvj.internaltool.security.JwtTokenUtil;
import com.tvj.internaltool.service.EmailService;
import com.tvj.internaltool.service.FileStorageService;
import com.tvj.internaltool.service.UserService;
import com.tvj.internaltool.utils.EnvironmentUtils;
import com.tvj.internaltool.utils.ModelMapperUtils;
import com.tvj.internaltool.utils.ResponseCode;
import com.tvj.internaltool.utils.UserUtils;
import org.apache.commons.text.CharacterPredicates;
import org.apache.commons.text.RandomStringGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import org.springframework.web.multipart.MultipartFile;

import javax.mail.MessagingException;
import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Value("${front-end-host}")
    private String frontEndHost;

    @Value("${file.avatar-upload-dir}")
    private String avatarUploadDir;

    @Value("${account-is-locked.mail-subject}")
    private String accountIsLockedMailSubject;

    @Value("${account-is-locked.mail-template}")
    private String accountIsLockedMailTemplate;

    @Value("${forgot-password.mail-subject}")
    private String forgotPasswordMailSubject;

    @Value("${forgot-password.mail-template}")
    private String forgotPasswordMailTemplate;

    @Value("${forgot-password.token-expired-duration-in-hour}")
    private long forgotPasswordDurationInHour;

    @Value("${forgot-password.max-login-failed-count}")
    private int forgotPasswordMaxLoginFailedCount;

    private final JwtTokenUtil jwtTokenUtil;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final UserSettingRepository userSettingRepository;
    private final EmailService emailService;
    private final FileStorageService fileStorageService;
    private final EnvironmentUtils environmentUtils;
    private final ForgotPasswordTokenRepository forgotPasswordTokenRepository;

    public UserServiceImpl(JwtTokenUtil jwtTokenUtil, PasswordEncoder passwordEncoder, UserRepository userRepository, UserSettingRepository userSettingRepository, EmailService emailService, EnvironmentUtils environmentUtils, ForgotPasswordTokenRepository forgotPasswordTokenRepository, FileStorageService fileStorageService) {
        this.jwtTokenUtil = jwtTokenUtil;
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.userSettingRepository = userSettingRepository;
        this.emailService = emailService;
        this.fileStorageService = fileStorageService;
        this.environmentUtils = environmentUtils;
        this.forgotPasswordTokenRepository = forgotPasswordTokenRepository;
    }

    @Override
    public Object processLogin(String username, String password) throws UsernameNotFoundException, DataAccessException {

        // Check if user exists
        UserEntity userEntity = userRepository.findByUsername(username);
        if (userEntity == null) {
            return ResponseCode.UNAUTHORIZED;
        }

        // Check if user is deactivated or locked
        if (!userEntity.isActive() || userEntity.getLoginFailCount() == forgotPasswordMaxLoginFailedCount) {
            return ResponseCode.USER_IS_LOCKED;
        }

        // Compare password
        if (!passwordEncoder.matches(password, userEntity.getPassword())) {
            userEntity.setLoginFailCount(userEntity.getLoginFailCount() + 1);

            // Lock user if login failed times more than threshold
            if (userEntity.getLoginFailCount() == forgotPasswordMaxLoginFailedCount) {
                try {
                    // Send notification email
                    emailService.sendSimpleMessage(userEntity.getEmail()
                            , accountIsLockedMailSubject
                            , MessageFormat.format(accountIsLockedMailTemplate,
                                    userEntity.getUsername(),
                                    LocalDateTime.now()));
                } catch (MessagingException e) {
                    logger.error(e.getMessage());
                    return false;
                }
            }
            userRepository.save(userEntity);
            return ResponseCode.UNAUTHORIZED;
        }

        // Reset login fail count of login success
        if (userEntity.getLoginFailCount() != 0) {
            userEntity.setLoginFailCount(0);
            userRepository.save(userEntity);
        }

        // Build token
        UserDetails userDetails = buildUserDetails(userEntity);
        String token = jwtTokenUtil.generateToken(userDetails);

        // Return UserLoginResDto
        UserLoginResDto userLoginResDto = new UserLoginResDto();
        userLoginResDto.setToken(token);
        userLoginResDto.setFirstName(userEntity.getFirstName());
        userLoginResDto.setLastName(userEntity.getLastName());
        userLoginResDto.setRoleName(userEntity.getRole().getRoleName());
        userLoginResDto.setFirstTimeLogin(userEntity.isFirstTimeLogin());

        return userLoginResDto;
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
                            userEntity.getUsername(),
                            frontEndHost,
                            environmentUtils.getPort(),
                            randomLetters));
        } catch (MessagingException e) {
            logger.error(e.getMessage());
            return false;
        }

        return true;
    }

    @Override
    public boolean processConfirmForgotPasswordToken(String token) {
        ForgotPasswordTokenEntity forgotPasswordTokenEntity = forgotPasswordTokenRepository.findByTokenString(token);
        return isTokenValid(forgotPasswordTokenEntity);
    }

    @Transactional
    @Override
    public boolean processRecoverPassword(RecoverPasswordReqDto recoverPasswordReqDto) {

        ForgotPasswordTokenEntity forgotPasswordTokenEntity = forgotPasswordTokenRepository.findByTokenString(recoverPasswordReqDto.getToken());
        if (!isTokenValid(forgotPasswordTokenEntity)) {
            return false;
        }

        Optional<UserEntity> userEntity = userRepository.findById(forgotPasswordTokenEntity.getUserId());
        if (userEntity.isPresent()) {
            UserEntity updatedUser = userEntity.get();
            updatedUser.setPassword(passwordEncoder.encode(recoverPasswordReqDto.getNewPassword()));
            updatedUser.setLoginFailCount(0);

            // Verify user changed password after first time login
            if (updatedUser.isFirstTimeLogin()) {
                updatedUser.setFirstTimeLogin(false);
            }

            userRepository.save(updatedUser);
            forgotPasswordTokenRepository.delete(forgotPasswordTokenEntity);
            return true;
        }

        return false;
    }

    @Override
    public UserSettingResDto getUserSetting() {

        UserEntity userEntity = userRepository.findByUsername(UserUtils.getCurrentUsername());

        if (userEntity != null) {
            return buildUserSettingResDto(userEntity);
        }

        return null;
    }

    @Transactional
    @Override
    public UserSettingResDto updateUserSetting(UserSettingReqDto userSettingReqDto) {

        UserEntity userEntity = userRepository.findByUsername(UserUtils.getCurrentUsername());

        if (userEntity != null) {
            UserSettingEntity userSettingEntity = userEntity.getUserSettingEntity();
            userSettingEntity.setTeamId(userSettingReqDto.getTeamId());
            userSettingEntity.setTitle(userSettingReqDto.getTitle());
            userSettingEntity.setAddress(userSettingReqDto.getAddress());
            userSettingEntity.setPhone(userSettingReqDto.getPhone());
            userSettingEntity.setCountryId(userSettingReqDto.getCountryId());
            userSettingEntity.setLanguageId(userSettingReqDto.getLanguageId());
            userSettingEntity.setStatus(userSettingReqDto.getStatus());
            userSettingEntity.setUpdatedDate(LocalDateTime.now());
            userSettingEntity.setUpdatedBy(userEntity.getUsername());

            // flush data to repository in current transaction, then refresh to get latest data
            userSettingRepository.saveAndFlush(userSettingEntity);
            userRepository.refresh(userEntity);

            return buildUserSettingResDto(userEntity);
        }

        return null;
    }

    @Transactional
    @Override
    public boolean updatePassword(UpdatePasswordReqDto updatePasswordReqDto) {

        UserEntity userEntity = userRepository.findByUsername(UserUtils.getCurrentUsername());

        if (userEntity != null) {

            // Compare current password with inputted password
            if (!passwordEncoder.matches(updatePasswordReqDto.getOldPassword(), userEntity.getPassword())) {
                return false;
            }

            // Verify user changed password after first time login
            if (userEntity.isFirstTimeLogin()) {
                userEntity.setFirstTimeLogin(false);
            }

            userEntity.setPassword(passwordEncoder.encode(updatePasswordReqDto.getNewPassword()));
            userRepository.save(userEntity);
            return true;
        }

        return false;
    }

    @Transactional
    @Override
    public String uploadAvatar(MultipartFile file) {

        if (!isFileValid(file)) {
            return null;
        }

        UserEntity userEntity = userRepository.findByUsername(UserUtils.getCurrentUsername());

        if (userEntity != null) {
            String fileName = fileStorageService.storeFile(file);
            UserSettingEntity userSettingEntity = userEntity.getUserSettingEntity();
            userSettingEntity.setAvatar(fileName);
            userSettingRepository.save(userSettingEntity);
            return avatarUploadDir + fileName;
        }

        return null;
    }

    @Override
    public boolean removeAvatar() {
        UserEntity userEntity = userRepository.findByUsername(UserUtils.getCurrentUsername());

        if (userEntity != null) {
            UserSettingEntity userSettingEntity = userEntity.getUserSettingEntity();
            userSettingEntity.setAvatar(null);
            userSettingRepository.save(userSettingEntity);
            return true;
        }

        return false;
    }

    private boolean isTokenValid(ForgotPasswordTokenEntity forgotPasswordTokenEntity) {

        // Check if token exists
        if (forgotPasswordTokenEntity == null) {
            return false;
        }

        // Check if token expired
        if (forgotPasswordTokenEntity.getTokenExpiredDate().isBefore(LocalDateTime.now())) {
            return false;
        }

        return true;
    }

    private boolean isFileValid(MultipartFile file) {
        AvatarFileType[] fileTypes = AvatarFileType.values();

        boolean isFileValid = false;

        for (AvatarFileType fileType : fileTypes) {
            if (fileType.getFileType().equals(file.getContentType())) {
                isFileValid = true;
                break;
            }
        }

        return isFileValid;
    }

    private UserSettingResDto buildUserSettingResDto(UserEntity userEntity) {
        UserSettingEntity userSettingEntity = userEntity.getUserSettingEntity();
        UserSettingResDto userSettingResDto = ModelMapperUtils.map(userSettingEntity, UserSettingResDto.class);
        userSettingResDto.setUsername(userEntity.getUsername());
        userSettingResDto.setFirstName(userEntity.getFirstName());
        userSettingResDto.setLastName(userEntity.getLastName());
        userSettingResDto.setEmail(userEntity.getEmail());
        userSettingResDto.setDepartmentId(userSettingEntity.getTeamEntity().getDepartmentId());
        userSettingResDto.setDepartmentName(userSettingEntity.getTeamEntity().getDepartmentEntity().getDepartmentName());
        return userSettingResDto;
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
