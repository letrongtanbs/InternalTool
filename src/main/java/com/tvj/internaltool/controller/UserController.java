package com.tvj.internaltool.controller;

import com.tvj.internaltool.dto.req.ForgotPasswordReqDto;
import com.tvj.internaltool.dto.req.UserLoginReqDto;
import com.tvj.internaltool.dto.res.MessageResDto;
import com.tvj.internaltool.dto.res.UserLoginResDto;
import com.tvj.internaltool.security.JwtTokenUtil;
import com.tvj.internaltool.service.UserService;
import com.tvj.internaltool.utils.ResponseCode;
import com.tvj.internaltool.utils.ResponseMessage;
import com.tvj.internaltool.utils.UserUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/user")
public class UserController {

    private final JwtTokenUtil jwtTokenUtil;
    private final UserService userService;

    public UserController(JwtTokenUtil jwtTokenUtil, UserService userService) {
        this.jwtTokenUtil = jwtTokenUtil;
        this.userService = userService;
    }

    @PostMapping(value = "/login")
    public ResponseEntity<?> generateAuthenticationToken(@Valid @RequestBody UserLoginReqDto userLoginReqDto) {
        final UserDetails userDetails = userService.processLogin(userLoginReqDto.getUsername(), userLoginReqDto.getPassword());
        if (userDetails == null) {
            return new ResponseEntity<>(new MessageResDto(ResponseCode.UNAUTHORIZED, ResponseMessage.UNAUTHORIZED), HttpStatus.UNAUTHORIZED);
        }
        final String token = jwtTokenUtil.generateToken(userDetails);
        return new ResponseEntity<>(new UserLoginResDto(token), HttpStatus.OK);
    }

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

    @PostMapping(value = "/forgot-password")
    public ResponseEntity<?> forgotPasswordProcess(@Valid @RequestBody ForgotPasswordReqDto forgotPasswordReqDto) {
        boolean isMailSent = userService.processForgotPassword(forgotPasswordReqDto);
        if (isMailSent) {
            return new ResponseEntity<>(new MessageResDto(ResponseCode.SEND_MAIL_SUCCESS, ResponseMessage.SEND_MAIL_SUCCESS), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new MessageResDto(ResponseCode.SEND_MAIL_FAIL, ResponseMessage.SEND_MAIL_FAIL), HttpStatus.REQUEST_TIMEOUT);
        }
    }

}
