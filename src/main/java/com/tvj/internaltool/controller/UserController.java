package com.tvj.internaltool.controller;

import com.tvj.internaltool.dto.req.UserLoginReqDto;
import com.tvj.internaltool.dto.res.LoginResDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/user")
public class UserController {

    @PostMapping("/login")
    public ResponseEntity<LoginResDto> retrieveLoginResult(@Valid @RequestBody UserLoginReqDto userLoginReqDto) {
        if (userLoginReqDto.getUsername().equals("admin") && userLoginReqDto.getPassword().equals("admin")) {
            return new ResponseEntity<>(new LoginResDto("OK"), HttpStatus.OK);
        }
        return new ResponseEntity<>(new LoginResDto("NG"), HttpStatus.FORBIDDEN);
    }

}
