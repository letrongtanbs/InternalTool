package com.tvj.internaltool.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

import javax.mail.MessagingException;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.tvj.internaltool.dto.req.PasswordRecoverUpdatePasswordReqDto;
import com.tvj.internaltool.dto.req.UserSettingUpdatePasswordReqDto;
import com.tvj.internaltool.dto.req.UserSettingUpdateReqDto;
import com.tvj.internaltool.dto.res.UserLoginResDto;
import com.tvj.internaltool.dto.res.UserSettingResDto;
import com.tvj.internaltool.dummy.entity.ForgotPasswordTokenEntityDataDummy;
import com.tvj.internaltool.dummy.entity.UserEntityDataDummy;
import com.tvj.internaltool.dummy.entity.UserSettingEntityDataDummy;
import com.tvj.internaltool.entity.ForgotPasswordTokenEntity;
import com.tvj.internaltool.entity.UserEntity;
import com.tvj.internaltool.entity.UserSettingEntity;
import com.tvj.internaltool.enums.UserStatus;
import com.tvj.internaltool.repository.ForgotPasswordTokenRepository;
import com.tvj.internaltool.repository.UserRepository;
import com.tvj.internaltool.repository.UserSettingRepository;
import com.tvj.internaltool.security.JwtTokenUtil;
import com.tvj.internaltool.utils.ResponseCode;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceImplTest {

    @InjectMocks
    private UserServiceImpl userService; // cannot InjectMocks interface

    @Mock
    private EmailServiceImpl emailService; // this will be injected into userService (use for when(...))

    @Mock
    private UserRepository userRepository; // this will be injected into userService (use for when(...))

    @Mock
    private UserSettingRepository userSettingRepository; // this will be injected into userService (use for when(...))

    @Mock
    private ForgotPasswordTokenRepository forgotPasswordTokenRepository; // this will be injected into userService (use
                                                                         // for when(...))
    @Mock
    private JwtTokenUtil jwtTokenUtil; // this will be injected into userService (use for when(...))

    @Mock
    private FileStorageServiceImpl fileStorageService; // this will be injected into userService (use for when(...))

    private final static String currentUsername = "admin1";

    @Before
    public void setUp() {
        // Initialize value for @Value parameter
        ReflectionTestUtils.setField(userService, "forgotPasswordMaxLoginFailedCount", 5);

        // Override value
        ReflectionTestUtils.setField(userService, "passwordEncoder", new BCryptPasswordEncoder());
        ReflectionTestUtils.setField(userService, "accountIsLockedMailSubject", "Mail Subject 1");
        ReflectionTestUtils.setField(userService, "accountIsLockedMailTemplate", "Mail Template 1");
        ReflectionTestUtils.setField(userService, "forgotPasswordMailSubject", "Mail Subject 2");
        ReflectionTestUtils.setField(userService, "forgotPasswordMailTemplate", "Mail Template 2");

        // Mocking Spring Security Context
        Authentication authentication = mock(Authentication.class);
        SecurityContext securityContext = mock(SecurityContext.class);
        SecurityContextHolder.setContext(securityContext);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(SecurityContextHolder.getContext().getAuthentication().getName()).thenReturn(currentUsername);
    }

    // ---------- processLogin START ---------

    @Test // Do not use org.junit.jupiter.api
    public void processLogin_success() {
        // Value from client
        String username = "admin1";
        String password = "12345678";

        UserEntityDataDummy userEntityDataDummy = new UserEntityDataDummy();
        UserEntity admin = userEntityDataDummy.getAdminUser1();
        when(userRepository.findActivatedUserByUsername(username)).thenReturn(admin);
        when(jwtTokenUtil.generateToken(any(UserDetails.class))).thenReturn("sampleToken");

        Object userLoginResDto = userService.processLogin(username, password);

        if (userLoginResDto instanceof UserLoginResDto) {
            assertEquals(((UserLoginResDto) userLoginResDto).getFirstName(), admin.getFirstName());
            assertEquals(((UserLoginResDto) userLoginResDto).getLastName(), admin.getLastName());
            assertEquals(((UserLoginResDto) userLoginResDto).getRoleName(), admin.getRole().getRoleName());
            assertEquals(((UserLoginResDto) userLoginResDto).getToken(), "sampleToken");
            assertEquals(((UserLoginResDto) userLoginResDto).isFirstTimeLogin(), admin.isFirstTimeLogin());
            verify(userRepository, times(1)).findActivatedUserByUsername(admin.getUsername());
            try {
                verify(emailService, times(0)).sendSimpleMessage(any(), any(), any());
            } catch (MessagingException e) {
                fail("Cannot send email!!");
            }
            verify(userRepository, times(1)).save(any(UserEntity.class));
            verify(jwtTokenUtil, times(1)).generateToken(any(UserDetails.class));
        } else {
            fail("Must return error code!!");
        }
    }

    @Test // Do not use org.junit.jupiter.api
    public void processLogin_resetLoginFailedCountAfterLoginSuccess() {
        // Value from client
        String username = "admin1";
        String password = "12345678";

        UserEntityDataDummy userEntityDataDummy = new UserEntityDataDummy();
        UserEntity admin = userEntityDataDummy.getAdminUser1();
        admin.setLoginFailCount(1);
        when(userRepository.findActivatedUserByUsername(username)).thenReturn(admin);
        when(jwtTokenUtil.generateToken(any(UserDetails.class))).thenReturn("sampleToken");

        Object userLoginResDto = userService.processLogin(username, password);

        if (userLoginResDto instanceof UserLoginResDto) {
            assertEquals(((UserLoginResDto) userLoginResDto).getFirstName(), admin.getFirstName());
            assertEquals(((UserLoginResDto) userLoginResDto).getLastName(), admin.getLastName());
            assertEquals(((UserLoginResDto) userLoginResDto).getRoleName(), admin.getRole().getRoleName());
            assertEquals(((UserLoginResDto) userLoginResDto).getToken(), "sampleToken");
            assertEquals(((UserLoginResDto) userLoginResDto).isFirstTimeLogin(), admin.isFirstTimeLogin());
            verify(userRepository, times(1)).findActivatedUserByUsername(admin.getUsername());
            try {
                verify(emailService, times(0)).sendSimpleMessage(any(), any(), any());
            } catch (MessagingException e) {
                fail("Cannot send email!!");
            }
            verify(userRepository, times(2)).save(any(UserEntity.class));
            verify(jwtTokenUtil, times(1)).generateToken(any(UserDetails.class));
        } else {
            fail("Must return error code!!");
        }
    }

    @Test // Do not use org.junit.jupiter.api
    public void processLogin_userDoesNotExist() {
        // Value from client
        String username = "admin1";
        String password = "12345678";

        when(userRepository.findActivatedUserByUsername(username)).thenReturn(null);

        Object userLoginResDto = userService.processLogin(username, password);

        if (userLoginResDto instanceof String) {
            verify(userRepository, times(1)).findActivatedUserByUsername(username);
            try {
                verify(emailService, times(0)).sendSimpleMessage(any(), any(), any());
            } catch (MessagingException e) {
                fail("Cannot send email!!");
            }
            verify(userRepository, times(0)).save(any());
            verify(jwtTokenUtil, times(0)).generateToken(any());
            assertEquals(userLoginResDto, ResponseCode.UNAUTHORIZED);
        } else {
            fail("Must return error code!!");
        }
    }

    @Test // Do not use org.junit.jupiter.api
    public void processLogin_userIsLocked() throws MessagingException {
        // Value from client
        String username = "admin1";
        String password = "12345678";

        UserEntityDataDummy userEntityDataDummy = new UserEntityDataDummy();
        UserEntity admin = userEntityDataDummy.getAdminUser1();
        admin.setLoginFailCount(5);
        when(userRepository.findActivatedUserByUsername(username)).thenReturn(admin);

        Object userLoginResDto = userService.processLogin(username, password);

        if (userLoginResDto instanceof String) {
            verify(userRepository, times(1)).findActivatedUserByUsername(username);
            verify(emailService, times(0)).sendSimpleMessage(any(), any(), any());
            verify(userRepository, times(0)).save(any());
            verify(jwtTokenUtil, times(0)).generateToken(any());
            assertEquals(userLoginResDto, ResponseCode.USER_IS_LOCKED);
        } else {
            fail("Must return error code!!");
        }
    }

    @Test // Do not use org.junit.jupiter.api
    public void processLogin_userIsLocked_thenSendMail() throws MessagingException {
        // Value from client
        String username = "admin1";
        String password = "123456789";

        UserEntityDataDummy userEntityDataDummy = new UserEntityDataDummy();
        UserEntity admin = userEntityDataDummy.getAdminUser1();
        admin.setLoginFailCount(4);
        when(userRepository.findActivatedUserByUsername(username)).thenReturn(admin);
        // Mock void method
        doNothing().when(emailService).sendSimpleMessage(anyString(), anyString(), anyString());

        Object userLoginResDto = userService.processLogin(username, password);

        if (userLoginResDto instanceof String) {
            verify(userRepository, times(1)).findActivatedUserByUsername(username);
            verify(emailService, times(1)).sendSimpleMessage(anyString(), anyString(), anyString());
            verify(userRepository, times(1)).save(any(UserEntity.class));
            verify(jwtTokenUtil, times(0)).generateToken(any());
            assertEquals(userLoginResDto, ResponseCode.UNAUTHORIZED);
        } else {
            fail("Must return error code!!");
        }
    }

    @Test // Do not use org.junit.jupiter.api
    public void processLogin_passwordIsNotMatched() {
        // Value from client
        String username = "admin1";
        String password = "123456789";

        UserEntityDataDummy userEntityDataDummy = new UserEntityDataDummy();
        UserEntity admin = userEntityDataDummy.getAdminUser1();
        when(userRepository.findActivatedUserByUsername(username)).thenReturn(admin);

        Object userLoginResDto = userService.processLogin(username, password);

        if (userLoginResDto instanceof String) {
            verify(userRepository, times(1)).findActivatedUserByUsername(username);
            try {
                verify(emailService, times(0)).sendSimpleMessage(any(), any(), any());
            } catch (MessagingException e) {
                fail("Cannot send email!!");
            }
            verify(userRepository, times(1)).save(any(UserEntity.class));
            verify(jwtTokenUtil, times(0)).generateToken(any());
            assertEquals(userLoginResDto, ResponseCode.UNAUTHORIZED);
        } else {
            fail("Must return error code!!");
        }
    }

    // ---------- processLogin END ---------

    // ---------- getUserDetails START ---------

    @Test
    public void getUserDetails_success() {
        // Value from client
        String username = "admin1";

        UserEntityDataDummy userEntityDataDummy = new UserEntityDataDummy();
        UserEntity admin = userEntityDataDummy.getAdminUser1();
        when(userRepository.findActivatedUserByUsername(username)).thenReturn(admin);

        UserDetails userDetails = userService.getUserDetails(username);

        verify(userRepository, times(1)).findActivatedUserByUsername(username);
        assertEquals(userDetails.getUsername(), username);
    }

    @Test
    public void getUserDetails_userDoesNotExist() {
        // Value from client
        String username = "admin1";

        when(userRepository.findActivatedUserByUsername(username)).thenReturn(null);

        UserDetails userDetails = userService.getUserDetails(username);

        verify(userRepository, times(1)).findActivatedUserByUsername(username);
        assertNull(userDetails);
    }
    // ---------- getUserDetails END ---------

    // ---------- getUserByUsername START ---------

    @Test
    public void getUserByUsername_success() {
        // Value from client
        String username = "admin1";

        UserEntityDataDummy userEntityDataDummy = new UserEntityDataDummy();
        UserEntity admin = userEntityDataDummy.getAdminUser1();
        when(userRepository.findActivatedUserByUsername(username)).thenReturn(admin);

        UserEntity userEntity = userService.findActivatedUserByUsername(username);

        verify(userRepository, times(1)).findActivatedUserByUsername(username);
        assertEquals(userEntity.getUsername(), username);
    }

    @Test
    public void getUserByUsername_userDoesNotExist() {
        // Value from client
        String username = "admin1";

        when(userRepository.findActivatedUserByUsername(username)).thenReturn(null);

        UserEntity userEntity = userService.findActivatedUserByUsername(username);

        verify(userRepository, times(1)).findActivatedUserByUsername(username);
        assertNull(userEntity);
    }

    // ---------- getUserByUsername END ---------

    // ---------- processForgotPassword START ---------

    @Test
    public void processForgotPassword_success() throws MessagingException {
        // Value from client
        String username = "admin1";

        UserEntityDataDummy userEntityDataDummy = new UserEntityDataDummy();
        UserEntity admin = userEntityDataDummy.getAdminUser1();
        when(userRepository.findActivatedUserByUsername(username)).thenReturn(admin);
        when(forgotPasswordTokenRepository.save(any(ForgotPasswordTokenEntity.class))).thenReturn(null);

        // Mock void method
        doNothing().when(forgotPasswordTokenRepository).deleteTokenByUsername(username);
        doNothing().when(emailService).sendSimpleMessage(anyString(), anyString(), anyString());

        boolean result = userService.processForgotPassword(username);

        verify(userRepository, times(1)).findActivatedUserByUsername(username);
        verify(forgotPasswordTokenRepository, times(1)).deleteTokenByUsername(username);
        verify(forgotPasswordTokenRepository, times(1)).save(any(ForgotPasswordTokenEntity.class));
        verify(emailService, times(1)).sendSimpleMessage(anyString(), anyString(), anyString());
        assertTrue(result);
    }

    @Test
    public void processForgotPassword_userDoesNotExist() throws MessagingException {
        // Value from client
        String username = "admin1";

        when(userRepository.findActivatedUserByUsername(username)).thenReturn(null);

        boolean result = userService.processForgotPassword(username);

        verify(userRepository, times(1)).findActivatedUserByUsername(username);
        verify(forgotPasswordTokenRepository, times(0)).deleteTokenByUsername(any());
        verify(forgotPasswordTokenRepository, times(0)).save(any());
        verify(emailService, times(0)).sendSimpleMessage(any(), any(), any());
        assertFalse(result);
    }

    // ---------- processForgotPassword END ---------

    // ---------- processConfirmForgotPasswordToken START ---------

    @Test
    public void processConfirmForgotPasswordToken_success() {
        // Value from client
        String token = "Token1";

        ForgotPasswordTokenEntityDataDummy forgotPasswordTokenEntityDataDummy = new ForgotPasswordTokenEntityDataDummy();
        ForgotPasswordTokenEntity forgotPasswordTokenEntity = forgotPasswordTokenEntityDataDummy
                .getForgotPasswordTokenEntity();
        forgotPasswordTokenEntity.setTokenExpiredDate(LocalDateTime.now().plusHours(1));

        when(forgotPasswordTokenRepository.findByTokenString(token)).thenReturn(forgotPasswordTokenEntity);

        boolean result = userService.processConfirmForgotPasswordToken(token);

        verify(forgotPasswordTokenRepository, times(1)).findByTokenString(token);
        assertTrue(result);
    }

    @Test
    public void processConfirmForgotPasswordToken_tokenIsEmpty() {
        // Value from client
        String token = "Token1";

        when(forgotPasswordTokenRepository.findByTokenString(token)).thenReturn(null);

        boolean result = userService.processConfirmForgotPasswordToken(token);

        verify(forgotPasswordTokenRepository, times(1)).findByTokenString(token);
        assertFalse(result);
    }

    @Test
    public void processConfirmForgotPasswordToken_tokenIsExpired() {
        // Value from client
        String token = "Token1";

        ForgotPasswordTokenEntityDataDummy forgotPasswordTokenEntityDataDummy = new ForgotPasswordTokenEntityDataDummy();
        ForgotPasswordTokenEntity forgotPasswordTokenEntity = forgotPasswordTokenEntityDataDummy
                .getForgotPasswordTokenEntity();
        forgotPasswordTokenEntity.setTokenExpiredDate(LocalDateTime.now().minusHours(1));

        when(forgotPasswordTokenRepository.findByTokenString(token)).thenReturn(forgotPasswordTokenEntity);

        boolean result = userService.processConfirmForgotPasswordToken(token);

        verify(forgotPasswordTokenRepository, times(1)).findByTokenString(token);
        assertFalse(result);
    }

    // ---------- processConfirmForgotPasswordToken END ---------

    // ---------- processRecoverPassword START ---------

    @Test
    public void processRecoverPassword_success() {
        // Value from client
        PasswordRecoverUpdatePasswordReqDto passwordRecoverUpdatePasswordReqDto = new PasswordRecoverUpdatePasswordReqDto();
        passwordRecoverUpdatePasswordReqDto.setToken("Token1");
        passwordRecoverUpdatePasswordReqDto.setNewPassword("newPassword");

        ForgotPasswordTokenEntityDataDummy forgotPasswordTokenEntityDataDummy = new ForgotPasswordTokenEntityDataDummy();
        ForgotPasswordTokenEntity forgotPasswordTokenEntity = forgotPasswordTokenEntityDataDummy
                .getForgotPasswordTokenEntity();
        forgotPasswordTokenEntity.setTokenExpiredDate(LocalDateTime.now().plusHours(1));

        UserEntityDataDummy userEntityDataDummy = new UserEntityDataDummy();
        UserEntity admin = userEntityDataDummy.getAdminUser1();

        when(forgotPasswordTokenRepository.findByTokenString(passwordRecoverUpdatePasswordReqDto.getToken()))
                .thenReturn(forgotPasswordTokenEntity);
        when(userRepository.findActivatedUserByUsername(forgotPasswordTokenEntity.getUsername())).thenReturn(admin);
        when(userRepository.save(admin)).thenReturn(null);

        // Mock void method
        doNothing().when(forgotPasswordTokenRepository).delete(forgotPasswordTokenEntity);

        boolean result = userService.processRecoverPassword(passwordRecoverUpdatePasswordReqDto);

        verify(forgotPasswordTokenRepository, times(1)).findByTokenString(passwordRecoverUpdatePasswordReqDto.getToken());
        verify(userRepository, times(1)).findActivatedUserByUsername(forgotPasswordTokenEntity.getUsername());
        verify(userRepository, times(1)).save(admin);
        verify(forgotPasswordTokenRepository, times(1)).delete(forgotPasswordTokenEntity);
        assertTrue(result);
    }

    @Test
    public void processRecoverPassword_tokenIsEmpty() {
        // Value from client
        PasswordRecoverUpdatePasswordReqDto passwordRecoverUpdatePasswordReqDto = new PasswordRecoverUpdatePasswordReqDto();
        passwordRecoverUpdatePasswordReqDto.setToken("Token1");
        passwordRecoverUpdatePasswordReqDto.setNewPassword("newPassword");

        when(forgotPasswordTokenRepository.findByTokenString(passwordRecoverUpdatePasswordReqDto.getToken())).thenReturn(null);

        boolean result = userService.processRecoverPassword(passwordRecoverUpdatePasswordReqDto);

        verify(forgotPasswordTokenRepository, times(1)).findByTokenString(passwordRecoverUpdatePasswordReqDto.getToken());
        verify(userRepository, times(0)).findById(any());
        verify(userRepository, times(0)).save(any());
        verify(forgotPasswordTokenRepository, times(0)).delete(any());
        assertFalse(result);
    }

    @Test
    public void processRecoverPassword_tokenIsExpired() {
        // Value from client
        PasswordRecoverUpdatePasswordReqDto passwordRecoverUpdatePasswordReqDto = new PasswordRecoverUpdatePasswordReqDto();
        passwordRecoverUpdatePasswordReqDto.setToken("Token1");
        passwordRecoverUpdatePasswordReqDto.setNewPassword("newPassword");

        ForgotPasswordTokenEntityDataDummy forgotPasswordTokenEntityDataDummy = new ForgotPasswordTokenEntityDataDummy();
        ForgotPasswordTokenEntity forgotPasswordTokenEntity = forgotPasswordTokenEntityDataDummy
                .getForgotPasswordTokenEntity();
        forgotPasswordTokenEntity.setTokenExpiredDate(LocalDateTime.now().minusHours(1));

        when(forgotPasswordTokenRepository.findByTokenString(passwordRecoverUpdatePasswordReqDto.getToken()))
                .thenReturn(forgotPasswordTokenEntity);

        boolean result = userService.processRecoverPassword(passwordRecoverUpdatePasswordReqDto);

        verify(forgotPasswordTokenRepository, times(1)).findByTokenString(passwordRecoverUpdatePasswordReqDto.getToken());
        verify(userRepository, times(0)).findById(any());
        verify(userRepository, times(0)).save(any());
        verify(forgotPasswordTokenRepository, times(0)).delete(any());
        assertFalse(result);
    }

    @Test
    public void processRecoverPassword_userDoesNotExist() {
        // Value from client
        PasswordRecoverUpdatePasswordReqDto passwordRecoverUpdatePasswordReqDto = new PasswordRecoverUpdatePasswordReqDto();
        passwordRecoverUpdatePasswordReqDto.setToken("Token1");
        passwordRecoverUpdatePasswordReqDto.setNewPassword("newPassword");

        ForgotPasswordTokenEntityDataDummy forgotPasswordTokenEntityDataDummy = new ForgotPasswordTokenEntityDataDummy();
        ForgotPasswordTokenEntity forgotPasswordTokenEntity = forgotPasswordTokenEntityDataDummy
                .getForgotPasswordTokenEntity();
        forgotPasswordTokenEntity.setTokenExpiredDate(LocalDateTime.now().plusHours(1));

        when(forgotPasswordTokenRepository.findByTokenString(passwordRecoverUpdatePasswordReqDto.getToken()))
                .thenReturn(forgotPasswordTokenEntity);
        when(userRepository.findActivatedUserByUsername(forgotPasswordTokenEntity.getUsername())).thenReturn(null);

        boolean result = userService.processRecoverPassword(passwordRecoverUpdatePasswordReqDto);

        verify(forgotPasswordTokenRepository, times(1)).findByTokenString(passwordRecoverUpdatePasswordReqDto.getToken());
        verify(userRepository, times(1)).findActivatedUserByUsername(forgotPasswordTokenEntity.getUsername());
        verify(userRepository, times(0)).save(any());
        verify(forgotPasswordTokenRepository, times(0)).delete(any());
        assertFalse(result);
    }

    // ---------- processRecoverPassword END ---------

    // ---------- getUserSetting START ---------

    @Test
    public void getUserSetting_success() {
        UserEntityDataDummy userEntityDataDummy = new UserEntityDataDummy();
        UserEntity admin = userEntityDataDummy.getAdminUser1();
        when(userRepository.findActivatedUserByUsername(currentUsername)).thenReturn(admin);

        UserSettingResDto userSettingResDto = userService.getUserSetting();

        verify(userRepository, times(1)).findActivatedUserByUsername(currentUsername);
        assertNotNull(userSettingResDto);
    }

    @Test
    public void getUserSetting_userDoesNotExist() {
        when(userRepository.findActivatedUserByUsername(currentUsername)).thenReturn(null);

        UserSettingResDto userSettingResDto = userService.getUserSetting();

        verify(userRepository, times(1)).findActivatedUserByUsername(currentUsername);
        assertNull(userSettingResDto);
    }

    // ---------- getUserSetting END ---------

    // ---------- updateUserSetting START ---------

    @Test
    public void updateUserSetting_success() {
        // Value from client
        UserSettingUpdateReqDto userSettingUpdateReqDto = new UserSettingUpdateReqDto();
        userSettingUpdateReqDto.setStatusId(UserStatus.BUSY.getStatus());
        userSettingUpdateReqDto.setLanguageId("2");

        UserSettingEntityDataDummy userSettingEntityDataDummy = new UserSettingEntityDataDummy();
        UserSettingEntity userSettingEntity = userSettingEntityDataDummy.getAdminUserSetting1();
        userSettingEntity.setStatusId(UserStatus.BUSY.getStatus());
        userSettingEntity.setLanguageId("2");

        UserEntityDataDummy userEntityDataDummy = new UserEntityDataDummy();
        UserEntity admin = userEntityDataDummy.getAdminUser1();

        when(userRepository.findActivatedUserByUsername(currentUsername)).thenReturn(admin);
        when(userSettingRepository.saveAndFlush(any(UserSettingEntity.class))).thenReturn(userSettingEntity);

        // Mock void method
        doNothing().when(userRepository).refresh(admin);

        UserSettingResDto userSettingResDto = userService.updateUserSetting(userSettingUpdateReqDto);

        verify(userRepository, times(1)).findActivatedUserByUsername(currentUsername);
        verify(userSettingRepository, times(1)).saveAndFlush(any(UserSettingEntity.class));
        verify(userRepository, times(1)).refresh(admin);
        assertEquals(userSettingResDto.getStatusId(), userSettingUpdateReqDto.getStatusId());
        assertEquals(userSettingResDto.getLanguageId(), userSettingUpdateReqDto.getLanguageId());
    }

    @Test
    public void updateUserSetting_userDoesNotExist() {
        // Value from client
        UserSettingUpdateReqDto userSettingReqDto = new UserSettingUpdateReqDto();
        userSettingReqDto.setStatusId(UserStatus.BUSY.getStatus());
        userSettingReqDto.setLanguageId("2");

        UserSettingEntityDataDummy userSettingEntityDataDummy = new UserSettingEntityDataDummy();
        UserSettingEntity userSettingEntity = userSettingEntityDataDummy.getAdminUserSetting1();
        userSettingEntity.setStatusId(UserStatus.BUSY.getStatus());
        userSettingEntity.setLanguageId("2");

        when(userRepository.findActivatedUserByUsername(currentUsername)).thenReturn(null);

        UserSettingResDto userSettingResDto = userService.updateUserSetting(userSettingReqDto);

        verify(userRepository, times(1)).findActivatedUserByUsername(currentUsername);
        verify(userSettingRepository, times(0)).saveAndFlush(any(UserSettingEntity.class));
        verify(userRepository, times(0)).refresh(any(UserEntity.class));
        assertNull(userSettingResDto);
    }

    // ---------- updateUserSetting END ---------

    // ---------- updatePassword START ---------

    @Test
    public void updatePassword_success() {
        // Value from client
        UserSettingUpdatePasswordReqDto userSettingUpdatePasswordReqDto = new UserSettingUpdatePasswordReqDto();
        userSettingUpdatePasswordReqDto.setOldPassword("12345678");
        userSettingUpdatePasswordReqDto.setNewPassword("123456789");

        UserEntityDataDummy userEntityDataDummy = new UserEntityDataDummy();
        UserEntity admin = userEntityDataDummy.getAdminUser1();

        when(userRepository.findActivatedUserByUsername(currentUsername)).thenReturn(admin);
        when(userRepository.save(admin)).thenReturn(admin);

        boolean result = userService.updatePassword(userSettingUpdatePasswordReqDto);

        verify(userRepository, times(1)).findActivatedUserByUsername(currentUsername);
        verify(userRepository, times(1)).save(admin);
        assertTrue(result);
    }

    @Test
    public void updatePassword_userDoesNotExist() {
        // Value from client
        UserSettingUpdatePasswordReqDto userSettingUpdatePasswordReqDto = new UserSettingUpdatePasswordReqDto();
        userSettingUpdatePasswordReqDto.setOldPassword("12345678");
        userSettingUpdatePasswordReqDto.setNewPassword("123456789");

        when(userRepository.findActivatedUserByUsername(currentUsername)).thenReturn(null);

        boolean result = userService.updatePassword(userSettingUpdatePasswordReqDto);

        verify(userRepository, times(1)).findActivatedUserByUsername(currentUsername);
        verify(userRepository, times(0)).save(any(UserEntity.class));
        assertFalse(result);
    }

    @Test
    public void updatePassword_passwordDoesNotMatched() {
        // Value from client
        UserSettingUpdatePasswordReqDto userSettingUpdatePasswordReqDto = new UserSettingUpdatePasswordReqDto();
        userSettingUpdatePasswordReqDto.setOldPassword("123456789");
        userSettingUpdatePasswordReqDto.setNewPassword("123456789");

        UserEntityDataDummy userEntityDataDummy = new UserEntityDataDummy();
        UserEntity admin = userEntityDataDummy.getAdminUser1();

        when(userRepository.findActivatedUserByUsername(currentUsername)).thenReturn(admin);

        boolean result = userService.updatePassword(userSettingUpdatePasswordReqDto);

        verify(userRepository, times(1)).findActivatedUserByUsername(currentUsername);
        verify(userRepository, times(0)).save(any(UserEntity.class));
        assertFalse(result);
    }

    // ---------- updatePassword END ---------

    // ---------- uploadAvatar START ---------

    @Test
    public void uploadAvatar_success() {
        // Value from client
        MockMultipartFile image = new MockMultipartFile("file", "cv.jpg", "image/jpeg",
                "{\"image\": \"F:\\TVJ\\file_upload\\test\\cv.jpg\"}".getBytes());

        String dateTimePatternSticky = "yyyyMMddhhmmss";

        String fileName = StringUtils.cleanPath(Objects.requireNonNull(image.getOriginalFilename()));
        String newFileName = currentUsername + "_"
                + LocalDateTime.now().format(DateTimeFormatter.ofPattern(dateTimePatternSticky)) + "_" + fileName;

        UserEntityDataDummy userEntityDataDummy = new UserEntityDataDummy();
        UserEntity admin = userEntityDataDummy.getAdminUser1();

        UserSettingEntityDataDummy userSettingEntityDataDummy = new UserSettingEntityDataDummy();
        UserSettingEntity userSettingEntity = userSettingEntityDataDummy.getAdminUserSetting1();
        userSettingEntity.setAvatar(newFileName);
        userSettingEntity.setUpdatedBy(currentUsername);
        userSettingEntity.setUpdatedDate(LocalDateTime.now());

        when(userRepository.findActivatedUserByUsername(currentUsername)).thenReturn(admin);
        when(fileStorageService.storeFile(image)).thenReturn(newFileName);
        when(userSettingRepository.save(any(UserSettingEntity.class))).thenReturn(userSettingEntity);

        String outputFileName = userService.uploadAvatar(image);

        verify(userRepository, times(1)).findActivatedUserByUsername(currentUsername);
        verify(fileStorageService, times(1)).storeFile(image);
        verify(userSettingRepository, times(1)).save(any(UserSettingEntity.class));
        assertEquals(outputFileName, newFileName);
    }

    @Test
    public void uploadAvatar_invalidFileType() {
        // Value from client
        MockMultipartFile image = new MockMultipartFile("file", "cv.gif", "image/gif",
                "{\"image\": \"F:\\TVJ\\file_upload\\test\\cv.gif\"}".getBytes());

        String outputFileName = userService.uploadAvatar(image);

        verify(userRepository, times(0)).findActivatedUserByUsername(anyString());
        verify(fileStorageService, times(0)).storeFile(any(MultipartFile.class));
        verify(userSettingRepository, times(0)).save(any(UserSettingEntity.class));
        assertNull(outputFileName);
    }

    @Test
    public void uploadAvatar_userDoesNotExist() {
        // Value from client
        MockMultipartFile image = new MockMultipartFile("file", "cv.jpg", "image/jpeg",
                "{\"image\": \"F:\\TVJ\\file_upload\\test\\cv.jpg\"}".getBytes());

        when(userRepository.findActivatedUserByUsername(currentUsername)).thenReturn(null);

        String outputFileName = userService.uploadAvatar(image);

        verify(userRepository, times(1)).findActivatedUserByUsername(anyString());
        verify(fileStorageService, times(0)).storeFile(any(MultipartFile.class));
        verify(userSettingRepository, times(0)).save(any(UserSettingEntity.class));
        assertNull(outputFileName);
    }

    // ---------- uploadAvatar END ---------

    // ---------- removeAvatar START ---------

    @Test
    public void removeAvatar_success() {
        UserEntityDataDummy userEntityDataDummy = new UserEntityDataDummy();
        UserEntity admin = userEntityDataDummy.getAdminUser1();

        when(userRepository.findActivatedUserByUsername(currentUsername)).thenReturn(admin);

        boolean result = userService.removeAvatar();

        verify(userRepository, times(1)).findActivatedUserByUsername(currentUsername);
        assertTrue(result);
    }

    @Test
    public void removeAvatar_userDoesNotExist() {
        when(userRepository.findActivatedUserByUsername(currentUsername)).thenReturn(null);

        boolean result = userService.removeAvatar();

        verify(userRepository, times(1)).findActivatedUserByUsername(currentUsername);
        assertFalse(result);
    }

    // ---------- removeAvatar END ---------

    // ---------- saveLastLogout START ---------

    @Test
    public void saveLastLogout_success() {
        UserEntityDataDummy userEntityDataDummy = new UserEntityDataDummy();
        UserEntity admin = userEntityDataDummy.getAdminUser1();

        when(userRepository.findActivatedUserByUsername(currentUsername)).thenReturn(admin);

        boolean result = userService.saveLastLogout();

        verify(userRepository, times(1)).findActivatedUserByUsername(currentUsername);
        assertTrue(result);
    }

    @Test
    public void saveLastLogout_userDoesNotExist() {
        when(userRepository.findActivatedUserByUsername(currentUsername)).thenReturn(null);

        boolean result = userService.saveLastLogout();

        verify(userRepository, times(1)).findActivatedUserByUsername(currentUsername);
        assertFalse(result);
    }

    // ---------- saveLastLogout END ---------

}
