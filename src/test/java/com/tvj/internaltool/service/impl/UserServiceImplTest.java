package com.tvj.internaltool.service.impl;

import com.tvj.internaltool.dummy.UserEntityDataDummy;
import com.tvj.internaltool.entity.UserEntity;
import com.tvj.internaltool.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
class UserServiceImplTest {

    @InjectMocks
    private UserServiceImpl userService; // cannot InjectMocks interface

    @Mock
    private UserRepository userRepository; // this will be injected into userService (use for when(...))

    @Test
    void processLogin() {

        UserEntityDataDummy userEntityDataDummy = new UserEntityDataDummy();
        UserEntity admin = userEntityDataDummy.getAdminUser1();

        // Mocking Spring Security Context
        Authentication authentication = mock(Authentication.class);
        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
        when(SecurityContextHolder.getContext().getAuthentication().getName()).thenReturn(admin.getUsername());
//
//        when(userRepository.findActivatedUserByUsername(userEntityDataDummy.getRootUser().getUsername()))
//                .thenReturn(userEntityDataDummy.getRootUser());
//        when(userPrivilegesRepository.findActivatedPrivilegesByRole(userEntityDataDummy.getRootUser().getRole()))
//                .thenReturn(userPrivilegesEntityDataDummy.getRootPrivileges());
//        when(userRepository.findAll())
//                .thenReturn(listUser);
//
//
//        List<UserResDto> userResDtoList = userService.findAllUser(new HttpReqDto("/user/get-all", "GET"));
//
//        assertEquals(userResDtoList.size(), 3);
//        verify(userRepository, times(1)).findActivatedUserByUsername(userEntityDataDummy.getRootUser().getUsername());
//        verify(userPrivilegesRepository, times(1)).findActivatedPrivilegesByRole(userEntityDataDummy.getRootUser().getRole());
//        verify(userRepository, times(1)).findAll();
//
    }
}