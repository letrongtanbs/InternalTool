package com.tvj.internaltool.service.impl;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.tvj.internaltool.dto.res.UserStatusListResDto;
import com.tvj.internaltool.dummy.entity.UserStatusEntityDataDummy;
import com.tvj.internaltool.entity.UserStatusEntity;
import com.tvj.internaltool.repository.UserStatusRepository;

@RunWith(MockitoJUnitRunner.class)
public class UserStatusServiceImplTest {

    @InjectMocks
    private UserStatusServiceImpl userStatusService; // cannot InjectMocks interface

    @Mock
    private UserStatusRepository userStatusRepository;

    // ---------- getAllUserStatus START ---------

    @Test
    public void getAllUserStatus_success_returnZeroRecord() {
        when(userStatusRepository.findAll()).thenReturn(Collections.emptyList());

        UserStatusListResDto userStatusListResDto = userStatusService.getAllUserStatus();

        verify(userStatusRepository, times(1)).findAll();
        assertEquals(userStatusListResDto.getUserStatusResDtoList().size(), 0);
    }

    @Test
    public void getAllUserStatus_success_returnOneRecord() {
        UserStatusEntityDataDummy userStatusEntityDataDummy  = new UserStatusEntityDataDummy();
        List<UserStatusEntity> userStatusEntityList = Collections.singletonList(
                userStatusEntityDataDummy.getAvailable());

        when(userStatusRepository.findAll()).thenReturn(userStatusEntityList);

        UserStatusListResDto userStatusListResDto = userStatusService.getAllUserStatus();

        verify(userStatusRepository, times(1)).findAll();
        assertEquals(userStatusListResDto.getUserStatusResDtoList().size(), 1);
    }

    @Test
    public void getAllUserStatus_success_returnTwoRecords() {
        UserStatusEntityDataDummy userStatusEntityDataDummy  = new UserStatusEntityDataDummy();
        List<UserStatusEntity> userStatusEntityList = Arrays.asList(
                userStatusEntityDataDummy.getAvailable(),
                userStatusEntityDataDummy.getAway());

        when(userStatusRepository.findAll()).thenReturn(userStatusEntityList);

        UserStatusListResDto userStatusListResDto = userStatusService.getAllUserStatus();

        verify(userStatusRepository, times(1)).findAll();
        assertEquals(userStatusListResDto.getUserStatusResDtoList().size(), 2);
    }

    // ---------- getAllUserStatus END ---------

}
