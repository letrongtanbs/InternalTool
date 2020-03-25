package com.tvj.internaltool.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.mail.MessagingException;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.util.ReflectionTestUtils;

import com.tvj.internaltool.dto.req.MemberAddReqDto;
import com.tvj.internaltool.dto.req.MemberSearchReqDto;
import com.tvj.internaltool.dto.req.MemberUpdateReqDto;
import com.tvj.internaltool.dto.res.MemberListResDto;
import com.tvj.internaltool.dto.res.MemberResDto;
import com.tvj.internaltool.dummy.entity.UserEntityDataDummy;
import com.tvj.internaltool.dummy.entity.UserSettingEntityDataDummy;
import com.tvj.internaltool.entity.UserEntity;
import com.tvj.internaltool.entity.UserSettingEntity;
import com.tvj.internaltool.repository.UserRepository;
import com.tvj.internaltool.repository.UserSettingRepository;

@RunWith(MockitoJUnitRunner.class)
public class MemberManagementServiceImplTest {

    @InjectMocks
    private MemberManagementServiceImpl memberManagementService; // cannot InjectMocks interface

    @Mock
    private EmailServiceImpl emailService; // this will be injected into userService (use for when(...))

    @Mock
    private UserRepository userRepository; // this will be injected into userService (use for when(...))

    @Mock
    private UserSettingRepository userSettingRepository; // this will be injected into userService (use for when(...))

    private final static String currentUsername = "admin1";

    @Before
    public void setUp() {
        // Override value
        ReflectionTestUtils.setField(memberManagementService, "passwordEncoder", new BCryptPasswordEncoder());
        ReflectionTestUtils.setField(memberManagementService, "passwordForNewUserMailSubject", "Mail Subject 1");
        ReflectionTestUtils.setField(memberManagementService, "passwordForNewUserMailTemplate", "Mail Template 1");

        // Mocking Spring Security Context
        Authentication authentication = mock(Authentication.class);
        SecurityContext securityContext = mock(SecurityContext.class);
        SecurityContextHolder.setContext(securityContext);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(SecurityContextHolder.getContext().getAuthentication().getName()).thenReturn(currentUsername);
    }

    // ---------- searchMember START ---------

    @Test
    public void searchMember_success_returnZeroRecord() {
        MemberSearchReqDto memberSearchReqDto = new MemberSearchReqDto();
        memberSearchReqDto.setUsername("ngocdc");

        List<UserEntity> userEntityList = new ArrayList<>();

        when(userRepository.searchMember(any(MemberSearchReqDto.class))).thenReturn(userEntityList);

        MemberListResDto memberListResDto = memberManagementService.searchMember(memberSearchReqDto);

        verify(userRepository, times(1)).searchMember(any(MemberSearchReqDto.class));
        assertEquals(memberListResDto.getMemberResDtoList().size(), 0);
    }

    @Test
    public void searchMember_success_returnOneRecord() {
        MemberSearchReqDto memberSearchReqDto = new MemberSearchReqDto();
        memberSearchReqDto.setUsername("ngocdc");

        UserEntityDataDummy userEntityDataDummy = new UserEntityDataDummy();
        List<UserEntity> userEntityList = Collections.singletonList(userEntityDataDummy.getAdminUser1());

        when(userRepository.searchMember(any(MemberSearchReqDto.class))).thenReturn(userEntityList);

        MemberListResDto memberListResDto = memberManagementService.searchMember(memberSearchReqDto);

        verify(userRepository, times(1)).searchMember(any(MemberSearchReqDto.class));
        assertEquals(memberListResDto.getMemberResDtoList().size(), 1);
    }

    @Test
    public void searchMember_success_returnTwoRecords() {
        MemberSearchReqDto memberSearchReqDto = new MemberSearchReqDto();
        memberSearchReqDto.setUsername("ngocdc");

        UserEntityDataDummy userEntityDataDummy = new UserEntityDataDummy();
        List<UserEntity> userEntityList = Arrays.asList(userEntityDataDummy.getAdminUser1(),
                userEntityDataDummy.getAdminUser2());

        when(userRepository.searchMember(any(MemberSearchReqDto.class))).thenReturn(userEntityList);

        MemberListResDto memberListResDto = memberManagementService.searchMember(memberSearchReqDto);

        verify(userRepository, times(1)).searchMember(any(MemberSearchReqDto.class));
        assertEquals(memberListResDto.getMemberResDtoList().size(), 2);
    }

    // ---------- searchMember END ---------

    // ---------- addMember START ---------

