package com.tvj.internaltool.service.impl;

import com.tvj.internaltool.dto.req.RecoverPasswordReqDto;
import com.tvj.internaltool.dto.res.UserLoginResDto;
import com.tvj.internaltool.dummy.entity.ForgotPasswordTokenEntityDataDummy;
import com.tvj.internaltool.dummy.entity.UserEntityDataDummy;
import com.tvj.internaltool.entity.ForgotPasswordTokenEntity;
import com.tvj.internaltool.entity.UserEntity;
import com.tvj.internaltool.repository.ForgotPasswordTokenRepository;
import com.tvj.internaltool.repository.UserRepository;
import com.tvj.internaltool.security.JwtTokenUtil;
import com.tvj.internaltool.utils.EnvironmentUtils;
import com.tvj.internaltool.utils.ResponseCode;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.util.ReflectionTestUtils;

import javax.mail.MessagingException;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceImplTest {

    @InjectMocks
    private UserServiceImpl userService; // cannot InjectMocks interface

    @Mock
    private EmailServiceImpl emailService; // this will be injected into userService (use for when(...))

    @Mock
    private UserRepository userRepository; // this will be injected into userService (use for when(...))

    @Mock
    private ForgotPasswordTokenRepository forgotPasswordTokenRepository; // this will be injected into userService (use for when(...))

    @Mock
    private JwtTokenUtil jwtTokenUtil; // this will be injected into userService (use for when(...))

    @Mock
    private EnvironmentUtils environmentUtils; // this will be injected into userService (use for when(...))

    @Before
    public void setUp() {
        // Initialize value for @Value parameter
        ReflectionTestUtils.setField(userService, "forgotPasswordMaxLoginFailedCount", 5);
        // Override value
        ReflectionTestUtils.setField(userService, "passwordEncoder", new BCryptPasswordEncoder());
        ReflectionTestUtils.setField(userService, "accountIsLockedMailSubject", "Mail Subject 1");
        ReflectionTestUtils.setField(userService, "accountIsLockedMailTemplate", "Mail Template 1");
        ReflectionTestUtils.setField(userService, "forgotPasswordMailSubject", "Mail Subject 2");
        ReflectionTestUtils.setField(userService, "forgotPasswordMailTemplate", "Mail Template 3");
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
            verify(userRepository, times(0)).save(any());
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
            verify(userRepository, times(1)).save(any(UserEntity.class));
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

        UserEntity userEntity = userService.getUserByUsername(username);

        verify(userRepository, times(1)).findActivatedUserByUsername(username);
        assertEquals(userEntity.getUsername(), username);
    }

    @Test
    public void getUserByUsername_userDoesNotExist() {
        // Value from client
        String username = "admin1";

        when(userRepository.findActivatedUserByUsername(username)).thenReturn(null);

        UserEntity userEntity = userService.getUserByUsername(username);

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
        doNothing().when(forgotPasswordTokenRepository).deleteTokenByUserId(admin.getUserId());
        doNothing().when(emailService).sendSimpleMessage(anyString(), anyString(), anyString());

        boolean result = userService.processForgotPassword(username);

        verify(userRepository, times(1)).findActivatedUserByUsername(username);
        verify(forgotPasswordTokenRepository, times(1)).deleteTokenByUserId(admin.getUserId());
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
        verify(forgotPasswordTokenRepository, times(0)).deleteTokenByUserId(any());
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
        ForgotPasswordTokenEntity forgotPasswordTokenEntity = forgotPasswordTokenEntityDataDummy.getForgotPasswordTokenEntity();
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
        ForgotPasswordTokenEntity forgotPasswordTokenEntity = forgotPasswordTokenEntityDataDummy.getForgotPasswordTokenEntity();
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
        RecoverPasswordReqDto recoverPasswordReqDto = new RecoverPasswordReqDto();
        recoverPasswordReqDto.setToken("Token1");
        recoverPasswordReqDto.setNewPassword("newPassword");

        ForgotPasswordTokenEntityDataDummy forgotPasswordTokenEntityDataDummy = new ForgotPasswordTokenEntityDataDummy();
        ForgotPasswordTokenEntity forgotPasswordTokenEntity = forgotPasswordTokenEntityDataDummy.getForgotPasswordTokenEntity();
        forgotPasswordTokenEntity.setTokenExpiredDate(LocalDateTime.now().plusHours(1));

        UserEntityDataDummy userEntityDataDummy = new UserEntityDataDummy();
        UserEntity admin = userEntityDataDummy.getAdminUser1();

        when(forgotPasswordTokenRepository.findByTokenString(recoverPasswordReqDto.getToken())).thenReturn(forgotPasswordTokenEntity);
        when(userRepository.findById(forgotPasswordTokenEntityDataDummy.getForgotPasswordTokenEntity().getUserId()))
                .thenReturn(Optional.of(admin));
        when(userRepository.save(Optional.of(admin).get())).thenReturn(null);

        // Mock void method
        doNothing().when(forgotPasswordTokenRepository).delete(forgotPasswordTokenEntity);

        boolean result = userService.processRecoverPassword(recoverPasswordReqDto);

        verify(forgotPasswordTokenRepository, times(1)).findByTokenString(recoverPasswordReqDto.getToken());
        verify(userRepository, times(1))
                .findById(forgotPasswordTokenEntityDataDummy.getForgotPasswordTokenEntity().getUserId());
        verify(userRepository, times(1)).save(Optional.of(admin).get());
        verify(forgotPasswordTokenRepository, times(1)).delete(forgotPasswordTokenEntity);

        assertTrue(result);
    }

    @Test
    public void processRecoverPassword_tokenIsEmpty() {
        // Value from client
        RecoverPasswordReqDto recoverPasswordReqDto = new RecoverPasswordReqDto();
        recoverPasswordReqDto.setToken("Token1");
        recoverPasswordReqDto.setNewPassword("newPassword");

        when(forgotPasswordTokenRepository.findByTokenString(recoverPasswordReqDto.getToken())).thenReturn(null);

        boolean result = userService.processRecoverPassword(recoverPasswordReqDto);

        verify(forgotPasswordTokenRepository, times(1)).findByTokenString(recoverPasswordReqDto.getToken());
        verify(userRepository, times(0)).findById(any());
        verify(userRepository, times(0)).save(any());
        verify(forgotPasswordTokenRepository, times(0)).delete(any());

        assertFalse(result);
    }

    @Test
    public void processRecoverPassword_tokenIsExpired() {
        // Value from client
        RecoverPasswordReqDto recoverPasswordReqDto = new RecoverPasswordReqDto();
        recoverPasswordReqDto.setToken("Token1");
        recoverPasswordReqDto.setNewPassword("newPassword");

        ForgotPasswordTokenEntityDataDummy forgotPasswordTokenEntityDataDummy = new ForgotPasswordTokenEntityDataDummy();
        ForgotPasswordTokenEntity forgotPasswordTokenEntity = forgotPasswordTokenEntityDataDummy.getForgotPasswordTokenEntity();
        forgotPasswordTokenEntity.setTokenExpiredDate(LocalDateTime.now().minusHours(1));

        when(forgotPasswordTokenRepository.findByTokenString(recoverPasswordReqDto.getToken())).thenReturn(forgotPasswordTokenEntity);

        boolean result = userService.processRecoverPassword(recoverPasswordReqDto);

        verify(forgotPasswordTokenRepository, times(1)).findByTokenString(recoverPasswordReqDto.getToken());
        verify(userRepository, times(0)).findById(any());
        verify(userRepository, times(0)).save(any());
        verify(forgotPasswordTokenRepository, times(0)).delete(any());

        assertFalse(result);
    }

    @Test
    public void processRecoverPassword_userDoesNotExist() {
        // Value from client
        RecoverPasswordReqDto recoverPasswordReqDto = new RecoverPasswordReqDto();
        recoverPasswordReqDto.setToken("Token1");
        recoverPasswordReqDto.setNewPassword("newPassword");

        ForgotPasswordTokenEntityDataDummy forgotPasswordTokenEntityDataDummy = new ForgotPasswordTokenEntityDataDummy();
        ForgotPasswordTokenEntity forgotPasswordTokenEntity = forgotPasswordTokenEntityDataDummy.getForgotPasswordTokenEntity();
        forgotPasswordTokenEntity.setTokenExpiredDate(LocalDateTime.now().plusHours(1));

        when(forgotPasswordTokenRepository.findByTokenString(recoverPasswordReqDto.getToken())).thenReturn(forgotPasswordTokenEntity);
        when(userRepository.findById(forgotPasswordTokenEntityDataDummy.getForgotPasswordTokenEntity().getUserId()))
                .thenReturn(Optional.empty());

        boolean result = userService.processRecoverPassword(recoverPasswordReqDto);

        verify(forgotPasswordTokenRepository, times(1)).findByTokenString(recoverPasswordReqDto.getToken());
        verify(userRepository, times(1))
                .findById(forgotPasswordTokenEntityDataDummy.getForgotPasswordTokenEntity().getUserId());
        verify(userRepository, times(0)).save(any());
        verify(forgotPasswordTokenRepository, times(0)).delete(any());

        assertFalse(result);
    }

    // ---------- processRecoverPassword END ---------

}
