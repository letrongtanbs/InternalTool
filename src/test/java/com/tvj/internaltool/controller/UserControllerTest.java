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

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMultipartHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.tvj.internaltool.dto.req.PasswordRecoverSendRequestReqDto;
import com.tvj.internaltool.dto.req.PasswordRecoverUpdatePasswordReqDto;
import com.tvj.internaltool.dto.req.UserLoginReqDto;
import com.tvj.internaltool.dto.req.UserSettingUpdatePasswordReqDto;
import com.tvj.internaltool.dto.req.UserSettingUpdateReqDto;
import com.tvj.internaltool.dto.res.MessageResDto;
import com.tvj.internaltool.dto.res.UserLoginResDto;
import com.tvj.internaltool.dto.res.UserSettingResDto;
import com.tvj.internaltool.dummy.dto.res.UserLoginResDtoDataDummy;
import com.tvj.internaltool.dummy.dto.res.UserSettingResDtoDataDummy;
import com.tvj.internaltool.enums.UserStatus;
import com.tvj.internaltool.service.UserService;
import com.tvj.internaltool.utils.CustomRestExceptionHandler;
import com.tvj.internaltool.utils.ResponseCode;
import com.tvj.internaltool.utils.ResponseMessage;

@RunWith(MockitoJUnitRunner.class)
public class UserControllerTest {

    @InjectMocks
    private UserController userController;

    @Mock
    private UserService userService;

    private MockMvc mockMvc;

