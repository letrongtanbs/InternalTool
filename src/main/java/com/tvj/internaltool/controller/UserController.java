package com.tvj.internaltool.controller;

import com.tvj.internaltool.dto.req.UserLoginReqDto;
import com.tvj.internaltool.dto.res.ErrorResDto;
import com.tvj.internaltool.dto.res.UserLoginResDto;
import com.tvj.internaltool.security.JwtTokenUtil;
import com.tvj.internaltool.service.UserService;
import com.tvj.internaltool.utils.ErrorCode;
import com.tvj.internaltool.utils.ErrorMessage;
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
            return new ResponseEntity<>(new ErrorResDto(ErrorCode.FORBIDDEN, ErrorMessage.FORBIDDEN), HttpStatus.FORBIDDEN);
        }
        final String token = jwtTokenUtil.generateToken(userDetails);
        return new ResponseEntity<>(new UserLoginResDto(token), HttpStatus.OK);
    }

    @GetMapping(value = "/url")
    public ResponseEntity<?> test() {
        return new ResponseEntity<>("Hello: " + UserUtils.getCurrentUsername(), HttpStatus.OK);
    }

    @GetMapping(value = "/url-for-root")
    public ResponseEntity<?> testRoot() {
        return new ResponseEntity<>("Hello boss: " + UserUtils.getCurrentUsername(), HttpStatus.OK);
    }

}
