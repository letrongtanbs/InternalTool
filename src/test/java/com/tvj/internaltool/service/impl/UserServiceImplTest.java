package com.tvj.internaltool.service.impl;

import com.tvj.internaltool.dto.res.UserLoginResDto;
import com.tvj.internaltool.dummy.UserEntityDataDummy;
import com.tvj.internaltool.entity.UserEntity;
import com.tvj.internaltool.repository.UserRepository;
import com.tvj.internaltool.security.JwtTokenUtil;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.*;


@RunWith(MockitoJUnitRunner.class)
public class UserServiceImplTest { // use public always

    @InjectMocks
    private UserServiceImpl userService; // cannot InjectMocks interface

    @Mock
    private UserRepository userRepository; // this will be injected into userService (use for when(...))

    @Mock
    private PasswordEncoder passwordEncoder; // this will be injected into userService (use for when(...))

    @Mock
    private JwtTokenUtil jwtTokenUtil; // this will be injected into userService (use for when(...))

    @Before
    public void setUp() {
        ReflectionTestUtils.setField(userService, "forgotPasswordMaxLoginFailedCount", 5);
    }

    @Test // Do not use org.junit.jupiter.api
    public void processLogin() { // use public always
        // value from user
        String username = "admin1";
        String password = "12345678";

        UserEntityDataDummy userEntityDataDummy = new UserEntityDataDummy();
        UserEntity admin = userEntityDataDummy.getAdminUser1();
        when(userRepository.findActivatedUserByUsername(username)).thenReturn(admin);
        when(passwordEncoder.matches(password, admin.getPassword())).thenReturn(true);
        when(jwtTokenUtil.generateToken(any(UserDetails.class))).thenReturn("sampleToken");

        // input value from user
        Object userLoginResDto = userService.processLogin(username, password);

        if (userLoginResDto instanceof UserLoginResDto) {
            assertEquals(((UserLoginResDto) userLoginResDto).getFirstName(), admin.getFirstName());
            assertEquals(((UserLoginResDto) userLoginResDto).getLastName(), admin.getLastName());
            assertEquals(((UserLoginResDto) userLoginResDto).getRoleName(), admin.getRole().getRoleName());
            assertEquals(((UserLoginResDto) userLoginResDto).getToken(), "sampleToken");
            verify(userRepository, times(1)).findActivatedUserByUsername(admin.getUsername());
        } else {
            fail("Return error code!!");
        }
    }

}