    @Before // Execute before each test method
    public void setup() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(userController)
                .setControllerAdvice(new CustomRestExceptionHandler()) // add ControllerAdvice to controller test
                .build();
    }

    // ---------- /user/login START ----------

    @Test
    public void generateAuthenticationToken_success() throws Exception {
        // Value from client
        UserLoginReqDto userLoginReqDto = new UserLoginReqDto();
        userLoginReqDto.setUsername("admin1");
        userLoginReqDto.setPassword("12345678");

        UserLoginResDtoDataDummy adminResDto = new UserLoginResDtoDataDummy();
        when(userService.processLogin(userLoginReqDto.getUsername(), userLoginReqDto.getPassword()))
                .thenReturn(adminResDto.getAdminUserResDto1());

        MvcResult result = mockMvc
                .perform(post("/user/login").content(new ObjectMapper().writeValueAsString(userLoginReqDto))
                        .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();

        String jsonString = result.getResponse().getContentAsString();
        UserLoginResDto userLoginResDto = new Gson().fromJson(jsonString, UserLoginResDto.class);

        verify(userService, times(1)).processLogin(userLoginReqDto.getUsername(), userLoginReqDto.getPassword());
        assertEquals(userLoginResDto.getToken(), adminResDto.getAdminUserResDto1().getToken());
        assertEquals(userLoginResDto.getFirstName(), adminResDto.getAdminUserResDto1().getFirstName());
        assertEquals(userLoginResDto.getLastName(), adminResDto.getAdminUserResDto1().getLastName());
        assertEquals(userLoginResDto.getRoleName(), adminResDto.getAdminUserResDto1().getRoleName());
        assertEquals(userLoginResDto.isFirstTimeLogin(), adminResDto.getAdminUserResDto1().isFirstTimeLogin());
    }

    @Test
    public void generateAuthenticationToken_invalidHttpRequestMethodGET() throws Exception {
        mockMvc.perform(get("/user/login")).andExpect(status().isMethodNotAllowed()).andReturn();
    }

    @Test
    public void generateAuthenticationToken_invalidHttpRequestMethodPUT() throws Exception {
        mockMvc.perform(put("/user/login")).andExpect(status().isMethodNotAllowed()).andReturn();
    }

    @Test
    public void generateAuthenticationToken_invalidHttpRequestMethodPATCH() throws Exception {
        mockMvc.perform(patch("/user/login")).andExpect(status().isMethodNotAllowed()).andReturn();
    }

    @Test
    public void generateAuthenticationToken_invalidHttpRequestMethodDELETE() throws Exception {
        mockMvc.perform(delete("/user/login")).andExpect(status().isMethodNotAllowed()).andReturn();
    }

    @Test
    public void generateAuthenticationToken_usernameIsBlank() throws Exception {
        // Value from client
        UserLoginReqDto userLoginReqDto = new UserLoginReqDto();
        userLoginReqDto.setUsername("");
        userLoginReqDto.setPassword("12345678");

        mockMvc.perform(post("/user/login").content(new ObjectMapper().writeValueAsString(userLoginReqDto))
                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest()).andReturn();
    }

    @Test
    public void generateAuthenticationToken_usernameSizeExceedsMaximumLimit() throws Exception {
        // Value from client
        UserLoginReqDto userLoginReqDto = new UserLoginReqDto();
        userLoginReqDto.setUsername("a12345678901234567890");
        userLoginReqDto.setPassword("12345678");

        mockMvc.perform(post("/user/login").content(new ObjectMapper().writeValueAsString(userLoginReqDto))
                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest()).andReturn();
    }

    @Test
    public void generateAuthenticationToken_passwordIsBlank() throws Exception {
        // Value from client
        UserLoginReqDto userLoginReqDto = new UserLoginReqDto();
        userLoginReqDto.setUsername("admin1");
        userLoginReqDto.setPassword("");

        mockMvc.perform(post("/user/login").content(new ObjectMapper().writeValueAsString(userLoginReqDto))
                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest()).andReturn();
    }

    @Test
    public void generateAuthenticationToken_passwordSizeOutOfBounds() throws Exception {
        // Value from client
        UserLoginReqDto userLoginReqDto = new UserLoginReqDto();
        userLoginReqDto.setUsername("admin1");
        userLoginReqDto.setPassword("1234567");

        mockMvc.perform(post("/user/login").content(new ObjectMapper().writeValueAsString(userLoginReqDto))
                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest()).andReturn();
    }

    @Test
    public void generateAuthenticationToken_userIsUnauthorized() throws Exception {
        // Value from client
        UserLoginReqDto userLoginReqDto = new UserLoginReqDto();
        userLoginReqDto.setUsername("admin1");
        userLoginReqDto.setPassword("12345678");

        when(userService.processLogin(userLoginReqDto.getUsername(), userLoginReqDto.getPassword()))
                .thenReturn(ResponseCode.UNAUTHORIZED);

        MvcResult result = mockMvc
                .perform(post("/user/login").content(new ObjectMapper().writeValueAsString(userLoginReqDto))
                        .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized()).andReturn();

        String jsonString = result.getResponse().getContentAsString();
        MessageResDto messageResDto = new Gson().fromJson(jsonString, MessageResDto.class);

        verify(userService, times(1)).processLogin(userLoginReqDto.getUsername(), userLoginReqDto.getPassword());
        assertEquals(messageResDto.getCode(), ResponseCode.UNAUTHORIZED);
        assertEquals(messageResDto.getMessage(), ResponseMessage.UNAUTHORIZED);
    }

    @Test
    public void generateAuthenticationToken_userIsLocked() throws Exception {
        // Value from client
        UserLoginReqDto userLoginReqDto = new UserLoginReqDto();
        userLoginReqDto.setUsername("admin1");
        userLoginReqDto.setPassword("12345678");

        when(userService.processLogin(userLoginReqDto.getUsername(), userLoginReqDto.getPassword()))
                .thenReturn(ResponseCode.USER_IS_LOCKED);

        MvcResult result = mockMvc
                .perform(post("/user/login").content(new ObjectMapper().writeValueAsString(userLoginReqDto))
                        .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isLocked()).andReturn();

        String jsonString = result.getResponse().getContentAsString();
        MessageResDto messageResDto = new Gson().fromJson(jsonString, MessageResDto.class);

        verify(userService, times(1)).processLogin(userLoginReqDto.getUsername(), userLoginReqDto.getPassword());
        assertEquals(messageResDto.getCode(), ResponseCode.USER_IS_LOCKED);
        assertEquals(messageResDto.getMessage(), ResponseMessage.USER_IS_LOCKED);
    }

    // ---------- /user/login END ----------

    // ---------- /user/password-recover-send-request START ----------

    @Test
    public void passwordRecoverSendRequest_success() throws Exception {
        // Value from client
        PasswordRecoverSendRequestReqDto passwordRecoverSendRequestReqDto = new PasswordRecoverSendRequestReqDto();
        passwordRecoverSendRequestReqDto.setUsername("admin1");

        when(userService.processForgotPassword(passwordRecoverSendRequestReqDto.getUsername())).thenReturn(true);

        MvcResult result = mockMvc
                .perform(post("/user/password-recover-send-request")
                        .content(new ObjectMapper().writeValueAsString(passwordRecoverSendRequestReqDto))
                        .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();

        String jsonString = result.getResponse().getContentAsString();
        MessageResDto messageResDto = new Gson().fromJson(jsonString, MessageResDto.class);

        verify(userService, times(1)).processForgotPassword(passwordRecoverSendRequestReqDto.getUsername());
        assertEquals(messageResDto.getCode(), ResponseCode.SEND_MAIL_SUCCESS);
        assertEquals(messageResDto.getMessage(), ResponseMessage.SEND_MAIL_SUCCESS);
    }

    @Test
    public void passwordRecoverSendRequest_invalidHttpRequestMethodGET() throws Exception {
        mockMvc.perform(get("/user/password-recover-send-request")).andExpect(status().isMethodNotAllowed())
                .andReturn();
    }

    @Test
    public void passwordRecoverSendRequest_invalidHttpRequestMethodPUT() throws Exception {
        mockMvc.perform(put("/user/password-recover-send-request")).andExpect(status().isMethodNotAllowed())
                .andReturn();
    }

    @Test
    public void passwordRecoverSendRequest_invalidHttpRequestMethodPATCH() throws Exception {
        mockMvc.perform(patch("/user/password-recover-send-request")).andExpect(status().isMethodNotAllowed())
                .andReturn();
    }

    @Test
    public void passwordRecoverSendRequest_invalidHttpRequestMethodDELETE() throws Exception {
        mockMvc.perform(delete("/user/password-recover-send-request")).andExpect(status().isMethodNotAllowed())
                .andReturn();
    }

    @Test
    public void passwordRecoverSendRequest_usernameIsBlank() throws Exception {
        // Value from client
        PasswordRecoverSendRequestReqDto passwordRecoverSendRequestReqDto = new PasswordRecoverSendRequestReqDto();
        passwordRecoverSendRequestReqDto.setUsername("");

        mockMvc.perform(post("/user/password-recover-send-request")
                .content(new ObjectMapper().writeValueAsString(passwordRecoverSendRequestReqDto))
                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest()).andReturn();
    }

    @Test
    public void passwordRecoverSendRequest_usernameSizeExceedsMaximumLimit() throws Exception {
        // Value from client
        PasswordRecoverSendRequestReqDto passwordRecoverSendRequestReqDto = new PasswordRecoverSendRequestReqDto();
        passwordRecoverSendRequestReqDto.setUsername("a12345678901234567890");

        mockMvc.perform(post("/user/password-recover-send-request")
                .content(new ObjectMapper().writeValueAsString(passwordRecoverSendRequestReqDto))
                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest()).andReturn();
    }

    @Test
    public void passwordRecoverSendRequest_cannotSendRequest() throws Exception {
        // Value from client
        PasswordRecoverSendRequestReqDto passwordRecoverSendRequestReqDto = new PasswordRecoverSendRequestReqDto();
        passwordRecoverSendRequestReqDto.setUsername("admin1");

        when(userService.processForgotPassword(passwordRecoverSendRequestReqDto.getUsername())).thenReturn(false);

        MvcResult result = mockMvc
                .perform(post("/user/password-recover-send-request")
                        .content(new ObjectMapper().writeValueAsString(passwordRecoverSendRequestReqDto))
                        .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isRequestTimeout()).andReturn();

        String jsonString = result.getResponse().getContentAsString();
        MessageResDto messageResDto = new Gson().fromJson(jsonString, MessageResDto.class);

        verify(userService, times(1)).processForgotPassword(passwordRecoverSendRequestReqDto.getUsername());
        assertEquals(messageResDto.getCode(), ResponseCode.SEND_MAIL_FAILED);
        assertEquals(messageResDto.getMessage(), ResponseMessage.SEND_MAIL_FAILED);
    }

    // ---------- /user/password-recover-send-request END ----------

    // ---------- /user/password-recover-confirm-token START ----------

    @Test
    public void passwordRecoverConfirmToken_success() throws Exception {
        // Value from client
        String token = "token";

        when(userService.processConfirmForgotPasswordToken(token)).thenReturn(true);

        MvcResult result = mockMvc.perform(get("/user/password-recover-confirm-token").param("token", token))
                .andExpect(status().isOk()).andReturn();

        String jsonString = result.getResponse().getContentAsString();
        MessageResDto messageResDto = new Gson().fromJson(jsonString, MessageResDto.class);

        verify(userService, times(1)).processConfirmForgotPasswordToken(token);
        assertEquals(messageResDto.getCode(), ResponseCode.TOKEN_IS_VALID);
        assertEquals(messageResDto.getMessage(), ResponseMessage.TOKEN_IS_VALID);
    }

    @Test
    public void passwordRecoverConfirmToken_invalidHttpRequestMethodPOST() throws Exception {
        mockMvc.perform(post("/user/password-recover-confirm-token")).andExpect(status().isMethodNotAllowed())
                .andReturn();
    }

    @Test
    public void passwordRecoverConfirmToken_invalidHttpRequestMethodPUT() throws Exception {
        mockMvc.perform(put("/user/password-recover-confirm-token")).andExpect(status().isMethodNotAllowed())
                .andReturn();
    }

    @Test
    public void passwordRecoverConfirmToken_invalidHttpRequestMethodPATCH() throws Exception {
        mockMvc.perform(patch("/user/password-recover-confirm-token")).andExpect(status().isMethodNotAllowed())
                .andReturn();
    }

    @Test
    public void passwordRecoverConfirmToken_invalidHttpRequestMethodDELETE() throws Exception {
        mockMvc.perform(delete("/user/password-recover-confirm-token")).andExpect(status().isMethodNotAllowed())
                .andReturn();
    }

    @Test
    public void passwordRecoverConfirmToken_verifyTokenFailed() throws Exception {
        // Value from client
        String token = "token";

        when(userService.processConfirmForgotPasswordToken(token)).thenReturn(false);

        MvcResult result = mockMvc.perform(get("/user/password-recover-confirm-token").param("token", token))
                .andExpect(status().isBadRequest()).andReturn();

        String jsonString = result.getResponse().getContentAsString();
        MessageResDto messageResDto = new Gson().fromJson(jsonString, MessageResDto.class);

        verify(userService, times(1)).processConfirmForgotPasswordToken(token);
        assertEquals(messageResDto.getCode(), ResponseCode.TOKEN_HAS_EXPIRED);
        assertEquals(messageResDto.getMessage(), ResponseMessage.TOKEN_HAS_EXPIRED);
    }

    // ---------- /user/password-recover-confirm-token END ----------

    // ---------- /user/password-recover-update-password START ----------

    @Test
    public void passwordRecoverUpdatePassword_success() throws Exception {
        // Value from client
        PasswordRecoverUpdatePasswordReqDto passwordRecoverUpdatePasswordReqDto = new PasswordRecoverUpdatePasswordReqDto();
        passwordRecoverUpdatePasswordReqDto.setToken("Token");
        passwordRecoverUpdatePasswordReqDto.setNewPassword("newpassword");

        when(userService.processRecoverPassword(any(PasswordRecoverUpdatePasswordReqDto.class))).thenReturn(true);

        MvcResult result = mockMvc
                .perform(post("/user/password-recover-update-password")
                        .content(new ObjectMapper().writeValueAsString(passwordRecoverUpdatePasswordReqDto))
                        .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();

        String jsonString = result.getResponse().getContentAsString();
        MessageResDto messageResDto = new Gson().fromJson(jsonString, MessageResDto.class);

        verify(userService, times(1)).processRecoverPassword(any(PasswordRecoverUpdatePasswordReqDto.class));
        assertEquals(messageResDto.getCode(), ResponseCode.UPDATE_PASSWORD_SUCCESS);
        assertEquals(messageResDto.getMessage(), ResponseMessage.UPDATE_PASSWORD_SUCCESS);
    }

    @Test
    public void passwordRecoverUpdatePassword_invalidHttpRequestMethodGET() throws Exception {
        mockMvc.perform(get("/user/password-recover-update-password")).andExpect(status().isMethodNotAllowed())
                .andReturn();
    }

    @Test
    public void passwordRecoverUpdatePassword_invalidHttpRequestMethodPUT() throws Exception {
        mockMvc.perform(put("/user/password-recover-update-password")).andExpect(status().isMethodNotAllowed())
                .andReturn();
    }

    @Test
    public void passwordRecoverUpdatePassword_invalidHttpRequestMethodPATCH() throws Exception {
        mockMvc.perform(patch("/user/password-recover-update-password")).andExpect(status().isMethodNotAllowed())
                .andReturn();
    }

    @Test
    public void passwordRecoverUpdatePassword_invalidHttpRequestMethodDELETE() throws Exception {
        mockMvc.perform(delete("/user/password-recover-update-password")).andExpect(status().isMethodNotAllowed())
                .andReturn();
    }

    @Test
    public void passwordRecoverUpdatePassword_tokenIsBlank() throws Exception {
        // Value from client
        PasswordRecoverUpdatePasswordReqDto passwordRecoverUpdatePasswordReqDto = new PasswordRecoverUpdatePasswordReqDto();
        passwordRecoverUpdatePasswordReqDto.setToken("");
        passwordRecoverUpdatePasswordReqDto.setNewPassword("newpassword");

        mockMvc.perform(post("/user/password-recover-update-password")
                .content(new ObjectMapper().writeValueAsString(passwordRecoverUpdatePasswordReqDto))
                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest()).andReturn();
    }

    @Test
    public void passwordRecoverUpdatePassword_newPasswordIsBlank() throws Exception {
        // Value from client
        PasswordRecoverUpdatePasswordReqDto passwordRecoverUpdatePasswordReqDto = new PasswordRecoverUpdatePasswordReqDto();
        passwordRecoverUpdatePasswordReqDto.setToken("Token");
        passwordRecoverUpdatePasswordReqDto.setNewPassword("");

        mockMvc.perform(post("/user/password-recover-update-password")
                .content(new ObjectMapper().writeValueAsString(passwordRecoverUpdatePasswordReqDto))
                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest()).andReturn();
    }

    @Test
    public void passwordRecoverUpdatePassword_newPasswordSizeOutOfBounds() throws Exception {
        // Value from client
        PasswordRecoverUpdatePasswordReqDto passwordRecoverUpdatePasswordReqDto = new PasswordRecoverUpdatePasswordReqDto();
        passwordRecoverUpdatePasswordReqDto.setToken("Token");
        passwordRecoverUpdatePasswordReqDto.setNewPassword("1234567");

        mockMvc.perform(post("/user/password-recover-update-password")
                .content(new ObjectMapper().writeValueAsString(passwordRecoverUpdatePasswordReqDto))
                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest()).andReturn();
    }

    @Test
    public void passwordRecoverUpdatePassword_cannotUpdatePassword() throws Exception {
        // Value from client
        PasswordRecoverUpdatePasswordReqDto passwordRecoverUpdatePasswordReqDto = new PasswordRecoverUpdatePasswordReqDto();
        passwordRecoverUpdatePasswordReqDto.setToken("Token");
        passwordRecoverUpdatePasswordReqDto.setNewPassword("newpassword");

        when(userService.processRecoverPassword(any(PasswordRecoverUpdatePasswordReqDto.class))).thenReturn(false);

        MvcResult result = mockMvc
                .perform(post("/user/password-recover-update-password")
                        .content(new ObjectMapper().writeValueAsString(passwordRecoverUpdatePasswordReqDto))
                        .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest()).andReturn();

        String jsonString = result.getResponse().getContentAsString();
        MessageResDto messageResDto = new Gson().fromJson(jsonString, MessageResDto.class);

        verify(userService, times(1)).processRecoverPassword(any(PasswordRecoverUpdatePasswordReqDto.class));
        assertEquals(messageResDto.getCode(), ResponseCode.UPDATE_PASSWORD_FAILED);
        assertEquals(messageResDto.getMessage(), ResponseMessage.UPDATE_PASSWORD_FAILED);
    }

    // ---------- /user/password-recover-update-password END ----------

    // ---------- /user/user-setting-get-info START ----------

    @Test
    public void getUserSetting_success() throws Exception {
        UserSettingResDtoDataDummy userSettingResDtoDataDummy = new UserSettingResDtoDataDummy();
        UserSettingResDto userSettingResDto = userSettingResDtoDataDummy.getAdminUserSettingResDto1();

        when(userService.getUserSetting()).thenReturn(userSettingResDto);

        mockMvc.perform(get("/user/user-setting-get-info")).andExpect(status().isOk()).andReturn();

        verify(userService, times(1)).getUserSetting();
    }

    @Test
    public void getUserSetting_success_withEmptyData() throws Exception {
        when(userService.getUserSetting()).thenReturn(null);

        MvcResult result = mockMvc.perform(get("/user/user-setting-get-info")).andExpect(status().isBadRequest())
                .andReturn();

        String jsonString = result.getResponse().getContentAsString();
        MessageResDto messageResDto = new Gson().fromJson(jsonString, MessageResDto.class);

        verify(userService, times(1)).getUserSetting();
        assertEquals(messageResDto.getCode(), ResponseCode.GET_USER_SETTING_FAILED);
        assertEquals(messageResDto.getMessage(), ResponseMessage.GET_USER_SETTING_FAILED);
    }

    @Test
    public void getUserSetting_invalidHttpRequestMethodPOST() throws Exception {
        mockMvc.perform(post("/user/user-setting-get-info")).andExpect(status().isMethodNotAllowed()).andReturn();
    }

    @Test
    public void getUserSetting_invalidHttpRequestMethodPUT() throws Exception {
        mockMvc.perform(put("/user/user-setting-get-info")).andExpect(status().isMethodNotAllowed()).andReturn();
    }

    @Test
    public void getUserSetting_invalidHttpRequestMethodPATCH() throws Exception {
        mockMvc.perform(patch("/user/user-setting-get-info")).andExpect(status().isMethodNotAllowed()).andReturn();
    }

    @Test
    public void getUserSetting_invalidHttpRequestMethodDELETE() throws Exception {
        mockMvc.perform(delete("/user/user-setting-get-info")).andExpect(status().isMethodNotAllowed()).andReturn();
    }

    // ---------- /user/user-setting-get-info END ----------

    // ---------- /user/user-setting-update-info START ----------

    @Test
    public void updateUserSetting_success() throws Exception {
        // Value from client
        UserSettingUpdateReqDto userSettingUpdateReqDto = new UserSettingUpdateReqDto();
        userSettingUpdateReqDto.setTeamId("1");
        userSettingUpdateReqDto.setAddress("1 Some Where");
        userSettingUpdateReqDto.setPhone("1234567890");
        userSettingUpdateReqDto.setCountryId("1");
        userSettingUpdateReqDto.setLanguageId("1");
        userSettingUpdateReqDto.setStatusId(UserStatus.AVAILABLE.getStatus());

        UserSettingResDtoDataDummy userSettingResDtoDataDummy = new UserSettingResDtoDataDummy();
        UserSettingResDto userSettingResDto = userSettingResDtoDataDummy.getAdminUserSettingResDto1();

        when(userService.updateUserSetting(any(UserSettingUpdateReqDto.class))).thenReturn(userSettingResDto);

        mockMvc.perform(put("/user/user-setting-update-info")
                .content(new ObjectMapper().writeValueAsString(userSettingUpdateReqDto))
                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
                .andReturn();

        verify(userService, times(1)).updateUserSetting(any(UserSettingUpdateReqDto.class));
    }

    @Test
    public void updateUserSetting_invalidHttpRequestMethodGET() throws Exception {
        mockMvc.perform(get("/user/user-setting-update-info")).andExpect(status().isMethodNotAllowed()).andReturn();
    }

    @Test
    public void updateUserSetting_invalidHttpRequestMethodPOST() throws Exception {
        mockMvc.perform(post("/user/user-setting-update-info")).andExpect(status().isMethodNotAllowed()).andReturn();
    }

    @Test
    public void updateUserSetting_invalidHttpRequestMethodPATCH() throws Exception {
        mockMvc.perform(patch("/user/user-setting-update-info")).andExpect(status().isMethodNotAllowed()).andReturn();
    }

    @Test
    public void updateUserSetting_invalidHttpRequestMethodDELETE() throws Exception {
        mockMvc.perform(delete("/user/user-setting-update-info")).andExpect(status().isMethodNotAllowed()).andReturn();
    }

    @Test
    public void updateUserSetting_teamIdSizeOutOfBounds() throws Exception {
        // Value from client
        UserSettingUpdateReqDto userSettingUpdateReqDto = new UserSettingUpdateReqDto();
        userSettingUpdateReqDto.setTeamId("a1234567890123456789012345678901234567890");
        userSettingUpdateReqDto.setAddress("1 Some Where");
        userSettingUpdateReqDto.setPhone("1234567890");
        userSettingUpdateReqDto.setCountryId("1");
        userSettingUpdateReqDto.setLanguageId("1");
        userSettingUpdateReqDto.setStatusId(UserStatus.AVAILABLE.getStatus());

        mockMvc.perform(put("/user/user-setting-update-info")
                .content(new ObjectMapper().writeValueAsString(userSettingUpdateReqDto))
                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest()).andReturn();
    }

    @Test
    public void updateUserSetting_addressSizeExceedsMaximumLimit() throws Exception {
        // Value from client
        UserSettingUpdateReqDto userSettingUpdateReqDto = new UserSettingUpdateReqDto();
        userSettingUpdateReqDto.setTeamId("1");
        userSettingUpdateReqDto.setAddress(
                "12345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901");
        userSettingUpdateReqDto.setPhone("1234567890");
        userSettingUpdateReqDto.setCountryId("1");
        userSettingUpdateReqDto.setLanguageId("1");
        userSettingUpdateReqDto.setStatusId(UserStatus.AVAILABLE.getStatus());

        mockMvc.perform(put("/user/user-setting-update-info")
                .content(new ObjectMapper().writeValueAsString(userSettingUpdateReqDto))
                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest()).andReturn();
    }

    @Test
    public void updateUserSetting_phoneSizeOutOfBounds() throws Exception {
        // Value from client
        UserSettingUpdateReqDto userSettingUpdateReqDto = new UserSettingUpdateReqDto();
        userSettingUpdateReqDto.setTeamId("1");
        userSettingUpdateReqDto.setAddress("1 Some Where");
        userSettingUpdateReqDto.setPhone("1234567");
        userSettingUpdateReqDto.setCountryId("1");
        userSettingUpdateReqDto.setLanguageId("1");
        userSettingUpdateReqDto.setStatusId(UserStatus.AVAILABLE.getStatus());

        mockMvc.perform(put("/user/user-setting-update-info")
                .content(new ObjectMapper().writeValueAsString(userSettingUpdateReqDto))
                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest()).andReturn();
    }

    @Test
    public void updateUserSetting_countryIdSizeOutOfBounds() throws Exception {
        // Value from client
        UserSettingUpdateReqDto userSettingUpdateReqDto = new UserSettingUpdateReqDto();
        userSettingUpdateReqDto.setTeamId("1");
        userSettingUpdateReqDto.setAddress("1 Some Where");
        userSettingUpdateReqDto.setPhone("12345678");
        userSettingUpdateReqDto.setCountryId("12345678901234567890123456789012345678901");
        userSettingUpdateReqDto.setLanguageId("1");
        userSettingUpdateReqDto.setStatusId(UserStatus.AVAILABLE.getStatus());

        mockMvc.perform(put("/user/user-setting-update-info")
                .content(new ObjectMapper().writeValueAsString(userSettingUpdateReqDto))
                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest()).andReturn();
    }

    @Test
    public void updateUserSetting_languageIdIsBlank() throws Exception {
        // Value from client
        UserSettingUpdateReqDto userSettingUpdateReqDto = new UserSettingUpdateReqDto();
        userSettingUpdateReqDto.setTeamId("1");
        userSettingUpdateReqDto.setAddress("1 Some Where");
        userSettingUpdateReqDto.setPhone("12345678");
        userSettingUpdateReqDto.setCountryId("1");
        userSettingUpdateReqDto.setLanguageId("");
        userSettingUpdateReqDto.setStatusId(UserStatus.AVAILABLE.getStatus());

        mockMvc.perform(put("/user/user-setting-update-info")
                .content(new ObjectMapper().writeValueAsString(userSettingUpdateReqDto))
                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest()).andReturn();
    }

    @Test
    public void updateUserSetting_languageIdSizeOutOfBounds() throws Exception {
        // Value from client
        UserSettingUpdateReqDto userSettingUpdateReqDto = new UserSettingUpdateReqDto();
        userSettingUpdateReqDto.setTeamId("1");
        userSettingUpdateReqDto.setAddress("1 Some Where");
        userSettingUpdateReqDto.setPhone("12345678");
        userSettingUpdateReqDto.setCountryId("1");
        userSettingUpdateReqDto.setLanguageId("12345678901234567890123456789012345678901");
        userSettingUpdateReqDto.setStatusId(UserStatus.AVAILABLE.getStatus());

        mockMvc.perform(put("/user/user-setting-update-info")
                .content(new ObjectMapper().writeValueAsString(userSettingUpdateReqDto))
                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest()).andReturn();
    }

    @Test
    public void updateUserSetting_statusIdIsBlank() throws Exception {
        // Value from client
        UserSettingUpdateReqDto userSettingUpdateReqDto = new UserSettingUpdateReqDto();
        userSettingUpdateReqDto.setTeamId("1");
        userSettingUpdateReqDto.setAddress("1 Some Where");
        userSettingUpdateReqDto.setPhone("12345678");
        userSettingUpdateReqDto.setCountryId("1");
        userSettingUpdateReqDto.setLanguageId("1");
        userSettingUpdateReqDto.setStatusId("");

        mockMvc.perform(put("/user/user-setting-update-info")
                .content(new ObjectMapper().writeValueAsString(userSettingUpdateReqDto))
                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest()).andReturn();
    }

    @Test
    public void updateUserSetting_statusIsInvalid() throws Exception {
        // Value from client
        UserSettingUpdateReqDto userSettingUpdateReqDto = new UserSettingUpdateReqDto();
        userSettingUpdateReqDto.setTeamId("1");
        userSettingUpdateReqDto.setAddress("1 Some Where");
        userSettingUpdateReqDto.setPhone("12345678");
        userSettingUpdateReqDto.setCountryId("1");
        userSettingUpdateReqDto.setLanguageId("1");
        userSettingUpdateReqDto.setStatusId("10");

        mockMvc.perform(put("/user/user-setting-update-info")
                .content(new ObjectMapper().writeValueAsString(userSettingUpdateReqDto))
                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest()).andReturn();
    }

    @Test
    public void updateUserSetting_cannotUpdateUserSetting() throws Exception {
        // Value from client
        UserSettingUpdateReqDto userSettingUpdateReqDto = new UserSettingUpdateReqDto();
        userSettingUpdateReqDto.setTeamId("1");
        userSettingUpdateReqDto.setAddress("1 Some Where");
        userSettingUpdateReqDto.setPhone("1234567890");
        userSettingUpdateReqDto.setCountryId("1");
        userSettingUpdateReqDto.setLanguageId("Vietnamese");
        userSettingUpdateReqDto.setStatusId(UserStatus.AVAILABLE.getStatus());

        when(userService.updateUserSetting(any(UserSettingUpdateReqDto.class))).thenReturn(null);

        MvcResult result = mockMvc
                .perform(put("/user/user-setting-update-info")
                        .content(new ObjectMapper().writeValueAsString(userSettingUpdateReqDto))
                        .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest()).andReturn();

        String jsonString = result.getResponse().getContentAsString();
        MessageResDto messageResDto = new Gson().fromJson(jsonString, MessageResDto.class);

        verify(userService, times(1)).updateUserSetting(any(UserSettingUpdateReqDto.class));
        assertEquals(messageResDto.getCode(), ResponseCode.UPDATE_USER_SETTING_FAILED);
        assertEquals(messageResDto.getMessage(), ResponseMessage.UPDATE_USER_SETTING_FAILED);
    }

    // ---------- /user/user-setting-update-info END ----------

    // ---------- /user/user-setting-update-password START ----------

    @Test
    public void updatePassword_success() throws Exception {
        // Value from client
        UserSettingUpdatePasswordReqDto userSettingUpdatePasswordReqDto = new UserSettingUpdatePasswordReqDto();
        userSettingUpdatePasswordReqDto.setOldPassword("12345678");
        userSettingUpdatePasswordReqDto.setNewPassword("87654321");

        when(userService.updatePassword(any(UserSettingUpdatePasswordReqDto.class))).thenReturn(true);

        MvcResult result = mockMvc
                .perform(patch("/user/user-setting-update-password")
                        .content(new ObjectMapper().writeValueAsString(userSettingUpdatePasswordReqDto))
                        .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();

        String jsonString = result.getResponse().getContentAsString();
        MessageResDto messageResDto = new Gson().fromJson(jsonString, MessageResDto.class);

        verify(userService, times(1)).updatePassword(any(UserSettingUpdatePasswordReqDto.class));
        assertEquals(messageResDto.getCode(), ResponseCode.UPDATE_PASSWORD_SUCCESS);
        assertEquals(messageResDto.getMessage(), ResponseMessage.UPDATE_PASSWORD_SUCCESS);
    }

    @Test
    public void updatePassword_invalidHttpRequestMethodGET() throws Exception {
        mockMvc.perform(get("/user/user-setting-update-password")).andExpect(status().isMethodNotAllowed()).andReturn();
    }

    @Test
    public void updatePassword_invalidHttpRequestMethodPOST() throws Exception {
        mockMvc.perform(post("/user/user-setting-update-password")).andExpect(status().isMethodNotAllowed())
                .andReturn();
    }

    @Test
    public void updatePassword_invalidHttpRequestMethodPUT() throws Exception {
        mockMvc.perform(put("/user/user-setting-update-password")).andExpect(status().isMethodNotAllowed()).andReturn();
    }

    @Test
    public void updatePassword_invalidHttpRequestMethodDELETE() throws Exception {
        mockMvc.perform(delete("/user/user-setting-update-password")).andExpect(status().isMethodNotAllowed())
                .andReturn();
    }

    @Test
    public void updatePassword_oldPasswordIsBlank() throws Exception {
        // Value from client
        UserSettingUpdatePasswordReqDto userSettingUpdatePasswordReqDto = new UserSettingUpdatePasswordReqDto();
        userSettingUpdatePasswordReqDto.setOldPassword("");
        userSettingUpdatePasswordReqDto.setNewPassword("12345678");

        mockMvc.perform(patch("/user/user-setting-update-password")
                .content(new ObjectMapper().writeValueAsString(userSettingUpdatePasswordReqDto))
                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest()).andReturn();
    }

    @Test
    public void updatePassword_oldPasswordSizeOutOfBounds() throws Exception {
        // Value from client
        UserSettingUpdatePasswordReqDto userSettingUpdatePasswordReqDto = new UserSettingUpdatePasswordReqDto();
        userSettingUpdatePasswordReqDto.setOldPassword("1234567");
        userSettingUpdatePasswordReqDto.setNewPassword("12345678");

        mockMvc.perform(patch("/user/user-setting-update-password")
                .content(new ObjectMapper().writeValueAsString(userSettingUpdatePasswordReqDto))
                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest()).andReturn();
    }

    @Test
    public void updatePassword_newPasswordIsBlank() throws Exception {
        // Value from client
        UserSettingUpdatePasswordReqDto userSettingUpdatePasswordReqDto = new UserSettingUpdatePasswordReqDto();
        userSettingUpdatePasswordReqDto.setOldPassword("12345678");
        userSettingUpdatePasswordReqDto.setNewPassword("");

        mockMvc.perform(patch("/user/user-setting-update-password")
                .content(new ObjectMapper().writeValueAsString(userSettingUpdatePasswordReqDto))
                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest()).andReturn();
    }

    @Test
    public void updatePassword_newPasswordSizeOutOfBounds() throws Exception {
        // Value from client
        UserSettingUpdatePasswordReqDto userSettingUpdatePasswordReqDto = new UserSettingUpdatePasswordReqDto();
        userSettingUpdatePasswordReqDto.setOldPassword("12345678");
        userSettingUpdatePasswordReqDto.setNewPassword("1234567");

        mockMvc.perform(patch("/user/user-setting-update-password")
                .content(new ObjectMapper().writeValueAsString(userSettingUpdatePasswordReqDto))
                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest()).andReturn();
    }

    @Test
    public void updatePassword_cannotUpdatePassword() throws Exception {
        // Value from client
        UserSettingUpdatePasswordReqDto userSettingUpdatePasswordReqDto = new UserSettingUpdatePasswordReqDto();
        userSettingUpdatePasswordReqDto.setOldPassword("12345678");
        userSettingUpdatePasswordReqDto.setNewPassword("87654321");

        when(userService.updatePassword(any(UserSettingUpdatePasswordReqDto.class))).thenReturn(false);

        MvcResult result = mockMvc
                .perform(patch("/user/user-setting-update-password")
                        .content(new ObjectMapper().writeValueAsString(userSettingUpdatePasswordReqDto))
                        .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest()).andReturn();

        String jsonString = result.getResponse().getContentAsString();
        MessageResDto messageResDto = new Gson().fromJson(jsonString, MessageResDto.class);

        verify(userService, times(1)).updatePassword(any(UserSettingUpdatePasswordReqDto.class));
        assertEquals(messageResDto.getCode(), ResponseCode.UPDATE_PASSWORD_FAILED);
        assertEquals(messageResDto.getMessage(), ResponseMessage.UPDATE_PASSWORD_FAILED);
    }

    // ---------- /user/user-setting-update-password END ----------

    // ---------- /user/upload-avatar START ----------

    @Test
    public void uploadAvatar_success() throws Exception {
        // Value from client
        MockMultipartFile image = new MockMultipartFile("file", "cv.jpg", "image/jpeg",
                "{\"image\": \"F:\\TVJ\\file_upload\\test\\cv.jpg\"}".getBytes());

        when(userService.uploadAvatar(any(MultipartFile.class))).thenReturn("output/file/path");

        MockMultipartHttpServletRequestBuilder builder = MockMvcRequestBuilders.multipart("/user/upload-avatar");

        mockMvc.perform(builder.file(image)).andExpect(status().isOk()).andReturn();

        verify(userService, times(1)).uploadAvatar(any(MultipartFile.class));
    }
    
    @Test
    public void uploadAvatar_invalidHttpRequestMethodGET() throws Exception {
        mockMvc.perform(get("/user/upload-avatar")).andExpect(status().isMethodNotAllowed()).andReturn();
    }
    
    @Test
    public void uploadAvatar_invalidHttpRequestMethodPUT() throws Exception {
        mockMvc.perform(put("/user/upload-avatar")).andExpect(status().isMethodNotAllowed()).andReturn();
    }
    
    @Test
    public void uploadAvatar_invalidHttpRequestMethodPATCH() throws Exception {
        mockMvc.perform(patch("/user/upload-avatar")).andExpect(status().isMethodNotAllowed()).andReturn();
    }
    
    @Test
    public void uploadAvatar_invalidHttpRequestMethodDELETE() throws Exception {
        mockMvc.perform(delete("/user/upload-avatar")).andExpect(status().isMethodNotAllowed()).andReturn();
    }

    @Test
    public void uploadAvatar_cannotUploadAvatar() throws Exception {
        // Value from client
        MockMultipartFile image = new MockMultipartFile("file", "cv.jpg", "image/jpeg",
                "{\"image\": \"F:\\TVJ\\file_upload\\test\\cv.jpg\"}".getBytes());

        when(userService.uploadAvatar(any(MultipartFile.class))).thenReturn("");

        MockMultipartHttpServletRequestBuilder builder = MockMvcRequestBuilders.multipart("/user/upload-avatar");

        MvcResult result = mockMvc.perform(builder.file(image)).andExpect(status().isBadRequest()).andReturn();

        String jsonString = result.getResponse().getContentAsString();
        MessageResDto messageResDto = new Gson().fromJson(jsonString, MessageResDto.class);

        verify(userService, times(1)).uploadAvatar(any(MultipartFile.class));
        assertEquals(messageResDto.getCode(), ResponseCode.UPDATE_AVATAR_FAILED);
        assertEquals(messageResDto.getMessage(), ResponseMessage.UPDATE_AVATAR_FAILED);
    }

    // ---------- /user/upload-avatar END ----------

    // ---------- /user/remove-avatar START ----------

    @Test
    public void removeAvatar_success() throws Exception {
        when(userService.removeAvatar()).thenReturn(true);

        MvcResult result = mockMvc.perform(delete("/user/remove-avatar")).andExpect(status().isOk()).andReturn();

        String jsonString = result.getResponse().getContentAsString();
        MessageResDto messageResDto = new Gson().fromJson(jsonString, MessageResDto.class);

        verify(userService, times(1)).removeAvatar();
        assertEquals(messageResDto.getCode(), ResponseCode.REMOVE_AVATAR_SUCCESS);
        assertEquals(messageResDto.getMessage(), ResponseMessage.REMOVE_AVATAR_SUCCESS);
    }
    
    @Test
    public void removeAvatar_invalidHttpRequestMethodGET() throws Exception {
        mockMvc.perform(get("/user/remove-avatar")).andExpect(status().isMethodNotAllowed()).andReturn();
    }
    
    @Test
    public void removeAvatar_invalidHttpRequestMethodPOST() throws Exception {
        mockMvc.perform(post("/user/remove-avatar")).andExpect(status().isMethodNotAllowed()).andReturn();
    }
    
    @Test
    public void removeAvatar_invalidHttpRequestMethodPUT() throws Exception {
        mockMvc.perform(put("/user/remove-avatar")).andExpect(status().isMethodNotAllowed()).andReturn();
    }
    
    @Test
    public void removeAvatar_invalidHttpRequestMethodPATCH() throws Exception {
        mockMvc.perform(patch("/user/remove-avatar")).andExpect(status().isMethodNotAllowed()).andReturn();
    }

    @Test
    public void removeAvatar_cannotRemoveAvatar() throws Exception {
        when(userService.removeAvatar()).thenReturn(false);

        MvcResult result = mockMvc.perform(delete("/user/remove-avatar")).andExpect(status().isBadRequest())
                .andReturn();

        String jsonString = result.getResponse().getContentAsString();
        MessageResDto messageResDto = new Gson().fromJson(jsonString, MessageResDto.class);

        verify(userService, times(1)).removeAvatar();
        assertEquals(messageResDto.getCode(), ResponseCode.REMOVE_AVATAR_FAILED);
        assertEquals(messageResDto.getMessage(), ResponseMessage.REMOVE_AVATAR_FAILED);
    }

    // ---------- /user/remove-avatar END ----------

    // ---------- /user/save-last-logout START ----------

    @Test
    public void saveLastLogout_success() throws Exception {
        when(userService.saveLastLogout()).thenReturn(true);

        MvcResult result = mockMvc.perform(get("/user/save-last-logout")).andExpect(status().isOk()).andReturn();

        String jsonString = result.getResponse().getContentAsString();
        MessageResDto messageResDto = new Gson().fromJson(jsonString, MessageResDto.class);

        verify(userService, times(1)).saveLastLogout();
        assertEquals(messageResDto.getCode(), ResponseCode.SAVE_LAST_LOGOUT_SUCCESS);
        assertEquals(messageResDto.getMessage(), ResponseMessage.SAVE_LAST_LOGOUT_SUCCESS);
    }

    @Test
    public void saveLastLogout_invalidHttpRequestMethodPOST() throws Exception {
        mockMvc.perform(post("/user/save-last-logout")).andExpect(status().isMethodNotAllowed()).andReturn();
    }

    @Test
    public void saveLastLogout_invalidHttpRequestMethodPUT() throws Exception {
        mockMvc.perform(put("/user/save-last-logout")).andExpect(status().isMethodNotAllowed()).andReturn();
    }

    @Test
    public void saveLastLogout_invalidHttpRequestMethodPATCH() throws Exception {
        mockMvc.perform(patch("/user/save-last-logout")).andExpect(status().isMethodNotAllowed()).andReturn();
    }

    @Test
    public void saveLastLogout_invalidHttpRequestMethodDELETE() throws Exception {
        mockMvc.perform(delete("/user/save-last-logout")).andExpect(status().isMethodNotAllowed()).andReturn();
    }

    @Test
    public void saveLastLogout_failure() throws Exception {
        when(userService.saveLastLogout()).thenReturn(false);

        MvcResult result = mockMvc.perform(get("/user/save-last-logout")).andExpect(status().isBadRequest())
                .andReturn();

        String jsonString = result.getResponse().getContentAsString();
        MessageResDto messageResDto = new Gson().fromJson(jsonString, MessageResDto.class);

        verify(userService, times(1)).saveLastLogout();
        assertEquals(messageResDto.getCode(), ResponseCode.SAVE_LAST_LOGOUT_FAILED);
        assertEquals(messageResDto.getMessage(), ResponseMessage.SAVE_LAST_LOGOUT_FAILED);
    }

    // ---------- /user/save-last-logout START ----------

}
