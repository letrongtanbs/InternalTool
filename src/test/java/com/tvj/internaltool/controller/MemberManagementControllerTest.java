package com.tvj.internaltool.controller;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
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
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.tvj.internaltool.dto.req.MemberAddReqDto;
import com.tvj.internaltool.dto.req.MemberSearchReqDto;
import com.tvj.internaltool.dto.req.MemberUpdateReqDto;
import com.tvj.internaltool.dto.res.MemberListResDto;
import com.tvj.internaltool.dto.res.MessageResDto;
import com.tvj.internaltool.dummy.dto.res.MemberResDtoDataDummy;
import com.tvj.internaltool.service.MemberManagementService;
import com.tvj.internaltool.utils.ResponseCode;
import com.tvj.internaltool.utils.ResponseMessage;

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

        MvcResult result = mockMvc
                .perform(get("/member-management/search").param("username", memberSearchReqDto.getUsername()))
                .andExpect(status().isOk()).andReturn();

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

        MvcResult result = mockMvc
                .perform(get("/member-management/search").param("username", memberSearchReqDto.getUsername()))
                .andExpect(status().isOk()).andReturn();

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
        memberListResDtoDummy.setMemberResDtoList(
                Arrays.asList(memberResDtoDataDummy.getMember1(), memberResDtoDataDummy.getMember2()));

        when(memberManagementService.searchMember(any(MemberSearchReqDto.class))).thenReturn(memberListResDtoDummy);

        MvcResult result = mockMvc
                .perform(get("/member-management/search").param("username", memberSearchReqDto.getUsername()))
                .andExpect(status().isOk()).andReturn();

        String jsonString = result.getResponse().getContentAsString();
        MemberListResDto memberListResDto = new Gson().fromJson(jsonString, MemberListResDto.class);

        verify(memberManagementService, times(1)).searchMember(any(MemberSearchReqDto.class));
        assertEquals(memberListResDto.getMemberResDtoList().size(), 2);
    }

    // ---------- /member-management/search END ----------

    // ---------- /member-management/add-member START ----------

    @Test
    public void addMember_success() throws Exception {
        // Value from client
        MemberAddReqDto memberAddReqDto = new MemberAddReqDto();
        memberAddReqDto.setUsername("ngocdc");
        memberAddReqDto.setFirstName("Dinh");
        memberAddReqDto.setLastName("Ngoc");
        memberAddReqDto.setTitleId("1");
        memberAddReqDto.setEmail("ngocdc@tinhvan.com");
        memberAddReqDto.setPassword("12345678");

        when(memberManagementService.addMember(any(MemberAddReqDto.class))).thenReturn(true);

        MvcResult result = mockMvc
                .perform(post("/member-management/add-member")
                        .content(new ObjectMapper().writeValueAsString(memberAddReqDto))
                        .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();

        String jsonString = result.getResponse().getContentAsString();
        MessageResDto messageResDto = new Gson().fromJson(jsonString, MessageResDto.class);

        verify(memberManagementService, times(1)).addMember(any(MemberAddReqDto.class));
        assertEquals(messageResDto.getCode(), ResponseCode.ADD_NEW_MEMBER_SUCCESS);
        assertEquals(messageResDto.getMessage(), ResponseMessage.ADD_NEW_MEMBER_SUCCESS);
    }

    @Test
    public void addMember_memberExists() throws Exception {
        // Value from client
        MemberAddReqDto memberAddReqDto = new MemberAddReqDto();
        memberAddReqDto.setUsername("ngocdc");
        memberAddReqDto.setFirstName("Dinh");
        memberAddReqDto.setLastName("Ngoc");
        memberAddReqDto.setTitleId("1");
        memberAddReqDto.setEmail("ngocdc@tinhvan.com");
        memberAddReqDto.setPassword("12345678");

        when(memberManagementService.addMember(any(MemberAddReqDto.class))).thenReturn(false);

        MvcResult result = mockMvc
                .perform(post("/member-management/add-member")
                        .content(new ObjectMapper().writeValueAsString(memberAddReqDto))
                        .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest()).andReturn();

        String jsonString = result.getResponse().getContentAsString();
        MessageResDto messageResDto = new Gson().fromJson(jsonString, MessageResDto.class);

        verify(memberManagementService, times(1)).addMember(any(MemberAddReqDto.class));
        assertEquals(messageResDto.getCode(), ResponseCode.ADD_NEW_MEMBER_FAILED);
        assertEquals(messageResDto.getMessage(), ResponseMessage.ADD_NEW_MEMBER_FAILED);
    }

    // ---------- /member-management/add-member END ----------

    // ---------- /member-management/update-member START ----------

    @Test
    public void updateMember_success() throws Exception {
        // Value from client
        MemberUpdateReqDto memberUpdateReqDto = new MemberUpdateReqDto();
        memberUpdateReqDto.setUsername("ngocdc");
        memberUpdateReqDto.setFirstName("Dinh");
        memberUpdateReqDto.setLastName("Ngoc");
        memberUpdateReqDto.setTitleId("1");
        memberUpdateReqDto.setEmail("ngocdc@tinhvan.com");

        when(memberManagementService.updateMember(any(MemberUpdateReqDto.class))).thenReturn(true);

        MvcResult result = mockMvc
                .perform(put("/member-management/update-member")
                        .content(new ObjectMapper().writeValueAsString(memberUpdateReqDto))
                        .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();

        String jsonString = result.getResponse().getContentAsString();
        MessageResDto messageResDto = new Gson().fromJson(jsonString, MessageResDto.class);

        verify(memberManagementService, times(1)).updateMember(any(MemberUpdateReqDto.class));
        assertEquals(messageResDto.getCode(), ResponseCode.UPDATE_MEMBER_SUCCESS);
        assertEquals(messageResDto.getMessage(), ResponseMessage.UPDATE_MEMBER_SUCCESS);
    }

    @Test
    public void updateMember_memberDoesNotExist() throws Exception {
        // Value from client
        MemberUpdateReqDto memberUpdateReqDto = new MemberUpdateReqDto();
        memberUpdateReqDto.setUsername("ngocdc");
        memberUpdateReqDto.setFirstName("Dinh");
        memberUpdateReqDto.setLastName("Ngoc");
        memberUpdateReqDto.setTitleId("1");
        memberUpdateReqDto.setEmail("ngocdc@tinhvan.com");

        when(memberManagementService.updateMember(any(MemberUpdateReqDto.class))).thenReturn(false);

        MvcResult result = mockMvc
                .perform(put("/member-management/update-member")
                        .content(new ObjectMapper().writeValueAsString(memberUpdateReqDto))
                        .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest()).andReturn();

        String jsonString = result.getResponse().getContentAsString();
        MessageResDto messageResDto = new Gson().fromJson(jsonString, MessageResDto.class);

        verify(memberManagementService, times(1)).updateMember(any(MemberUpdateReqDto.class));
        assertEquals(messageResDto.getCode(), ResponseCode.UPDATE_MEMBER_FAILED);
        assertEquals(messageResDto.getMessage(), ResponseMessage.UPDATE_MEMBER_FAILED);
    }

    // ---------- /member-management/update-member END ----------

}
