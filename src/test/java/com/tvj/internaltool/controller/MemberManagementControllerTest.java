package com.tvj.internaltool.controller;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
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
import com.tvj.internaltool.dto.req.MemberActivateStatusUpdateReqDto;
import com.tvj.internaltool.dto.req.MemberAddReqDto;
import com.tvj.internaltool.dto.req.MemberDeleteReqDto;
import com.tvj.internaltool.dto.req.MemberSearchReqDto;
import com.tvj.internaltool.dto.req.MemberUpdateReqDto;
import com.tvj.internaltool.dto.res.MemberListResDto;
import com.tvj.internaltool.dto.res.MessageResDto;
import com.tvj.internaltool.dummy.dto.res.MemberResDtoDataDummy;
import com.tvj.internaltool.service.MemberManagementService;
import com.tvj.internaltool.utils.CustomRestExceptionHandler;
import com.tvj.internaltool.utils.ResponseCode;
import com.tvj.internaltool.utils.ResponseMessage;

@RunWith(MockitoJUnitRunner.class)
public class MemberManagementControllerTest {

    @InjectMocks
    private MemberManagementController memberManagementController;

    @Mock
    private MemberManagementService memberManagementService;

    private MockMvc mockMvc;

    @Before // Execute before each test method
    public void setup() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(memberManagementController)
                .setControllerAdvice(new CustomRestExceptionHandler()) // add ControllerAdvice to controller test
                .build();
    }

    // ---------- /member-management/search START ----------

    @Test
    public void searchMember_success_returnZeroRecord() throws Exception {
        MemberSearchReqDto memberSearchReqDto = new MemberSearchReqDto();
        memberSearchReqDto.setUsername("ngocdc");
        memberSearchReqDto.setOffset(0);
        memberSearchReqDto.setLimit(10);

        MemberListResDto memberListResDtoDummy = new MemberListResDto();
        memberListResDtoDummy.setMemberResDtoList(new ArrayList<>());
        memberListResDtoDummy.setTotal(0);

        when(memberManagementService.searchMember(any(MemberSearchReqDto.class))).thenReturn(memberListResDtoDummy);

        MvcResult result = mockMvc
                .perform(get("/member-management/search").param("username", memberSearchReqDto.getUsername())
                        .param("offset", String.valueOf(memberSearchReqDto.getOffset()))
                        .param("limit", String.valueOf(memberSearchReqDto.getLimit())))
                .andExpect(status().isOk()).andReturn();

        String jsonString = result.getResponse().getContentAsString();
        MemberListResDto memberListResDto = new Gson().fromJson(jsonString, MemberListResDto.class);

        verify(memberManagementService, times(1)).searchMember(any(MemberSearchReqDto.class));
        assertEquals(memberListResDto.getTotal(), 0);
        assertEquals(memberListResDto.getMemberResDtoList().size(), 0);
    }

    @Test
    public void searchMember_success_returnOneRecord() throws Exception {
        MemberSearchReqDto memberSearchReqDto = new MemberSearchReqDto();
        memberSearchReqDto.setUsername("admin");
        memberSearchReqDto.setOffset(0);
        memberSearchReqDto.setLimit(10);

        MemberListResDto memberListResDtoDummy = new MemberListResDto();
        MemberResDtoDataDummy memberResDtoDataDummy = new MemberResDtoDataDummy();
        memberListResDtoDummy.setMemberResDtoList(Collections.singletonList(memberResDtoDataDummy.getMember1()));
        memberListResDtoDummy.setTotal(1);

        when(memberManagementService.searchMember(any(MemberSearchReqDto.class))).thenReturn(memberListResDtoDummy);

        MvcResult result = mockMvc
                .perform(get("/member-management/search").param("username", memberSearchReqDto.getUsername())
                        .param("offset", String.valueOf(memberSearchReqDto.getOffset()))
                        .param("limit", String.valueOf(memberSearchReqDto.getLimit())))
                .andExpect(status().isOk()).andReturn();

        String jsonString = result.getResponse().getContentAsString();
        MemberListResDto memberListResDto = new Gson().fromJson(jsonString, MemberListResDto.class);

        verify(memberManagementService, times(1)).searchMember(any(MemberSearchReqDto.class));
        assertEquals(memberListResDto.getTotal(), 1);
        assertEquals(memberListResDto.getMemberResDtoList().size(), 1);
    }

    @Test
    public void searchMember_success_returnTwoRecords() throws Exception {
        MemberSearchReqDto memberSearchReqDto = new MemberSearchReqDto();
        memberSearchReqDto.setUsername("admin");
        memberSearchReqDto.setOffset(0);
        memberSearchReqDto.setLimit(10);

        MemberListResDto memberListResDtoDummy = new MemberListResDto();
        MemberResDtoDataDummy memberResDtoDataDummy = new MemberResDtoDataDummy();
        memberListResDtoDummy.setMemberResDtoList(
                Arrays.asList(memberResDtoDataDummy.getMember1(), memberResDtoDataDummy.getMember2()));
        memberListResDtoDummy.setTotal(2);

        when(memberManagementService.searchMember(any(MemberSearchReqDto.class))).thenReturn(memberListResDtoDummy);

        MvcResult result = mockMvc
                .perform(get("/member-management/search").param("username", memberSearchReqDto.getUsername())
                        .param("offset", String.valueOf(memberSearchReqDto.getOffset()))
                        .param("limit", String.valueOf(memberSearchReqDto.getLimit())))
                .andExpect(status().isOk()).andReturn();

        String jsonString = result.getResponse().getContentAsString();
        MemberListResDto memberListResDto = new Gson().fromJson(jsonString, MemberListResDto.class);

        verify(memberManagementService, times(1)).searchMember(any(MemberSearchReqDto.class));
        assertEquals(memberListResDto.getTotal(), 2);
        assertEquals(memberListResDto.getMemberResDtoList().size(), 2);
    }

    @Test 
    public void searchMember_invalidHttpRequestMethodPOST() throws Exception {
        mockMvc.perform(post("/member-management/search")).andExpect(status().isMethodNotAllowed()).andReturn();
    }

    @Test 
    public void searchMember_invalidHttpRequestMethodPUT() throws Exception {
        mockMvc.perform(put("/member-management/search")).andExpect(status().isMethodNotAllowed()).andReturn();
    }

    @Test 
    public void searchMember_invalidHttpRequestMethodPATCH() throws Exception {
        mockMvc.perform(patch("/member-management/search")).andExpect(status().isMethodNotAllowed()).andReturn();
    }

    @Test 
    public void searchMember_invalidHttpRequestMethodDELETE() throws Exception {
        mockMvc.perform(delete("/member-management/search")).andExpect(status().isMethodNotAllowed()).andReturn();
    }

    @Test 
    public void searchMember_nameSizeExceedsMaximumLimit() throws Exception {
        // Value from client
        MemberSearchReqDto memberSearchReqDto = new MemberSearchReqDto();
        memberSearchReqDto.setName("12345678901234567890123456789012345678901");
        memberSearchReqDto.setOffset(0);
        memberSearchReqDto.setLimit(10);

        mockMvc.perform(get("/member-management/search").param("name", memberSearchReqDto.getName())
                .param("offset", String.valueOf(memberSearchReqDto.getOffset()))
                .param("limit", String.valueOf(memberSearchReqDto.getLimit()))).andExpect(status().isBadRequest())
                .andReturn();
    }

    @Test 
    public void searchMember_usernameSizeExceedsMaximumLimit() throws Exception {
        // Value from client
        MemberSearchReqDto memberSearchReqDto = new MemberSearchReqDto();
        memberSearchReqDto.setUsername("123456789012345678901");
        memberSearchReqDto.setOffset(0);
        memberSearchReqDto.setLimit(10);

        mockMvc.perform(get("/member-management/search").param("username", memberSearchReqDto.getUsername())
                .param("offset", String.valueOf(memberSearchReqDto.getOffset()))
                .param("limit", String.valueOf(memberSearchReqDto.getLimit()))).andExpect(status().isBadRequest())
                .andReturn();
    }

    @Test 
    public void searchMember_titleIdSizeExceedsMaximumLimit() throws Exception {
        // Value from client
        MemberSearchReqDto memberSearchReqDto = new MemberSearchReqDto();
        memberSearchReqDto.setTitleId("12345678901234567890123456789012345678901");
        memberSearchReqDto.setOffset(0);
        memberSearchReqDto.setLimit(10);

        mockMvc.perform(get("/member-management/search").param("titleId", memberSearchReqDto.getTitleId())
                .param("offset", String.valueOf(memberSearchReqDto.getOffset()))
                .param("limit", String.valueOf(memberSearchReqDto.getLimit()))).andExpect(status().isBadRequest())
                .andReturn();
    }

    @Test 
    public void searchMember_offsetSizeExceedsMaximumLimit() throws Exception {
        // Value from client
        MemberSearchReqDto memberSearchReqDto = new MemberSearchReqDto();
        memberSearchReqDto.setOffset(-1);
        memberSearchReqDto.setLimit(10);

        mockMvc.perform(get("/member-management/search").param("offset", String.valueOf(memberSearchReqDto.getOffset()))
                .param("limit", String.valueOf(memberSearchReqDto.getLimit()))).andExpect(status().isBadRequest())
                .andReturn();
    }

    @Test 
    public void searchMember_limitSizeExceedsMaximumLimit() throws Exception {
        // Value from client
        MemberSearchReqDto memberSearchReqDto = new MemberSearchReqDto();
        memberSearchReqDto.setOffset(0);
        memberSearchReqDto.setLimit(0);

        mockMvc.perform(get("/member-management/search").param("offset", String.valueOf(memberSearchReqDto.getOffset()))
                .param("limit", String.valueOf(memberSearchReqDto.getLimit()))).andExpect(status().isBadRequest())
                .andReturn();
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
    public void addMember_invalidHttpRequestMethodGET() throws Exception {
        mockMvc.perform(get("/member-management/add-member")).andExpect(status().isMethodNotAllowed()).andReturn();
    }

    @Test 
    public void addMember_invalidHttpRequestMethodPUT() throws Exception {
        mockMvc.perform(put("/member-management/add-member")).andExpect(status().isMethodNotAllowed()).andReturn();
    }

    @Test 
    public void addMember_invalidHttpRequestMethodPATCH() throws Exception {
        mockMvc.perform(patch("/member-management/add-member")).andExpect(status().isMethodNotAllowed()).andReturn();
    }

    @Test 
    public void addMember_invalidHttpRequestMethodDELETE() throws Exception {
        mockMvc.perform(delete("/member-management/add-member")).andExpect(status().isMethodNotAllowed()).andReturn();
    }

    @Test 
    public void addMember_usernameIsBlank() throws Exception {
        // Value from client
        MemberAddReqDto memberAddReqDto = new MemberAddReqDto();
        memberAddReqDto.setUsername("");
        memberAddReqDto.setFirstName("Dinh");
        memberAddReqDto.setLastName("Ngoc");
        memberAddReqDto.setTitleId("1");
        memberAddReqDto.setEmail("ngocdc@tinhvan.com");
        memberAddReqDto.setPassword("12345678");

        mockMvc.perform(
                post("/member-management/add-member").content(new ObjectMapper().writeValueAsString(memberAddReqDto))
                        .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest()).andReturn();
    }

    @Test 
    public void addMember_usernameSizeExceedsMaximumLimit() throws Exception {
        // Value from client
        MemberAddReqDto memberAddReqDto = new MemberAddReqDto();
        memberAddReqDto.setUsername("a12345678901234567890");
        memberAddReqDto.setFirstName("Dinh");
        memberAddReqDto.setLastName("Ngoc");
        memberAddReqDto.setTitleId("1");
        memberAddReqDto.setEmail("ngocdc@tinhvan.com");
        memberAddReqDto.setPassword("12345678");

        mockMvc.perform(
                post("/member-management/add-member").content(new ObjectMapper().writeValueAsString(memberAddReqDto))
                        .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest()).andReturn();
    }

    @Test 
    public void addMember_firstNameIsBlank() throws Exception {
        // Value from client
        MemberAddReqDto memberAddReqDto = new MemberAddReqDto();
        memberAddReqDto.setUsername("ngocdc");
        memberAddReqDto.setFirstName("");
        memberAddReqDto.setLastName("Ngoc");
        memberAddReqDto.setTitleId("1");
        memberAddReqDto.setEmail("ngocdc@tinhvan.com");
        memberAddReqDto.setPassword("12345678");

        mockMvc.perform(
                post("/member-management/add-member").content(new ObjectMapper().writeValueAsString(memberAddReqDto))
                        .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest()).andReturn();
    }

    @Test 
    public void addMember_lastNameIsBlank() throws Exception {
        // Value from client
        MemberAddReqDto memberAddReqDto = new MemberAddReqDto();
        memberAddReqDto.setUsername("ngocdc");
        memberAddReqDto.setFirstName("Dinh");
        memberAddReqDto.setLastName("");
        memberAddReqDto.setTitleId("1");
        memberAddReqDto.setEmail("ngocdc@tinhvan.com");
        memberAddReqDto.setPassword("12345678");

        mockMvc.perform(
                post("/member-management/add-member").content(new ObjectMapper().writeValueAsString(memberAddReqDto))
                        .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest()).andReturn();
    }

    @Test 
    public void addMember_titleIdIsBlank() throws Exception {
        // Value from client
        MemberAddReqDto memberAddReqDto = new MemberAddReqDto();
        memberAddReqDto.setUsername("ngocdc");
        memberAddReqDto.setFirstName("Dinh");
        memberAddReqDto.setLastName("Ngoc");
        memberAddReqDto.setTitleId("");
        memberAddReqDto.setEmail("ngocdc@tinhvan.com");
        memberAddReqDto.setPassword("12345678");

        mockMvc.perform(
                post("/member-management/add-member").content(new ObjectMapper().writeValueAsString(memberAddReqDto))
                        .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest()).andReturn();
    }

    @Test 
    public void addMember_titleIdSizeExceedsMaximumLimit() throws Exception {
        // Value from client
        MemberAddReqDto memberAddReqDto = new MemberAddReqDto();
        memberAddReqDto.setUsername("ngocdc");
        memberAddReqDto.setFirstName("Dinh");
        memberAddReqDto.setLastName("Ngoc");
        memberAddReqDto.setTitleId("a12345678901234567890");
        memberAddReqDto.setEmail("ngocdc@tinhvan.com");
        memberAddReqDto.setPassword("12345678");

        mockMvc.perform(
                post("/member-management/add-member").content(new ObjectMapper().writeValueAsString(memberAddReqDto))
                        .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest()).andReturn();
    }

    @Test 
    public void addMember_emailIsBlank() throws Exception {
        // Value from client
        MemberAddReqDto memberAddReqDto = new MemberAddReqDto();
        memberAddReqDto.setUsername("ngocdc");
        memberAddReqDto.setFirstName("Dinh");
        memberAddReqDto.setLastName("Ngoc");
        memberAddReqDto.setTitleId("1");
        memberAddReqDto.setEmail("");
        memberAddReqDto.setPassword("12345678");

        mockMvc.perform(
                post("/member-management/add-member").content(new ObjectMapper().writeValueAsString(memberAddReqDto))
                        .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest()).andReturn();
    }

    @Test 
    public void addMember_emailIsInvalid() throws Exception {
        // Value from client
        MemberAddReqDto memberAddReqDto = new MemberAddReqDto();
        memberAddReqDto.setUsername("ngocdc");
        memberAddReqDto.setFirstName("Dinh");
        memberAddReqDto.setLastName("Ngoc");
        memberAddReqDto.setTitleId("1");
        memberAddReqDto.setEmail("ngocdc@@tinhvan.com");
        memberAddReqDto.setPassword("12345678");

        mockMvc.perform(
                post("/member-management/add-member").content(new ObjectMapper().writeValueAsString(memberAddReqDto))
                        .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest()).andReturn();
    }

    @Test 
    public void addMember_passwordIsBlank() throws Exception {
        // Value from client
        MemberAddReqDto memberAddReqDto = new MemberAddReqDto();
        memberAddReqDto.setUsername("ngocdc");
        memberAddReqDto.setFirstName("Dinh");
        memberAddReqDto.setLastName("Ngoc");
        memberAddReqDto.setTitleId("1");
        memberAddReqDto.setEmail("ngocdc@tinhvan.com");
        memberAddReqDto.setPassword("");

        mockMvc.perform(
                post("/member-management/add-member").content(new ObjectMapper().writeValueAsString(memberAddReqDto))
                        .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest()).andReturn();
    }

    @Test 
    public void addMember_passwordSizeOutOfBounds() throws Exception {
        // Value from client
        MemberAddReqDto memberAddReqDto = new MemberAddReqDto();
        memberAddReqDto.setUsername("ngocdc");
        memberAddReqDto.setFirstName("Dinh");
        memberAddReqDto.setLastName("Ngoc");
        memberAddReqDto.setTitleId("1");
        memberAddReqDto.setEmail("ngocdc@tinhvan.com");
        memberAddReqDto.setPassword("123456789012345678901");

        mockMvc.perform(
                post("/member-management/add-member").content(new ObjectMapper().writeValueAsString(memberAddReqDto))
                        .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest()).andReturn();
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
    public void updateMember_invalidHttpRequestMethodGET() throws Exception {
        mockMvc.perform(get("/member-management/update-member")).andExpect(status().isMethodNotAllowed()).andReturn();
    }

    @Test 
    public void updateMember_invalidHttpRequestMethodPOST() throws Exception {
        mockMvc.perform(post("/member-management/update-member")).andExpect(status().isMethodNotAllowed()).andReturn();
    }

    @Test 
    public void updateMember_invalidHttpRequestMethodPATCH() throws Exception {
        mockMvc.perform(patch("/member-management/update-member")).andExpect(status().isMethodNotAllowed()).andReturn();
    }

    @Test 
    public void updateMember_invalidHttpRequestMethodDELETE() throws Exception {
        mockMvc.perform(delete("/member-management/update-member")).andExpect(status().isMethodNotAllowed())
                .andReturn();
    }

    @Test 
    public void updateMember_usernameIsBlank() throws Exception {
        // Value from client
        MemberUpdateReqDto memberUpdateReqDto = new MemberUpdateReqDto();
        memberUpdateReqDto.setUsername("");
        memberUpdateReqDto.setFirstName("Dinh");
        memberUpdateReqDto.setLastName("Ngoc");
        memberUpdateReqDto.setTitleId("1");
        memberUpdateReqDto.setEmail("ngocdc@tinhvan.com");

        mockMvc.perform(put("/member-management/update-member")
                .content(new ObjectMapper().writeValueAsString(memberUpdateReqDto))
                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest()).andReturn();
    }

    @Test 
    public void updateMember_usernameSizeExceedsMaximumLimit() throws Exception {
        // Value from client
        MemberUpdateReqDto memberUpdateReqDto = new MemberUpdateReqDto();
        memberUpdateReqDto.setUsername("a12345678901234567890");
        memberUpdateReqDto.setFirstName("Dinh");
        memberUpdateReqDto.setLastName("Ngoc");
        memberUpdateReqDto.setTitleId("1");
        memberUpdateReqDto.setEmail("ngocdc@tinhvan.com");

        mockMvc.perform(put("/member-management/update-member")
                .content(new ObjectMapper().writeValueAsString(memberUpdateReqDto))
                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest()).andReturn();
    }

    @Test 
    public void updateMember_firstNameIsBlank() throws Exception {
        // Value from client
        MemberUpdateReqDto memberUpdateReqDto = new MemberUpdateReqDto();
        memberUpdateReqDto.setUsername("ngocdc");
        memberUpdateReqDto.setFirstName("");
        memberUpdateReqDto.setLastName("Ngoc");
        memberUpdateReqDto.setTitleId("1");
        memberUpdateReqDto.setEmail("ngocdc@tinhvan.com");

        mockMvc.perform(put("/member-management/update-member")
                .content(new ObjectMapper().writeValueAsString(memberUpdateReqDto))
                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest()).andReturn();
    }

    @Test 
    public void updateMember_lastNameIsBlank() throws Exception {
        // Value from client
        MemberUpdateReqDto memberUpdateReqDto = new MemberUpdateReqDto();
        memberUpdateReqDto.setUsername("ngocdc");
        memberUpdateReqDto.setFirstName("Dinh");
        memberUpdateReqDto.setLastName("");
        memberUpdateReqDto.setTitleId("1");
        memberUpdateReqDto.setEmail("ngocdc@tinhvan.com");

        mockMvc.perform(put("/member-management/update-member")
                .content(new ObjectMapper().writeValueAsString(memberUpdateReqDto))
                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest()).andReturn();
    }

    @Test 
    public void updateMember_titleIdIsBlank() throws Exception {
        // Value from client
        MemberUpdateReqDto memberUpdateReqDto = new MemberUpdateReqDto();
        memberUpdateReqDto.setUsername("ngocdc");
        memberUpdateReqDto.setFirstName("Dinh");
        memberUpdateReqDto.setLastName("Ngoc");
        memberUpdateReqDto.setTitleId("");
        memberUpdateReqDto.setEmail("ngocdc@tinhvan.com");

        mockMvc.perform(put("/member-management/update-member")
                .content(new ObjectMapper().writeValueAsString(memberUpdateReqDto))
                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest()).andReturn();
    }

    @Test 
    public void updateMember_titleIdSizeExceedsMaximumLimit() throws Exception {
        // Value from client
        MemberUpdateReqDto memberUpdateReqDto = new MemberUpdateReqDto();
        memberUpdateReqDto.setUsername("ngocdc");
        memberUpdateReqDto.setFirstName("Dinh");
        memberUpdateReqDto.setLastName("Ngoc");
        memberUpdateReqDto.setTitleId("12345678901234567890123456789012345678901");
        memberUpdateReqDto.setEmail("ngocdc@tinhvan.com");

        mockMvc.perform(put("/member-management/update-member")
                .content(new ObjectMapper().writeValueAsString(memberUpdateReqDto))
                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest()).andReturn();
    }

    @Test 
    public void updateMember_emailIdIsBlank() throws Exception {
        // Value from client
        MemberUpdateReqDto memberUpdateReqDto = new MemberUpdateReqDto();
        memberUpdateReqDto.setUsername("ngocdc");
        memberUpdateReqDto.setFirstName("Dinh");
        memberUpdateReqDto.setLastName("Ngoc");
        memberUpdateReqDto.setTitleId("1");
        memberUpdateReqDto.setEmail("");

        mockMvc.perform(put("/member-management/update-member")
                .content(new ObjectMapper().writeValueAsString(memberUpdateReqDto))
                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest()).andReturn();
    }

    @Test 
    public void updateMember_emailIdIsInvalid() throws Exception {
        // Value from client
        MemberUpdateReqDto memberUpdateReqDto = new MemberUpdateReqDto();
        memberUpdateReqDto.setUsername("ngocdc");
        memberUpdateReqDto.setFirstName("Dinh");
        memberUpdateReqDto.setLastName("Ngoc");
        memberUpdateReqDto.setTitleId("1");
        memberUpdateReqDto.setEmail("ngocdc@@tinhvan.com");

        mockMvc.perform(put("/member-management/update-member")
                .content(new ObjectMapper().writeValueAsString(memberUpdateReqDto))
                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest()).andReturn();
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

    // ---------- /member-management/view-member START ----------

    @Test
    public void viewMember_success() throws Exception {
        // Value from client
        String username = "ngocdc";

        MemberResDtoDataDummy memberResDtoDataDummy = new MemberResDtoDataDummy();

        when(memberManagementService.viewMember(username)).thenReturn(memberResDtoDataDummy.getMember1());

        mockMvc.perform(get("/member-management/view-member").param("username", username)).andExpect(status().isOk())
                .andReturn();

        verify(memberManagementService, times(1)).viewMember(username);
    }

    @Test 
    public void viewMember_invalidHttpRequestMethodPOST() throws Exception {
        mockMvc.perform(post("/member-management/view-member")).andExpect(status().isMethodNotAllowed()).andReturn();
    }

    @Test 
    public void viewMember_invalidHttpRequestMethodPUT() throws Exception {
        mockMvc.perform(put("/member-management/view-member")).andExpect(status().isMethodNotAllowed()).andReturn();
    }

    @Test 
    public void viewMember_invalidHttpRequestMethodPATCH() throws Exception {
        mockMvc.perform(patch("/member-management/view-member")).andExpect(status().isMethodNotAllowed()).andReturn();
    }

    @Test 
    public void viewMember_invalidHttpRequestMethodDELETE() throws Exception {
        mockMvc.perform(delete("/member-management/view-member")).andExpect(status().isMethodNotAllowed()).andReturn();
    }

    @Test
    public void viewMember_memberDoesNotExist() throws Exception {
        // Value from client
        String username = "ngocdc";

        when(memberManagementService.viewMember(username)).thenReturn(null);

        MvcResult result = mockMvc.perform(get("/member-management/view-member").param("username", username))
                .andExpect(status().isBadRequest()).andReturn();

        String jsonString = result.getResponse().getContentAsString();
        MessageResDto messageResDto = new Gson().fromJson(jsonString, MessageResDto.class);

        verify(memberManagementService, times(1)).viewMember(username);
        assertEquals(messageResDto.getCode(), ResponseCode.VIEW_MEMBER_FAILED);
        assertEquals(messageResDto.getMessage(), ResponseMessage.VIEW_MEMBER_FAILED);
    }

    // ---------- /member-management/view-member END ----------

    // ---------- /member-management/update-member-activate-status START ----------

    @Test
    public void updateMemberActivateStatus_success() throws Exception {
        // Value from client
        MemberActivateStatusUpdateReqDto memberActivateStatusUpdateReqDto = new MemberActivateStatusUpdateReqDto();
        memberActivateStatusUpdateReqDto.setUsername("ngocdc");
        memberActivateStatusUpdateReqDto.setActivated(true);

        when(memberManagementService.updateMemberActivateStatus(any(MemberActivateStatusUpdateReqDto.class)))
                .thenReturn(true);

        MvcResult result = mockMvc
                .perform(patch("/member-management/update-member-activate-status")
                        .content(new ObjectMapper().writeValueAsString(memberActivateStatusUpdateReqDto))
                        .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();

        String jsonString = result.getResponse().getContentAsString();
        MessageResDto messageResDto = new Gson().fromJson(jsonString, MessageResDto.class);

        verify(memberManagementService, times(1))
                .updateMemberActivateStatus(any(MemberActivateStatusUpdateReqDto.class));
        assertEquals(messageResDto.getCode(), ResponseCode.UPDATE_MEMBER_ACTIVATED_STATUS_SUCCESS);
        assertEquals(messageResDto.getMessage(), ResponseMessage.UPDATE_MEMBER_ACTIVATED_STATUS_SUCCESS);
    }

    @Test 
    public void updateMemberActivateStatus_invalidHttpRequestMethodGET() throws Exception {
        mockMvc.perform(get("/member-management/update-member-activate-status"))
                .andExpect(status().isMethodNotAllowed()).andReturn();
    }

    @Test 
    public void updateMemberActivateStatus_invalidHttpRequestMethodPOST() throws Exception {
        mockMvc.perform(post("/member-management/update-member-activate-status"))
                .andExpect(status().isMethodNotAllowed()).andReturn();
    }

    @Test 
    public void vupdateMemberActivateStatus_invalidHttpRequestMethodPUT() throws Exception {
        mockMvc.perform(put("/member-management/update-member-activate-status"))
                .andExpect(status().isMethodNotAllowed()).andReturn();
    }

    @Test 
    public void updateMemberActivateStatus_invalidHttpRequestMethodDELETE() throws Exception {
        mockMvc.perform(delete("/member-management/update-member-activate-status"))
                .andExpect(status().isMethodNotAllowed()).andReturn();
    }

    @Test 
    public void updateMemberActivateStatus_usernameIsBlank() throws Exception {
        // Value from client
        MemberActivateStatusUpdateReqDto memberActivateStatusUpdateReqDto = new MemberActivateStatusUpdateReqDto();
        memberActivateStatusUpdateReqDto.setUsername("");
        memberActivateStatusUpdateReqDto.setActivated(true);

        mockMvc.perform(patch("/member-management/update-member-activate-status")
                .content(new ObjectMapper().writeValueAsString(memberActivateStatusUpdateReqDto))
                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest()).andReturn();
    }

    @Test 
    public void updateMemberActivateStatus_usernameSizeExceedsMaximumLimit() throws Exception {
        // Value from client
        MemberActivateStatusUpdateReqDto memberActivateStatusUpdateReqDto = new MemberActivateStatusUpdateReqDto();
        memberActivateStatusUpdateReqDto.setUsername("a12345678901234567890");
        memberActivateStatusUpdateReqDto.setActivated(true);

        mockMvc.perform(patch("/member-management/update-member-activate-status")
                .content(new ObjectMapper().writeValueAsString(memberActivateStatusUpdateReqDto))
                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest()).andReturn();
    }

    @Test
    public void updateMemberActivateStatus_memberDoesNotExist() throws Exception {
        // Value from client
        MemberActivateStatusUpdateReqDto memberActivateStatusUpdateReqDto = new MemberActivateStatusUpdateReqDto();
        memberActivateStatusUpdateReqDto.setUsername("ngocdc");
        memberActivateStatusUpdateReqDto.setActivated(true);

        when(memberManagementService.updateMemberActivateStatus(any(MemberActivateStatusUpdateReqDto.class)))
                .thenReturn(false);

        MvcResult result = mockMvc
                .perform(patch("/member-management/update-member-activate-status")
                        .content(new ObjectMapper().writeValueAsString(memberActivateStatusUpdateReqDto))
                        .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest()).andReturn();

        String jsonString = result.getResponse().getContentAsString();
        MessageResDto messageResDto = new Gson().fromJson(jsonString, MessageResDto.class);

        verify(memberManagementService, times(1))
                .updateMemberActivateStatus(any(MemberActivateStatusUpdateReqDto.class));
        assertEquals(messageResDto.getCode(), ResponseCode.UPDATE_MEMBER_ACTIVATED_STATUS_FAILED);
        assertEquals(messageResDto.getMessage(), ResponseMessage.UPDATE_MEMBER_ACTIVATED_STATUS_FAILED);
    }

    // ---------- /member-management/update-member-activate-status END ----------

    // ---------- /member-management/delete-member START ----------

    @Test
    public void deleteMember_success() throws Exception {
        // Value from client
        MemberDeleteReqDto memberDeleteReqDto = new MemberDeleteReqDto();
        memberDeleteReqDto.setUsername("ngocdc");

        when(memberManagementService.deleteMember(any(MemberDeleteReqDto.class))).thenReturn(true);

        MvcResult result = mockMvc
                .perform(delete("/member-management/delete-member")
                        .content(new ObjectMapper().writeValueAsString(memberDeleteReqDto))
                        .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();

        String jsonString = result.getResponse().getContentAsString();
        MessageResDto messageResDto = new Gson().fromJson(jsonString, MessageResDto.class);

        verify(memberManagementService, times(1)).deleteMember(any(MemberDeleteReqDto.class));
        assertEquals(messageResDto.getCode(), ResponseCode.DELETE_MEMBER_SUCCESS);
        assertEquals(messageResDto.getMessage(), ResponseMessage.DELETE_MEMBER_SUCCESS);
    }

    @Test 
    public void deleteMember_invalidHttpRequestMethodGET() throws Exception {
        mockMvc.perform(get("/member-management/delete-member")).andExpect(status().isMethodNotAllowed()).andReturn();
    }

    @Test 
    public void deleteMember_invalidHttpRequestMethodPOST() throws Exception {
        mockMvc.perform(post("/member-management/delete-member")).andExpect(status().isMethodNotAllowed()).andReturn();
    }

    @Test 
    public void deleteMember_invalidHttpRequestMethodPUT() throws Exception {
        mockMvc.perform(put("/member-management/delete-member")).andExpect(status().isMethodNotAllowed()).andReturn();
    }

    @Test 
    public void deleteMember_invalidHttpRequestMethodPATCH() throws Exception {
        mockMvc.perform(patch("/member-management/delete-member")).andExpect(status().isMethodNotAllowed()).andReturn();
    }

    @Test 
    public void deleteMember_usernameIsBlank() throws Exception {
        // Value from client
        MemberDeleteReqDto memberDeleteReqDto = new MemberDeleteReqDto();
        memberDeleteReqDto.setUsername("");

        mockMvc.perform(delete("/member-management/delete-member")
                .content(new ObjectMapper().writeValueAsString(memberDeleteReqDto))
                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest()).andReturn();
    }

    @Test 
    public void deleteMember_usernameSizeExceedsMaximumLimit() throws Exception {
        // Value from client
        MemberDeleteReqDto memberDeleteReqDto = new MemberDeleteReqDto();
        memberDeleteReqDto.setUsername("a12345678901234567890");

        mockMvc.perform(delete("/member-management/delete-member")
                .content(new ObjectMapper().writeValueAsString(memberDeleteReqDto))
                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest()).andReturn();
    }

    @Test
    public void deleteMember_memberDoesNotExist() throws Exception {
        // Value from client
        MemberDeleteReqDto memberDeleteReqDto = new MemberDeleteReqDto();
        memberDeleteReqDto.setUsername("ngocdc");

        when(memberManagementService.deleteMember(any(MemberDeleteReqDto.class))).thenReturn(false);

        MvcResult result = mockMvc
                .perform(delete("/member-management/delete-member")
                        .content(new ObjectMapper().writeValueAsString(memberDeleteReqDto))
                        .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest()).andReturn();

        String jsonString = result.getResponse().getContentAsString();
        MessageResDto messageResDto = new Gson().fromJson(jsonString, MessageResDto.class);

        verify(memberManagementService, times(1)).deleteMember(any(MemberDeleteReqDto.class));
        assertEquals(messageResDto.getCode(), ResponseCode.DELETE_MEMBER_FAILED);
        assertEquals(messageResDto.getMessage(), ResponseMessage.DELETE_MEMBER_FAILED);
    }

    // ---------- /member-management/delete-member END ----------

}
