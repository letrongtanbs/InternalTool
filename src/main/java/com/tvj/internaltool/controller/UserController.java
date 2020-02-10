package com.tvj.internaltool.controller;

import com.tvj.internaltool.dto.req.ForgotPasswordReqDto;
import com.tvj.internaltool.dto.req.RecoverPasswordReqDto;
import com.tvj.internaltool.dto.req.UserLoginReqDto;
import com.tvj.internaltool.dto.req.UserSettingReqDto;
import com.tvj.internaltool.dto.res.MessageResDto;
import com.tvj.internaltool.dto.res.SimpleContentResDto;
import com.tvj.internaltool.dto.res.UserLoginResDto;
import com.tvj.internaltool.dto.res.UserSettingResDto;
import com.tvj.internaltool.service.UserService;
import com.tvj.internaltool.utils.ResponseCode;
import com.tvj.internaltool.utils.ResponseMessage;
import com.tvj.internaltool.utils.UserUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

@RestController
@RequestMapping("/user")
@CrossOrigin
public class UserController {


    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(value = "/login")
    public ResponseEntity<?> generateAuthenticationToken(@Valid @RequestBody UserLoginReqDto userLoginReqDto) {
        UserLoginResDto userLoginResDto = userService.processLogin(userLoginReqDto.getUsername(), userLoginReqDto.getPassword());
        if (userLoginResDto != null) {
            return new ResponseEntity<>(userLoginResDto, HttpStatus.OK);
        }
        return new ResponseEntity<>(new MessageResDto(ResponseCode.UNAUTHORIZED, ResponseMessage.UNAUTHORIZED), HttpStatus.UNAUTHORIZED);
    }

    @PostMapping(value = "/forgot-password")
    public ResponseEntity<?> forgotPasswordProcess(@Valid @RequestBody ForgotPasswordReqDto forgotPasswordReqDto) {
        boolean isMailSent = userService.processForgotPassword(forgotPasswordReqDto.getUsername());
        if (isMailSent) {
            return new ResponseEntity<>(new MessageResDto(ResponseCode.SEND_MAIL_SUCCESS, ResponseMessage.SEND_MAIL_SUCCESS), HttpStatus.OK);
        }
        return new ResponseEntity<>(new MessageResDto(ResponseCode.SEND_MAIL_FAIL, ResponseMessage.SEND_MAIL_FAIL), HttpStatus.REQUEST_TIMEOUT);
    }

    @GetMapping(value = "/password-recover-confirm-token")
    public ResponseEntity<?> forgotPasswordConfirmToken(@NotBlank @RequestParam("token") String token) {
        token = userService.processConfirmForgotPasswordToken(token);
        if (token != null) {
            return new ResponseEntity<>(new SimpleContentResDto(token), HttpStatus.OK);
        }
        return new ResponseEntity<>(new MessageResDto(ResponseCode.TOKEN_HAS_EXPIRED, ResponseMessage.TOKEN_HAS_EXPIRED), HttpStatus.BAD_REQUEST);
    }

    @PostMapping(value = "/password-recover")
    public ResponseEntity<?> recoverPassword(@Valid @RequestBody RecoverPasswordReqDto recoverPasswordReqDto) {
        boolean isPasswordUpdated = userService.processRecoverPassword(recoverPasswordReqDto);
        if (isPasswordUpdated) {
            return new ResponseEntity<>(new MessageResDto(ResponseCode.UPDATE_PASSWORD_SUCCESS, ResponseMessage.UPDATE_PASSWORD_SUCCESS), HttpStatus.OK);
        }
        return new ResponseEntity<>(new MessageResDto(ResponseCode.UPDATE_PASSWORD_FAIL, ResponseMessage.UPDATE_PASSWORD_FAIL), HttpStatus.BAD_REQUEST);
    }

    @GetMapping(value = "/user-setting")
    public ResponseEntity<?> showUserSetting() {
        UserSettingResDto userSettingResDto = userService.getUserSetting();
        return new ResponseEntity<>(userSettingResDto, HttpStatus.OK);
    }

    @PutMapping(value = "/user-setting")
    public ResponseEntity<?> updateUserSetting(@Valid @RequestBody UserSettingReqDto userSettingReqDto) {
        UserSettingResDto userSettingResDto = userService.updateUserSetting(userSettingReqDto);
        return new ResponseEntity<>(userSettingResDto, HttpStatus.OK);
    }


    // test role-permission

    @GetMapping(value = "/{id}/list/{listId}")
    public ResponseEntity<?> test() {
        return new ResponseEntity<>("Hello: " + UserUtils.getCurrentUsername(), HttpStatus.OK);
    }

    @GetMapping(value = "/{id}/list2/{listId}")
    public ResponseEntity<?> test2() {
        return new ResponseEntity<>("Hello2: " + UserUtils.getCurrentUsername(), HttpStatus.OK);
    }

    @GetMapping(value = "/{id}/list/{listId}/item")
    public ResponseEntity<?> test3() {
        return new ResponseEntity<>("Hello3: " + UserUtils.getCurrentUsername(), HttpStatus.OK);
    }

    @GetMapping(value = "/{id}/list")
    public ResponseEntity<?> test4() {
        return new ResponseEntity<>("Hello4: " + UserUtils.getCurrentUsername(), HttpStatus.OK);
    }

}
