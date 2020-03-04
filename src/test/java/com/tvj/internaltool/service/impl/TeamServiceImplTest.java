package com.tvj.internaltool.service.impl;

import com.tvj.internaltool.dto.res.TeamListResDto;
import com.tvj.internaltool.dummy.entity.TeamEntityDataDummy;
import com.tvj.internaltool.entity.TeamEntity;
import com.tvj.internaltool.repository.TeamRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class TeamServiceImplTest {

    @InjectMocks
    private TeamServiceImpl teamService; // cannot InjectMocks interface

    @Mock
    private TeamRepository teamRepository;

    // ---------- getTeamByDepartment START ---------

    @Test
    public void getTeamByDepartment_success_returnZeroRecord() {
        // Value from client
        String departmentId = "1";

        when(teamRepository.findByDepartmentId(departmentId)).thenReturn(Collections.emptyList());

        TeamListResDto teamListResDto = teamService.getTeamByDepartment(departmentId);

        verify(teamRepository, times(1)).findByDepartmentId(departmentId);
        assertEquals(teamListResDto.getTeamResDtoList().size(), 0);
    }

    @Test
    public void getTeamByDepartment_success_returnOneRecord() {
        // Value from client
        String departmentId = "1";

        TeamEntityDataDummy teamEntityDataDummy = new TeamEntityDataDummy();
        List<TeamEntity> departmentEntityList = Collections.singletonList(
                teamEntityDataDummy.getTeam1());

        when(teamRepository.findByDepartmentId(departmentId)).thenReturn(departmentEntityList);

        TeamListResDto teamListResDto = teamService.getTeamByDepartment(departmentId);

        verify(teamRepository, times(1)).findByDepartmentId(departmentId);
        assertEquals(teamListResDto.getTeamResDtoList().size(), 1);
    }

    @Test
    public void getTeamByDepartment_success_returnTwoRecords() {
        // Value from client
        String departmentId = "1";

        TeamEntityDataDummy teamEntityDataDummy = new TeamEntityDataDummy();
        List<TeamEntity> departmentEntityList = Arrays.asList(
                teamEntityDataDummy.getTeam1(),
                teamEntityDataDummy.getTeam2());

        when(teamRepository.findByDepartmentId(departmentId)).thenReturn(departmentEntityList);

        TeamListResDto teamListResDto = teamService.getTeamByDepartment(departmentId);

        verify(teamRepository, times(1)).findByDepartmentId(departmentId);
        assertEquals(teamListResDto.getTeamResDtoList().size(), 2);
    }
    // ---------- getTeamByDepartment END ---------

}
