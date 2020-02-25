package com.tvj.internaltool.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.tvj.internaltool.dto.req.UserLoginReqDto;
import com.tvj.internaltool.dto.res.MessageResDto;
import com.tvj.internaltool.dto.res.UserLoginResDto;
import com.tvj.internaltool.dummy.dto.res.UserLoginResDtoDataDummy;
import com.tvj.internaltool.service.UserService;
import com.tvj.internaltool.utils.ResponseCode;
import com.tvj.internaltool.utils.ResponseMessage;
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

import java.io.UnsupportedEncodingException;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(MockitoJUnitRunner.class)
public class UserControllerTest {

    private MockMvc mockMvc;

    @InjectMocks
    private UserController userController;

    @Mock
    private UserService userService;

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }

    @Test // Do not use org.junit.jupiter.api
    public void generateAuthenticationToken_success() {

        UserLoginReqDto userLoginReqDto = new UserLoginReqDto();
        userLoginReqDto.setUsername("admin1");
        userLoginReqDto.setPassword("12345678");

        UserLoginResDtoDataDummy adminResDto = new UserLoginResDtoDataDummy();
        when(userService.processLogin(userLoginReqDto.getUsername(), userLoginReqDto.getPassword())).thenReturn(adminResDto.getAdminUserResDto1());

        MvcResult result = null;
        try {
            result = mockMvc.perform(post("/user/login")
                    .content(new ObjectMapper().writeValueAsString(userLoginReqDto))
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andReturn();
        } catch (Exception e) {
            e.printStackTrace();
        }

        String jsonString = null;
        try {
            assert result != null;
            jsonString = result.getResponse().getContentAsString();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        UserLoginResDto userLoginResDto = new Gson().fromJson(jsonString, UserLoginResDto.class);

        verify(userService, times(1)).processLogin(userLoginReqDto.getUsername(), userLoginReqDto.getPassword());
        assertEquals(userLoginResDto.getToken(), adminResDto.getAdminUserResDto1().getToken());
        assertEquals(userLoginResDto.getFirstName(), adminResDto.getAdminUserResDto1().getFirstName());
        assertEquals(userLoginResDto.getLastName(), adminResDto.getAdminUserResDto1().getLastName());
        assertEquals(userLoginResDto.getRoleName(), adminResDto.getAdminUserResDto1().getRoleName());
        assertEquals(userLoginResDto.isFirstTimeLogin(), adminResDto.getAdminUserResDto1().isFirstTimeLogin());
    }

    @Test // Do not use org.junit.jupiter.api
    public void generateAuthenticationToken_userIsUnauthorized() {

        UserLoginReqDto userLoginReqDto = new UserLoginReqDto();
        userLoginReqDto.setUsername("admin1");
        userLoginReqDto.setPassword("12345678");

        when(userService.processLogin(userLoginReqDto.getUsername(), userLoginReqDto.getPassword())).thenReturn(ResponseCode.UNAUTHORIZED);

        MvcResult result = null;
        try {
            result = mockMvc.perform(post("/user/login")
                    .content(new ObjectMapper().writeValueAsString(userLoginReqDto))
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isUnauthorized())
                    .andReturn();
        } catch (Exception e) {
            e.printStackTrace();
        }

        String jsonString = null;
        try {
            assert result != null;
            jsonString = result.getResponse().getContentAsString();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        MessageResDto messageResDto = new Gson().fromJson(jsonString, MessageResDto.class);

        verify(userService, times(1)).processLogin(userLoginReqDto.getUsername(), userLoginReqDto.getPassword());
        assertEquals(messageResDto.getCode(), ResponseCode.UNAUTHORIZED);
        assertEquals(messageResDto.getMessage(), ResponseMessage.UNAUTHORIZED);
    }

    @Test // Do not use org.junit.jupiter.api
    public void generateAuthenticationToken_userIsLocked() {

        UserLoginReqDto userLoginReqDto = new UserLoginReqDto();
        userLoginReqDto.setUsername("admin1");
        userLoginReqDto.setPassword("12345678");

        when(userService.processLogin(userLoginReqDto.getUsername(), userLoginReqDto.getPassword())).thenReturn(ResponseCode.USER_IS_LOCKED);

        MvcResult result = null;
        try {
            result = mockMvc.perform(post("/user/login")
                    .content(new ObjectMapper().writeValueAsString(userLoginReqDto))
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isLocked())
                    .andReturn();
        } catch (Exception e) {
            e.printStackTrace();
        }

        String jsonString = null;
        try {
            assert result != null;
            jsonString = result.getResponse().getContentAsString();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        MessageResDto messageResDto = new Gson().fromJson(jsonString, MessageResDto.class);

        verify(userService, times(1)).processLogin(userLoginReqDto.getUsername(), userLoginReqDto.getPassword());
        assertEquals(messageResDto.getCode(), ResponseCode.USER_IS_LOCKED);
        assertEquals(messageResDto.getMessage(), ResponseMessage.USER_IS_LOCKED);
    }

}
