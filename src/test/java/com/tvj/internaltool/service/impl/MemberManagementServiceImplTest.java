package com.tvj.internaltool.service.impl;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.tvj.internaltool.dto.req.MemberSearchReqDto;
import com.tvj.internaltool.dto.res.MemberListResDto;
import com.tvj.internaltool.dummy.entity.UserEntityDataDummy;
import com.tvj.internaltool.entity.UserEntity;
import com.tvj.internaltool.repository.UserRepository;

@RunWith(MockitoJUnitRunner.class)
public class MemberManagementServiceImplTest {

    @InjectMocks
    private MemberManagementServiceImpl memberManagementService; // cannot InjectMocks interface

    @Mock
    private UserRepository userRepository;

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
        List<UserEntity> userEntityList = Arrays.asList(
                userEntityDataDummy.getAdminUser1(),
                userEntityDataDummy.getAdminUser2());
        
        when(userRepository.searchMember(any(MemberSearchReqDto.class))).thenReturn(userEntityList);

        MemberListResDto memberListResDto = memberManagementService.searchMember(memberSearchReqDto);

        verify(userRepository, times(1)).searchMember(any(MemberSearchReqDto.class));
        assertEquals(memberListResDto.getMemberResDtoList().size(), 2);
    }

    // ---------- searchMember END ---------

}
