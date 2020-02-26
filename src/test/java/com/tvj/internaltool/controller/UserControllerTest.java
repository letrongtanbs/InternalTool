package com.tvj.internaltool.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.tvj.internaltool.dto.req.ForgotPasswordReqDto;
import com.tvj.internaltool.dto.req.RecoverPasswordReqDto;
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

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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

    // ---------- /user/login START ----------

    @Test // Do not use org.junit.jupiter.api
    public void generateAuthenticationToken_success() throws Exception {

        UserLoginReqDto userLoginReqDto = new UserLoginReqDto();
        userLoginReqDto.setUsername("admin1");
        userLoginReqDto.setPassword("12345678");

        UserLoginResDtoDataDummy adminResDto = new UserLoginResDtoDataDummy();
        when(userService.processLogin(userLoginReqDto.getUsername(), userLoginReqDto.getPassword())).thenReturn(adminResDto.getAdminUserResDto1());

        MvcResult result = mockMvc.perform(post("/user/login")
                .content(new ObjectMapper().writeValueAsString(userLoginReqDto))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String jsonString = result.getResponse().getContentAsString();
        UserLoginResDto userLoginResDto = new Gson().fromJson(jsonString, UserLoginResDto.class);

        verify(userService, times(1)).processLogin(userLoginReqDto.getUsername(), userLoginReqDto.getPassword());
        assertEquals(userLoginResDto.getToken(), adminResDto.getAdminUserResDto1().getToken());
        assertEquals(userLoginResDto.getFirstName(), adminResDto.getAdminUserResDto1().getFirstName());
        assertEquals(userLoginResDto.getLastName(), adminResDto.getAdminUserResDto1().getLastName());
        assertEquals(userLoginResDto.getRoleName(), adminResDto.getAdminUserResDto1().getRoleName());
        assertEquals(userLoginResDto.isFirstTimeLogin(), adminResDto.getAdminUserResDto1().isFirstTimeLogin());
    }

    @Test // Do not use org.junit.jupiter.api
    public void generateAuthenticationToken_userIsUnauthorized() throws Exception {

        UserLoginReqDto userLoginReqDto = new UserLoginReqDto();
        userLoginReqDto.setUsername("admin1");
        userLoginReqDto.setPassword("12345678");

        when(userService.processLogin(userLoginReqDto.getUsername(), userLoginReqDto.getPassword())).thenReturn(ResponseCode.UNAUTHORIZED);

        MvcResult result = mockMvc.perform(post("/user/login")
                .content(new ObjectMapper().writeValueAsString(userLoginReqDto))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized())
                .andReturn();

        String jsonString = result.getResponse().getContentAsString();
        MessageResDto messageResDto = new Gson().fromJson(jsonString, MessageResDto.class);

        verify(userService, times(1)).processLogin(userLoginReqDto.getUsername(), userLoginReqDto.getPassword());
        assertEquals(messageResDto.getCode(), ResponseCode.UNAUTHORIZED);
        assertEquals(messageResDto.getMessage(), ResponseMessage.UNAUTHORIZED);
    }

    @Test // Do not use org.junit.jupiter.api
    public void generateAuthenticationToken_userIsLocked() throws Exception {

        UserLoginReqDto userLoginReqDto = new UserLoginReqDto();
        userLoginReqDto.setUsername("admin1");
        userLoginReqDto.setPassword("12345678");

        when(userService.processLogin(userLoginReqDto.getUsername(), userLoginReqDto.getPassword())).thenReturn(ResponseCode.USER_IS_LOCKED);

        MvcResult result = mockMvc.perform(post("/user/login")
                .content(new ObjectMapper().writeValueAsString(userLoginReqDto))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isLocked())
                .andReturn();

        String jsonString = result.getResponse().getContentAsString();
        MessageResDto messageResDto = new Gson().fromJson(jsonString, MessageResDto.class);

        verify(userService, times(1)).processLogin(userLoginReqDto.getUsername(), userLoginReqDto.getPassword());
        assertEquals(messageResDto.getCode(), ResponseCode.USER_IS_LOCKED);
        assertEquals(messageResDto.getMessage(), ResponseMessage.USER_IS_LOCKED);
    }

    // ---------- /user/login END ----------

    // ---------- /user/password-recover-send-request START ----------

    @Test
    public void forgotPasswordSendRequest_success() throws Exception {
        ForgotPasswordReqDto forgotPasswordReqDto = new ForgotPasswordReqDto();
        forgotPasswordReqDto.setUsername("admin1");

        when(userService.processForgotPassword(forgotPasswordReqDto.getUsername())).thenReturn(true);

        MvcResult result = mockMvc.perform(post("/user/password-recover-send-request")
                .content(new ObjectMapper().writeValueAsString(forgotPasswordReqDto))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String jsonString = result.getResponse().getContentAsString();
        MessageResDto messageResDto = new Gson().fromJson(jsonString, MessageResDto.class);

        verify(userService, times(1)).processForgotPassword(forgotPasswordReqDto.getUsername());
        assertEquals(messageResDto.getCode(), ResponseCode.SEND_MAIL_SUCCESS);
        assertEquals(messageResDto.getMessage(), ResponseMessage.SEND_MAIL_SUCCESS);
    }

    @Test
    public void forgotPasswordSendRequest_cannotSendRequest() throws Exception {
        ForgotPasswordReqDto forgotPasswordReqDto = new ForgotPasswordReqDto();
        forgotPasswordReqDto.setUsername("admin1");

        when(userService.processForgotPassword(forgotPasswordReqDto.getUsername())).thenReturn(false);

        MvcResult result = mockMvc.perform(post("/user/password-recover-send-request")
                .content(new ObjectMapper().writeValueAsString(forgotPasswordReqDto))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isRequestTimeout())
                .andReturn();

        String jsonString = result.getResponse().getContentAsString();
        MessageResDto messageResDto = new Gson().fromJson(jsonString, MessageResDto.class);

        verify(userService, times(1)).processForgotPassword(forgotPasswordReqDto.getUsername());
        assertEquals(messageResDto.getCode(), ResponseCode.SEND_MAIL_FAIL);
        assertEquals(messageResDto.getMessage(), ResponseMessage.SEND_MAIL_FAIL);
    }

    // ---------- /user/password-recover-send-request END ----------

    // ---------- /user/password-recover-confirm-token START ----------

    @Test
    public void forgotPasswordConfirmToken_success() throws Exception {
        String token = "token";

        when(userService.processConfirmForgotPasswordToken(token)).thenReturn(true);

        MvcResult result = mockMvc.perform(get("/user/password-recover-confirm-token")
                .param("token", token))
                .andExpect(status().isOk())
                .andReturn();

        String jsonString = result.getResponse().getContentAsString();
        MessageResDto messageResDto = new Gson().fromJson(jsonString, MessageResDto.class);

        verify(userService, times(1)).processConfirmForgotPasswordToken(token);
        assertEquals(messageResDto.getCode(), ResponseCode.TOKEN_IS_VALID);
        assertEquals(messageResDto.getMessage(), ResponseMessage.TOKEN_IS_VALID);
    }

    @Test
    public void forgotPasswordConfirmToken_verifyTokenFailed() throws Exception {
        String token = "token";

        when(userService.processConfirmForgotPasswordToken(token)).thenReturn(false);

        MvcResult result = mockMvc.perform(get("/user/password-recover-confirm-token")
                .param("token", token))
                .andExpect(status().isBadRequest())
                .andReturn();

        String jsonString = result.getResponse().getContentAsString();
        MessageResDto messageResDto = new Gson().fromJson(jsonString, MessageResDto.class);

        verify(userService, times(1)).processConfirmForgotPasswordToken(token);
        assertEquals(messageResDto.getCode(), ResponseCode.TOKEN_HAS_EXPIRED);
        assertEquals(messageResDto.getMessage(), ResponseMessage.TOKEN_HAS_EXPIRED);
    }

    // ---------- /user/password-recover-confirm-token END ----------

    // ---------- /user/password-recover-update-password START ----------

    @Test
    public void recoverPasswordUpdatePassword_success() throws Exception {
        RecoverPasswordReqDto recoverPasswordReqDto = new RecoverPasswordReqDto();
        recoverPasswordReqDto.setToken("Token");
        recoverPasswordReqDto.setNewPassword("newpassword");

        when(userService.processRecoverPassword(any(RecoverPasswordReqDto.class))).thenReturn(true);

        MvcResult result = mockMvc.perform(post("/user/password-recover-update-password")
                .content(new ObjectMapper().writeValueAsString(recoverPasswordReqDto))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String jsonString = result.getResponse().getContentAsString();
        MessageResDto messageResDto = new Gson().fromJson(jsonString, MessageResDto.class);

        verify(userService, times(1)).processRecoverPassword(any(RecoverPasswordReqDto.class));
        assertEquals(messageResDto.getCode(), ResponseCode.UPDATE_PASSWORD_SUCCESS);
        assertEquals(messageResDto.getMessage(), ResponseMessage.UPDATE_PASSWORD_SUCCESS);
    }

    @Test
    public void recoverPasswordUpdatePassword_cannotUpdatePassword() throws Exception {
        RecoverPasswordReqDto recoverPasswordReqDto = new RecoverPasswordReqDto();
        recoverPasswordReqDto.setToken("Token");
        recoverPasswordReqDto.setNewPassword("newpassword");

        when(userService.processRecoverPassword(any(RecoverPasswordReqDto.class))).thenReturn(false);

        MvcResult result = mockMvc.perform(post("/user/password-recover-update-password")
                .content(new ObjectMapper().writeValueAsString(recoverPasswordReqDto))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();

        String jsonString = result.getResponse().getContentAsString();
        MessageResDto messageResDto = new Gson().fromJson(jsonString, MessageResDto.class);

        verify(userService, times(1)).processRecoverPassword(any(RecoverPasswordReqDto.class));
        assertEquals(messageResDto.getCode(), ResponseCode.UPDATE_PASSWORD_FAIL);
        assertEquals(messageResDto.getMessage(), ResponseMessage.UPDATE_PASSWORD_FAIL);
    }

    // ---------- /user/password-recover-update-password END ----------
}
