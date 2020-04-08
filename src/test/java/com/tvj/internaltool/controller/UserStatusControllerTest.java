package com.tvj.internaltool.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.Collections;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.google.gson.Gson;
import com.tvj.internaltool.dto.res.UserStatusListResDto;
import com.tvj.internaltool.dummy.dto.res.UserStatusResDtoDataDummy;
import com.tvj.internaltool.service.UserStatusService;
import com.tvj.internaltool.utils.CustomRestExceptionHandler;

@RunWith(MockitoJUnitRunner.class)
public class UserStatusControllerTest {

    @InjectMocks
    private UserStatusController userStatusController;

    @Mock
    private UserStatusService userStatusService;

    private MockMvc mockMvc;

    @Before // Execute before each test method
    public void setup() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(userStatusController)
                .setControllerAdvice(new CustomRestExceptionHandler()) // add ControllerAdvice to controller test
                .build();
    }

    // ---------- /user-status/list START ----------

    @Test
    public void getAllUserStatus_success_returnZeroRecord() throws Exception {
        when(userStatusService.getAllUserStatus()).thenReturn(new UserStatusListResDto());

        MvcResult result = mockMvc.perform(get("/user-status/list")).andExpect(status().isOk()).andReturn();

        String jsonString = result.getResponse().getContentAsString();
        UserStatusListResDto userStatusListResDto = new Gson().fromJson(jsonString, UserStatusListResDto.class);

        verify(userStatusService, times(1)).getAllUserStatus();
        assertNull(userStatusListResDto.getUserStatusResDtoList());
    }

    @Test
    public void getAllUserStatus_success_returnOneRecord() throws Exception {
        UserStatusResDtoDataDummy userStatusResDtoDataDummy = new UserStatusResDtoDataDummy();
        UserStatusListResDto userStatusListResDtoDummy = new UserStatusListResDto();
        userStatusListResDtoDummy
                .setUserStatusResDtoList(Collections.singletonList(userStatusResDtoDataDummy.getAvailable()));

        when(userStatusService.getAllUserStatus()).thenReturn(userStatusListResDtoDummy);

        MvcResult result = mockMvc.perform(get("/user-status/list")).andExpect(status().isOk()).andReturn();

        String jsonString = result.getResponse().getContentAsString();
        UserStatusListResDto userStatusListResDto = new Gson().fromJson(jsonString, UserStatusListResDto.class);

        verify(userStatusService, times(1)).getAllUserStatus();
        assertEquals(userStatusListResDto.getUserStatusResDtoList().size(), 1);
    }

    @Test
    public void getAllUserStatus_success_returnTwoRecords() throws Exception {
        UserStatusResDtoDataDummy userStatusResDtoDataDummy = new UserStatusResDtoDataDummy();
        UserStatusListResDto userStatusListResDtoDummy = new UserStatusListResDto();
        userStatusListResDtoDummy.setUserStatusResDtoList(
                Arrays.asList(userStatusResDtoDataDummy.getAvailable(), userStatusResDtoDataDummy.getAway()));

        when(userStatusService.getAllUserStatus()).thenReturn(userStatusListResDtoDummy);

        MvcResult result = mockMvc.perform(get("/user-status/list")).andExpect(status().isOk()).andReturn();

        String jsonString = result.getResponse().getContentAsString();
        UserStatusListResDto userStatusListResDto = new Gson().fromJson(jsonString, UserStatusListResDto.class);

        verify(userStatusService, times(1)).getAllUserStatus();
        assertEquals(userStatusListResDto.getUserStatusResDtoList().size(), 2);
    }

    // ---------- /user-status/list END ----------

}