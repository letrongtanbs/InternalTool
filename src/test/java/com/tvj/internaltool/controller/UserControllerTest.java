package com.tvj.internaltool.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.tvj.internaltool.dto.req.*;
import com.tvj.internaltool.dto.res.MessageResDto;
import com.tvj.internaltool.dto.res.UserLoginResDto;
import com.tvj.internaltool.dto.res.UserSettingResDto;
import com.tvj.internaltool.dummy.dto.res.UserLoginResDtoDataDummy;
import com.tvj.internaltool.dummy.dto.res.UserSettingResDtoDataDummy;
import com.tvj.internaltool.enums.UserStatus;
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
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMultipartHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.multipart.MultipartFile;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(MockitoJUnitRunner.class)
public class UserControllerTest {

    @InjectMocks
    private UserController userController;

    @Mock
    private UserService userService;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }

    // ---------- /user/login START ----------

    @Test // Do not use org.junit.jupiter.api
    public void generateAuthenticationToken_success() throws Exception {
        // Value from client
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
        // Value from client
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
        // Value from client
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
        // Value from client
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
        // Value from client
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
        assertEquals(messageResDto.getCode(), ResponseCode.SEND_MAIL_FAILED);
        assertEquals(messageResDto.getMessage(), ResponseMessage.SEND_MAIL_FAILED);
    }

    // ---------- /user/password-recover-send-request END ----------

    // ---------- /user/password-recover-confirm-token START ----------

    @Test
    public void forgotPasswordConfirmToken_success() throws Exception {
        // Value from client
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
        // Value from client
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
        // Value from client
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
        // Value from client
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

        mockMvc.perform(get("/user/user-setting-get-info"))
                .andExpect(status().isOk())
                .andReturn();

        verify(userService, times(1)).getUserSetting();
    }

    @Test
    public void getUserSetting_success_withEmptyData() throws Exception {
        when(userService.getUserSetting()).thenReturn(null);

        MvcResult result = mockMvc.perform(get("/user/user-setting-get-info"))
                .andExpect(status().isBadRequest())
                .andReturn();

        String jsonString = result.getResponse().getContentAsString();
        MessageResDto messageResDto = new Gson().fromJson(jsonString, MessageResDto.class);

        verify(userService, times(1)).getUserSetting();
        assertEquals(messageResDto.getCode(), ResponseCode.GET_USER_SETTING_FAILED);
        assertEquals(messageResDto.getMessage(), ResponseMessage.GET_USER_SETTING_FAILED);
    }

    // ---------- /user/user-setting-get-info END ----------

    // ---------- /user/user-setting-update-info START ----------

    @Test
    public void updateUserSetting_success() throws Exception {
        // Value from client
        UserSettingReqDto userSettingReqDto = new UserSettingReqDto();
        userSettingReqDto.setTeamId("1");
        userSettingReqDto.setAddress("1 Some Where");
        userSettingReqDto.setPhone("1234567890");
        userSettingReqDto.setCountryId("1");
        userSettingReqDto.setLanguageId("1");
        userSettingReqDto.setStatus(UserStatus.AVAILABLE.getStatus());

        UserSettingResDtoDataDummy userSettingResDtoDataDummy = new UserSettingResDtoDataDummy();
        UserSettingResDto userSettingResDto = userSettingResDtoDataDummy.getAdminUserSettingResDto1();

        when(userService.updateUserSetting(any(UserSettingReqDto.class))).thenReturn(userSettingResDto);

        mockMvc.perform(put("/user/user-setting-update-info")
                .content(new ObjectMapper().writeValueAsString(userSettingReqDto))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        verify(userService, times(1)).updateUserSetting(any(UserSettingReqDto.class));
    }

    @Test
    public void updateUserSetting_cannotUpdateUserSetting() throws Exception {
        // Value from client
        UserSettingReqDto userSettingReqDto = new UserSettingReqDto();
        userSettingReqDto.setTeamId("1");
        userSettingReqDto.setAddress("1 Some Where");
        userSettingReqDto.setPhone("1234567890");
        userSettingReqDto.setCountryId("1");
        userSettingReqDto.setLanguageId("Vietnamese");
        userSettingReqDto.setStatus(UserStatus.AVAILABLE.getStatus());

        when(userService.updateUserSetting(any(UserSettingReqDto.class))).thenReturn(null);

        MvcResult result = mockMvc.perform(put("/user/user-setting-update-info")
                .content(new ObjectMapper().writeValueAsString(userSettingReqDto))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();

        String jsonString = result.getResponse().getContentAsString();
        MessageResDto messageResDto = new Gson().fromJson(jsonString, MessageResDto.class);

        verify(userService, times(1)).updateUserSetting(any(UserSettingReqDto.class));
        assertEquals(messageResDto.getCode(), ResponseCode.UPDATE_USER_SETTING_FAILED);
        assertEquals(messageResDto.getMessage(), ResponseMessage.UPDATE_USER_SETTING_FAILED);
    }

    // ---------- /user/user-setting-update-info END ----------

    // ---------- /user/user-setting-update-password START ----------

    @Test
    public void updatePassword_success() throws Exception {
        // Value from client
        UpdatePasswordReqDto updatePasswordReqDto = new UpdatePasswordReqDto();
        updatePasswordReqDto.setOldPassword("12345678");
        updatePasswordReqDto.setNewPassword("87654321");

        when(userService.updatePassword(any(UpdatePasswordReqDto.class))).thenReturn(true);

        MvcResult result = mockMvc.perform(patch("/user/user-setting-update-password")
                .content(new ObjectMapper().writeValueAsString(updatePasswordReqDto))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String jsonString = result.getResponse().getContentAsString();
        MessageResDto messageResDto = new Gson().fromJson(jsonString, MessageResDto.class);

        verify(userService, times(1)).updatePassword(any(UpdatePasswordReqDto.class));
        assertEquals(messageResDto.getCode(), ResponseCode.UPDATE_PASSWORD_SUCCESS);
        assertEquals(messageResDto.getMessage(), ResponseMessage.UPDATE_PASSWORD_SUCCESS);
    }

    @Test
    public void updatePassword_cannotUpdatePassword() throws Exception {
        // Value from client
        UpdatePasswordReqDto updatePasswordReqDto = new UpdatePasswordReqDto();
        updatePasswordReqDto.setOldPassword("12345678");
        updatePasswordReqDto.setNewPassword("87654321");

        when(userService.updatePassword(any(UpdatePasswordReqDto.class))).thenReturn(false);

        MvcResult result = mockMvc.perform(patch("/user/user-setting-update-password")
                .content(new ObjectMapper().writeValueAsString(updatePasswordReqDto))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();

        String jsonString = result.getResponse().getContentAsString();
        MessageResDto messageResDto = new Gson().fromJson(jsonString, MessageResDto.class);

        verify(userService, times(1)).updatePassword(any(UpdatePasswordReqDto.class));
        assertEquals(messageResDto.getCode(), ResponseCode.UPDATE_PASSWORD_FAILED);
        assertEquals(messageResDto.getMessage(), ResponseMessage.UPDATE_PASSWORD_FAILED);
    }

    // ---------- /user/user-setting-update-password END ----------

    // ---------- /user/upload-avatar START ----------

    @Test
    public void uploadAvatar_success() throws Exception {
        // Value from client
        MockMultipartFile image = new MockMultipartFile(
                "file",
                "cv.jpg",
                "image/jpeg",
                "{\"image\": \"F:\\TVJ\\file_upload\\test\\cv.jpg\"}".getBytes());

        when(userService.uploadAvatar(any(MultipartFile.class))).thenReturn("output/file/path");

        MockMultipartHttpServletRequestBuilder builder =
                MockMvcRequestBuilders.multipart("/user/upload-avatar");

        mockMvc.perform(builder
                .file(image))
                .andExpect(status().isOk())
                .andReturn();

        verify(userService, times(1)).uploadAvatar(any(MultipartFile.class));
    }

    @Test
    public void uploadAvatar_cannotUploadAvatar() throws Exception {
        // Value from client
        MockMultipartFile image = new MockMultipartFile(
                "file",
                "cv.jpg",
                "image/jpeg",
                "{\"image\": \"F:\\TVJ\\file_upload\\test\\cv.jpg\"}".getBytes());

        when(userService.uploadAvatar(any(MultipartFile.class))).thenReturn("");

        MockMultipartHttpServletRequestBuilder builder =
                MockMvcRequestBuilders.multipart("/user/upload-avatar");

        MvcResult result = mockMvc.perform(builder
                .file(image))
                .andExpect(status().isBadRequest())
                .andReturn();

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

        MvcResult result = mockMvc.perform(delete("/user/remove-avatar"))
                .andExpect(status().isOk())
                .andReturn();

        String jsonString = result.getResponse().getContentAsString();
        MessageResDto messageResDto = new Gson().fromJson(jsonString, MessageResDto.class);

        verify(userService, times(1)).removeAvatar();
        assertEquals(messageResDto.getCode(), ResponseCode.REMOVE_AVATAR_SUCCESS);
        assertEquals(messageResDto.getMessage(), ResponseMessage.REMOVE_AVATAR_SUCCESS);
    }

    @Test
    public void removeAvatar_cannotRemoveAvatar() throws Exception {
        when(userService.removeAvatar()).thenReturn(false);

        MvcResult result = mockMvc.perform(delete("/user/remove-avatar"))
                .andExpect(status().isBadRequest())
                .andReturn();

        String jsonString = result.getResponse().getContentAsString();
        MessageResDto messageResDto = new Gson().fromJson(jsonString, MessageResDto.class);

        verify(userService, times(1)).removeAvatar();
        assertEquals(messageResDto.getCode(), ResponseCode.REMOVE_AVATAR_FAILED);
        assertEquals(messageResDto.getMessage(), ResponseMessage.REMOVE_AVATAR_FAILED);
    }

    // ---------- /user/remove-avatar END ----------

}
