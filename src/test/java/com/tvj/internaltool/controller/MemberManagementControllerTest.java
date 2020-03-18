package com.tvj.internaltool.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
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
import com.tvj.internaltool.dto.req.MemberSearchReqDto;
import com.tvj.internaltool.dto.res.MemberListResDto;
import com.tvj.internaltool.dummy.dto.res.MemberResDtoDataDummy;
import com.tvj.internaltool.service.MemberManagementService;

@RunWith(MockitoJUnitRunner.class)
public class MemberManagementControllerTest {

    @InjectMocks
    private MemberManagementController memberManagementController;

    @Mock
    private MemberManagementService memberManagementService;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(memberManagementController).build();
    }

    // ---------- /member-management/search START ----------

    @Test
    public void searchMember_success_returnZeroRecord() throws Exception {
        MemberSearchReqDto memberSearchReqDto = new MemberSearchReqDto();
        memberSearchReqDto.setUsername("ngocdc");

        MemberListResDto memberListResDtoDummy = new MemberListResDto();
        memberListResDtoDummy.setMemberResDtoList(new ArrayList<>());

        when(memberManagementService.searchMember(any(MemberSearchReqDto.class))).thenReturn(memberListResDtoDummy);

        MvcResult result = mockMvc.perform(get("/member-management/search")
                .param("username", memberSearchReqDto.getUsername()))
                .andExpect(status().isOk())
                .andReturn();

        String jsonString = result.getResponse().getContentAsString();
        MemberListResDto memberListResDto = new Gson().fromJson(jsonString, MemberListResDto.class);

        verify(memberManagementService, times(1)).searchMember(any(MemberSearchReqDto.class));
        assertEquals(memberListResDto.getMemberResDtoList().size(), 0);
    }
    
    @Test
    public void searchMember_success_returnOneRecord() throws Exception {
        MemberSearchReqDto memberSearchReqDto = new MemberSearchReqDto();
        memberSearchReqDto.setUsername("admin");

        MemberListResDto memberListResDtoDummy = new MemberListResDto();
        MemberResDtoDataDummy memberResDtoDataDummy = new MemberResDtoDataDummy();
        memberListResDtoDummy.setMemberResDtoList(Collections.singletonList(memberResDtoDataDummy.getMember1()));

        when(memberManagementService.searchMember(any(MemberSearchReqDto.class))).thenReturn(memberListResDtoDummy);

        MvcResult result = mockMvc.perform(get("/member-management/search")
                .param("username", memberSearchReqDto.getUsername()))
                .andExpect(status().isOk())
                .andReturn();

        String jsonString = result.getResponse().getContentAsString();
        MemberListResDto memberListResDto = new Gson().fromJson(jsonString, MemberListResDto.class);

        verify(memberManagementService, times(1)).searchMember(any(MemberSearchReqDto.class));
        assertEquals(memberListResDto.getMemberResDtoList().size(), 1);
    }
    
    @Test
    public void searchMember_success_returnTwoRecords() throws Exception {
        MemberSearchReqDto memberSearchReqDto = new MemberSearchReqDto();
        memberSearchReqDto.setUsername("admin");

        MemberListResDto memberListResDtoDummy = new MemberListResDto();
        MemberResDtoDataDummy memberResDtoDataDummy = new MemberResDtoDataDummy();
        memberListResDtoDummy.setMemberResDtoList(Arrays.asList(
                memberResDtoDataDummy.getMember1(), 
                memberResDtoDataDummy.getMember2()));

        when(memberManagementService.searchMember(any(MemberSearchReqDto.class))).thenReturn(memberListResDtoDummy);

        MvcResult result = mockMvc.perform(get("/member-management/search")
                .param("username", memberSearchReqDto.getUsername()))
                .andExpect(status().isOk())
                .andReturn();

        String jsonString = result.getResponse().getContentAsString();
        MemberListResDto memberListResDto = new Gson().fromJson(jsonString, MemberListResDto.class);

        verify(memberManagementService, times(1)).searchMember(any(MemberSearchReqDto.class));
        assertEquals(memberListResDto.getMemberResDtoList().size(), 2);
    }

    // ---------- /member-management/search END ----------

}