    @Test
    public void addMember_success() throws MessagingException {
        // Value from client
        MemberAddReqDto memberAddReqDto = new MemberAddReqDto();
        memberAddReqDto.setUsername("ngocdc");
        memberAddReqDto.setFirstName("Dinh");
        memberAddReqDto.setLastName("Ngoc");
        memberAddReqDto.setTitleId("1");
        memberAddReqDto.setEmail("ngocdc@tinhvan.com");
        memberAddReqDto.setPassword("12345678");

        UserEntityDataDummy userEntityDataDummy = new UserEntityDataDummy();
        UserSettingEntityDataDummy userSettingEntityDataDummy = new UserSettingEntityDataDummy();

        when(userRepository.findByUsername(memberAddReqDto.getUsername())).thenReturn(null);
        when(userRepository.save(any(UserEntity.class))).thenReturn(userEntityDataDummy.getAdminUser1());
        when(userSettingRepository.save(any(UserSettingEntity.class)))
                .thenReturn(userSettingEntityDataDummy.getAdminUserSetting1());
        // Mock void method
        doNothing().when(emailService).sendSimpleMessage(anyString(), anyString(), anyString());

        boolean result = memberManagementService.addMember(memberAddReqDto);

        verify(userRepository, times(1)).findByUsername(memberAddReqDto.getUsername());
        verify(userRepository, times(1)).save(any(UserEntity.class));
        verify(userSettingRepository, times(1)).save(any(UserSettingEntity.class));
        verify(emailService, times(1)).sendSimpleMessage(any(), any(), any());
        assertTrue(result);
    }

    @Test
    public void addMember_memberExists() throws MessagingException {
        // Value from client
        MemberAddReqDto memberAddReqDto = new MemberAddReqDto();
        memberAddReqDto.setUsername("ngocdc");
        memberAddReqDto.setFirstName("Dinh");
        memberAddReqDto.setLastName("Ngoc");
        memberAddReqDto.setTitleId("1");
        memberAddReqDto.setEmail("ngocdc@tinhvan.com");
        memberAddReqDto.setPassword("12345678");

        UserEntityDataDummy userEntityDataDummy = new UserEntityDataDummy();

        when(userRepository.findByUsername(memberAddReqDto.getUsername()))
                .thenReturn(userEntityDataDummy.getAdminUser1());

        boolean result = memberManagementService.addMember(memberAddReqDto);

        verify(userRepository, times(1)).findByUsername(memberAddReqDto.getUsername());
        verify(userRepository, times(0)).save(any(UserEntity.class));
        verify(userSettingRepository, times(0)).save(any(UserSettingEntity.class));
        verify(emailService, times(0)).sendSimpleMessage(any(), any(), any());
        assertFalse(result);
    }

    // ---------- addMember END ---------

    // ---------- updateMember START ---------

    @Test
    public void updateMember_success() {
        // Value from client
        MemberUpdateReqDto memberUpdateReqDto = new MemberUpdateReqDto();
        memberUpdateReqDto.setUsername("ngocdc");
        memberUpdateReqDto.setFirstName("Dinh");
        memberUpdateReqDto.setLastName("Ngoc");
        memberUpdateReqDto.setTitleId("1");
        memberUpdateReqDto.setEmail("ngocdc@tinhvan.com");

        UserEntityDataDummy userEntityDataDummy = new UserEntityDataDummy();

        when(userRepository.findByUsername(memberUpdateReqDto.getUsername()))
                .thenReturn(userEntityDataDummy.getAdminUser1());
        when(userRepository.save(any(UserEntity.class))).thenReturn(userEntityDataDummy.getAdminUser1());
        // Mock void method

        boolean result = memberManagementService.updateMember(memberUpdateReqDto);

        verify(userRepository, times(1)).findByUsername(memberUpdateReqDto.getUsername());
        verify(userRepository, times(1)).save(any(UserEntity.class));
        assertTrue(result);
    }

    @Test
    public void updateMember_memberDoesNotExist() {
        // Value from client
        MemberUpdateReqDto memberUpdateReqDto = new MemberUpdateReqDto();
        memberUpdateReqDto.setUsername("ngocdc");
        memberUpdateReqDto.setFirstName("Dinh");
        memberUpdateReqDto.setLastName("Ngoc");
        memberUpdateReqDto.setTitleId("1");
        memberUpdateReqDto.setEmail("ngocdc@tinhvan.com");

        when(userRepository.findByUsername(memberUpdateReqDto.getUsername())).thenReturn(null);
        // Mock void method

        boolean result = memberManagementService.updateMember(memberUpdateReqDto);

        verify(userRepository, times(1)).findByUsername(memberUpdateReqDto.getUsername());
        verify(userRepository, times(0)).save(any(UserEntity.class));
        assertFalse(result);
    }

    // ---------- updateMember END ---------

    // ---------- viewMember START ---------

    @Test
    public void viewMember_success() {
        // Value from client
        String username = "ngocdc";

        UserEntityDataDummy userEntityDataDummy = new UserEntityDataDummy();

        when(userRepository.findByUsername(username)).thenReturn(userEntityDataDummy.getAdminUser1());

        MemberResDto memberResDto = memberManagementService.viewMember(username);

        verify(userRepository, times(1)).findByUsername(username);
        assertNotNull(memberResDto);
    }

    @Test
    public void viewMember_userDoesNotExist() {
        // Value from client
        String username = "ngocdc";

        when(userRepository.findByUsername(username)).thenReturn(null);

        MemberResDto memberResDto = memberManagementService.viewMember(username);

        verify(userRepository, times(1)).findByUsername(username);
        assertNull(memberResDto);
    }

    // ---------- viewMember END ---------

}
