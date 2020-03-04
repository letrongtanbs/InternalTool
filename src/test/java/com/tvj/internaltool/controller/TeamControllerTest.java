package com.tvj.internaltool.controller;

import com.google.gson.Gson;
import com.tvj.internaltool.dto.res.TeamListResDto;
import com.tvj.internaltool.dummy.dto.res.TeamResDtoDataDummy;
import com.tvj.internaltool.service.TeamService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.Collections;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(MockitoJUnitRunner.class)
public class TeamControllerTest {

    @InjectMocks
    private TeamController teamController;

    @Mock
    private TeamService teamService;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(teamController).build();
    }

    // ---------- /team/list-by-department START ----------

    @Test
    public void getTeamByDepartment_success_returnZeroRecord() throws Exception {
        String departmentId = "1";

        when(teamService.getTeamByDepartment(departmentId)).thenReturn(new TeamListResDto());

        MvcResult result = mockMvc.perform(get("/team/list-by-department")
                .param("departmentId", departmentId))
                .andExpect(status().isOk())
                .andReturn();

        String jsonString = result.getResponse().getContentAsString();
        TeamListResDto teamListResDto = new Gson().fromJson(jsonString, TeamListResDto.class);

        verify(teamService, times(1)).getTeamByDepartment(departmentId);
        assertNull(teamListResDto.getTeamResDtoList());
    }

    @Test
    public void getTeamByDepartment_success_returnOneRecord() throws Exception {
        String departmentId = "1";

        TeamResDtoDataDummy teamResDtoDataDummy = new TeamResDtoDataDummy();
        TeamListResDto teamListResDtoDummy = new TeamListResDto();
        teamListResDtoDummy.setTeamResDtoList(Collections.singletonList(
                teamResDtoDataDummy.getTeam1()));

        when(teamService.getTeamByDepartment(departmentId)).thenReturn(teamListResDtoDummy);

        MvcResult result = mockMvc.perform(get("/team/list-by-department")
                .param("departmentId", departmentId))
                .andExpect(status().isOk())
                .andReturn();

        String jsonString = result.getResponse().getContentAsString();
        TeamListResDto teamListResDto = new Gson().fromJson(jsonString, TeamListResDto.class);

        verify(teamService, times(1)).getTeamByDepartment(departmentId);
        assertEquals(teamListResDto.getTeamResDtoList().size(), 1);
    }

    @Test
    public void getTeamByDepartment_success_returnTwoRecord() throws Exception {
        String departmentId = "1";

        TeamResDtoDataDummy teamResDtoDataDummy = new TeamResDtoDataDummy();
        TeamListResDto teamListResDtoDummy = new TeamListResDto();
        teamListResDtoDummy.setTeamResDtoList(Arrays.asList(
                teamResDtoDataDummy.getTeam1(),
                teamResDtoDataDummy.getTeam2()));

        when(teamService.getTeamByDepartment(departmentId)).thenReturn(teamListResDtoDummy);

        MvcResult result = mockMvc.perform(get("/team/list-by-department")
                .param("departmentId", departmentId))
                .andExpect(status().isOk())
                .andReturn();

        String jsonString = result.getResponse().getContentAsString();
        TeamListResDto teamListResDto = new Gson().fromJson(jsonString, TeamListResDto.class);

        verify(teamService, times(1)).getTeamByDepartment(departmentId);
        assertEquals(teamListResDto.getTeamResDtoList().size(), 2);
    }

    // ---------- /team/list-by-department END ----------

}
