package com.tvj.internaltool.service.impl;

import com.tvj.internaltool.dto.res.UserLoginResDto;
import com.tvj.internaltool.dummy.UserEntityDataDummy;
import com.tvj.internaltool.entity.UserEntity;
import com.tvj.internaltool.repository.UserRepository;
import com.tvj.internaltool.security.JwtTokenUtil;
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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
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
    private JwtTokenUtil jwtTokenUtil; // this will be injected into userService (use for when(...))

    @Before
    public void setUp() {
        // Initialize value for @Value parameter
        ReflectionTestUtils.setField(userService, "forgotPasswordMaxLoginFailedCount", 5);
        // Override value
        ReflectionTestUtils.setField(userService, "passwordEncoder", new BCryptPasswordEncoder());
    }

    @Test // Do not use org.junit.jupiter.api
    public void processLogin_success() {
        // value from user
        String username = "admin1";
        String password = "12345678";

        UserEntityDataDummy userEntityDataDummy = new UserEntityDataDummy();
        UserEntity admin = userEntityDataDummy.getAdminUser1();
        when(userRepository.findActivatedUserByUsername(username)).thenReturn(admin);
        when(jwtTokenUtil.generateToken(any(UserDetails.class))).thenReturn("sampleToken");

        // input value from user
        Object userLoginResDto = userService.processLogin(username, password);

        if (userLoginResDto instanceof UserLoginResDto) {
            assertEquals(((UserLoginResDto) userLoginResDto).getFirstName(), admin.getFirstName());
            assertEquals(((UserLoginResDto) userLoginResDto).getLastName(), admin.getLastName());
            assertEquals(((UserLoginResDto) userLoginResDto).getRoleName(), admin.getRole().getRoleName());
            assertEquals(((UserLoginResDto) userLoginResDto).getToken(), "sampleToken");
            verify(userRepository, times(1)).findActivatedUserByUsername(admin.getUsername());
            try {
                verify(emailService, times(0)).sendSimpleMessage(anyString(), anyString(), anyString());
            } catch (MessagingException e) {
                fail("Cannot send email!!");
            }
            verify(userRepository, times(0)).save(any(UserEntity.class));
            verify(jwtTokenUtil, times(1)).generateToken(any(UserDetails.class));
        } else {
            fail("Must return error code!!");
        }
    }

    @Test // Do not use org.junit.jupiter.api
    public void processLogin_resetLoginFailedCountAfterLoginSuccess() {
        // value from user
        String username = "admin1";
        String password = "12345678";

        UserEntityDataDummy userEntityDataDummy = new UserEntityDataDummy();
        UserEntity admin = userEntityDataDummy.getAdminUser1();
        admin.setLoginFailCount(1);
        when(userRepository.findActivatedUserByUsername(username)).thenReturn(admin);
        when(jwtTokenUtil.generateToken(any(UserDetails.class))).thenReturn("sampleToken");

        // input value from user
        Object userLoginResDto = userService.processLogin(username, password);

        if (userLoginResDto instanceof UserLoginResDto) {
            assertEquals(((UserLoginResDto) userLoginResDto).getFirstName(), admin.getFirstName());
            assertEquals(((UserLoginResDto) userLoginResDto).getLastName(), admin.getLastName());
            assertEquals(((UserLoginResDto) userLoginResDto).getRoleName(), admin.getRole().getRoleName());
            assertEquals(((UserLoginResDto) userLoginResDto).getToken(), "sampleToken");
            verify(userRepository, times(1)).findActivatedUserByUsername(admin.getUsername());
            try {
                verify(emailService, times(0)).sendSimpleMessage(anyString(), anyString(), anyString());
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
    public void processLogin_userDoesNotExists() {
        // value from user
        String username = "admin1";
        String password = "12345678";

        when(userRepository.findActivatedUserByUsername(username)).thenReturn(null);

        // input value from user
        Object userLoginResDto = userService.processLogin(username, password);

        if (userLoginResDto instanceof String) {
            verify(userRepository, times(1)).findActivatedUserByUsername(username);
            try {
                verify(emailService, times(0)).sendSimpleMessage(anyString(), anyString(), anyString());
            } catch (MessagingException e) {
                fail("Cannot send email!!");
            }
            verify(userRepository, times(0)).save(any(UserEntity.class));
            verify(jwtTokenUtil, times(0)).generateToken(any(UserDetails.class));
            assertEquals(userLoginResDto, ResponseCode.UNAUTHORIZED);
        } else {
            fail("Must return error code!!");
        }
    }

    @Test // Do not use org.junit.jupiter.api
    public void processLogin_userIsLocked() {
        // value from user
        String username = "admin1";
        String password = "12345678";

        UserEntityDataDummy userEntityDataDummy = new UserEntityDataDummy();
        UserEntity admin = userEntityDataDummy.getAdminUser1();
        admin.setLoginFailCount(5);
        when(userRepository.findActivatedUserByUsername(username)).thenReturn(admin);

        // input value from user
        Object userLoginResDto = userService.processLogin(username, password);

        if (userLoginResDto instanceof String) {
            verify(userRepository, times(1)).findActivatedUserByUsername(username);
            try {
                verify(emailService, times(0)).sendSimpleMessage(anyString(), anyString(), anyString());
            } catch (MessagingException e) {
                fail("Cannot send email!!");
            }
            verify(userRepository, times(0)).save(any(UserEntity.class));
            verify(jwtTokenUtil, times(0)).generateToken(any(UserDetails.class));
            assertEquals(userLoginResDto, ResponseCode.USER_IS_LOCKED);
        } else {
            fail("Must return error code!!");
        }
    }

    @Test // Do not use org.junit.jupiter.api
    public void processLogin_passwordIsNotMatched() {
        // value from user
        String username = "admin1";
        String password = "123456789";

        UserEntityDataDummy userEntityDataDummy = new UserEntityDataDummy();
        UserEntity admin = userEntityDataDummy.getAdminUser1();
        when(userRepository.findActivatedUserByUsername(username)).thenReturn(admin);

        // input value from user
        Object userLoginResDto = userService.processLogin(username, password);

        if (userLoginResDto instanceof String) {
            verify(userRepository, times(1)).findActivatedUserByUsername(username);
            try {
                verify(emailService, times(0)).sendSimpleMessage(anyString(), anyString(), anyString());
            } catch (MessagingException e) {
                fail("Cannot send email!!");
            }
            verify(userRepository, times(1)).save(any(UserEntity.class));
            verify(jwtTokenUtil, times(0)).generateToken(any(UserDetails.class));
            assertEquals(userLoginResDto, ResponseCode.UNAUTHORIZED);
        } else {
            fail("Must return error code!!");
        }
    }


}
