package com.tvj.internaltool.controller;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.tvj.internaltool.dto.req.PasswordRecoverSendRequestReqDto;
import com.tvj.internaltool.dto.req.PasswordRecoverUpdatePasswordReqDto;
import com.tvj.internaltool.dto.req.UserLoginReqDto;
import com.tvj.internaltool.dto.req.UserSettingUpdatePasswordReqDto;
import com.tvj.internaltool.dto.req.UserSettingUpdateReqDto;
import com.tvj.internaltool.dto.res.FileResDto;
import com.tvj.internaltool.dto.res.MessageResDto;
import com.tvj.internaltool.dto.res.UserLoginResDto;
import com.tvj.internaltool.dto.res.UserSettingResDto;
import com.tvj.internaltool.service.UserService;
import com.tvj.internaltool.utils.ResponseCode;
import com.tvj.internaltool.utils.ResponseMessage;

@RestController
@RequestMapping("/user")
@Validated // Enable validation for both request parameters and path variables
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(value = "/login")
    public ResponseEntity<?> generateAuthenticationToken(@Valid @RequestBody UserLoginReqDto userLoginReqDto) {
        Object userLoginResDto = userService.processLogin(userLoginReqDto.getUsername(), userLoginReqDto.getPassword());
        if (userLoginResDto instanceof UserLoginResDto) {
            return new ResponseEntity<>(userLoginResDto, HttpStatus.OK);
        } else if (ResponseCode.USER_IS_LOCKED.equals(userLoginResDto)) {
            return new ResponseEntity<>(new MessageResDto(ResponseCode.USER_IS_LOCKED, ResponseMessage.USER_IS_LOCKED),
                    HttpStatus.LOCKED);
        } else {
            return new ResponseEntity<>(new MessageResDto(ResponseCode.UNAUTHORIZED, ResponseMessage.UNAUTHORIZED),
                    HttpStatus.UNAUTHORIZED);
        }
    }

    @PostMapping(value = "/password-recover-send-request")
    public ResponseEntity<?> passwordRecoverSendRequest(
            @Valid @RequestBody PasswordRecoverSendRequestReqDto passwordRecoverSendRequestReqDto) {
        boolean isMailSent = userService.processForgotPassword(passwordRecoverSendRequestReqDto.getUsername());
        if (isMailSent) {
            return new ResponseEntity<>(
                    new MessageResDto(ResponseCode.SEND_MAIL_SUCCESS, ResponseMessage.SEND_MAIL_SUCCESS),
                    HttpStatus.OK);
        }
        return new ResponseEntity<>(new MessageResDto(ResponseCode.SEND_MAIL_FAILED, ResponseMessage.SEND_MAIL_FAILED),
                HttpStatus.REQUEST_TIMEOUT);
    }

    @GetMapping(value = "/password-recover-confirm-token")
    public ResponseEntity<?> passwordRecoverConfirmToken(
            @NotBlank(message = "token must not be blank!!") @Size(max = 100, message = "maximum size of token id is 100!!") @RequestParam("token") String token) {
        boolean isTokenValid = userService.processConfirmForgotPasswordToken(token);
        if (isTokenValid) {
            return new ResponseEntity<>(new MessageResDto(ResponseCode.TOKEN_IS_VALID, ResponseMessage.TOKEN_IS_VALID),
                    HttpStatus.OK);
        }
        return new ResponseEntity<>(
                new MessageResDto(ResponseCode.TOKEN_HAS_EXPIRED, ResponseMessage.TOKEN_HAS_EXPIRED),
                HttpStatus.BAD_REQUEST);
    }

    @PostMapping(value = "/password-recover-update-password")
    public ResponseEntity<?> passwordRecoverUpdatePassword(
            @Valid @RequestBody PasswordRecoverUpdatePasswordReqDto passwordRecoverUpdatePasswordReqDto) {
        boolean isPasswordUpdated = userService.processRecoverPassword(passwordRecoverUpdatePasswordReqDto);
        if (isPasswordUpdated) {
            return new ResponseEntity<>(
                    new MessageResDto(ResponseCode.UPDATE_PASSWORD_SUCCESS, ResponseMessage.UPDATE_PASSWORD_SUCCESS),
                    HttpStatus.OK);
        }
        return new ResponseEntity<>(
                new MessageResDto(ResponseCode.UPDATE_PASSWORD_FAILED, ResponseMessage.UPDATE_PASSWORD_FAILED),
                HttpStatus.BAD_REQUEST);
    }

    @GetMapping(value = "/user-setting-get-info")
    public ResponseEntity<?> getUserSetting() {
        UserSettingResDto userSettingResDto = userService.getUserSetting();
        if (userSettingResDto != null) {
            return new ResponseEntity<>(userSettingResDto, HttpStatus.OK);
        }
        return new ResponseEntity<>(
                new MessageResDto(ResponseCode.GET_USER_SETTING_FAILED, ResponseMessage.GET_USER_SETTING_FAILED),
                HttpStatus.BAD_REQUEST);
    }

    @PutMapping(value = "/user-setting-update-info")
    public ResponseEntity<?> updateUserSetting(@Valid @RequestBody UserSettingUpdateReqDto userSettingUpdateReqDto) {
        UserSettingResDto userSettingResDto = userService.updateUserSetting(userSettingUpdateReqDto);
        if (userSettingResDto != null) {
            return new ResponseEntity<>(userSettingResDto, HttpStatus.OK);
        }
        return new ResponseEntity<>(
                new MessageResDto(ResponseCode.UPDATE_USER_SETTING_FAILED, ResponseMessage.UPDATE_USER_SETTING_FAILED),
                HttpStatus.BAD_REQUEST);
    }

    @PatchMapping(value = "/user-setting-update-password")
    public ResponseEntity<?> updatePassword(
            @Valid @RequestBody UserSettingUpdatePasswordReqDto userSettingUpdatePasswordReqDto) {
        boolean isPasswordUpdated = userService.updatePassword(userSettingUpdatePasswordReqDto);
        if (isPasswordUpdated) {
            return new ResponseEntity<>(
                    new MessageResDto(ResponseCode.UPDATE_PASSWORD_SUCCESS, ResponseMessage.UPDATE_PASSWORD_SUCCESS),
                    HttpStatus.OK);
        }
        return new ResponseEntity<>(
                new MessageResDto(ResponseCode.UPDATE_PASSWORD_FAILED, ResponseMessage.UPDATE_PASSWORD_FAILED),
                HttpStatus.BAD_REQUEST);
    }

    @PostMapping(value = "/upload-avatar")
    public ResponseEntity<?> uploadAvatar(@RequestParam("file") MultipartFile multipartFile) {
        String imgPath = userService.uploadAvatar(multipartFile);
        if (StringUtils.isNotBlank(imgPath)) {
            return new ResponseEntity<>(new FileResDto(imgPath), HttpStatus.OK);
        }
        return new ResponseEntity<>(
                new MessageResDto(ResponseCode.UPDATE_AVATAR_FAILED, ResponseMessage.UPDATE_AVATAR_FAILED),
                HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping(value = "/remove-avatar")
    public ResponseEntity<?> removeAvatar() {
        boolean isRemoved = userService.removeAvatar();
        if (isRemoved) {
            return new ResponseEntity<>(
                    new MessageResDto(ResponseCode.REMOVE_AVATAR_SUCCESS, ResponseMessage.REMOVE_AVATAR_SUCCESS),
                    HttpStatus.OK);
        }
        return new ResponseEntity<>(
                new MessageResDto(ResponseCode.REMOVE_AVATAR_FAILED, ResponseMessage.REMOVE_AVATAR_FAILED),
                HttpStatus.BAD_REQUEST);
    }

    @GetMapping(value = "/save-last-logout")
    public ResponseEntity<?> saveLastLogout() {
        boolean isLoggedOut = userService.saveLastLogout();
        if (isLoggedOut) {
            return new ResponseEntity<>(
                    new MessageResDto(ResponseCode.SAVE_LAST_LOGOUT_SUCCESS, ResponseMessage.SAVE_LAST_LOGOUT_SUCCESS),
                    HttpStatus.OK);
        }
        return new ResponseEntity<>(
                new MessageResDto(ResponseCode.SAVE_LAST_LOGOUT_FAILED, ResponseMessage.SAVE_LAST_LOGOUT_FAILED),
                HttpStatus.BAD_REQUEST);
    }

}
