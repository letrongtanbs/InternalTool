package com.tvj.internaltool.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tvj.internaltool.dto.res.UserStatusListResDto;
import com.tvj.internaltool.service.UserStatusService;

@RestController
@RequestMapping("/user-status")
public class UserStatusController {

    private final UserStatusService userStatusService;

    public UserStatusController(UserStatusService userStatusService) {
        this.userStatusService = userStatusService;
    }

    @GetMapping(value = "/list")
    public ResponseEntity<?> getAllUserStatus() {
        UserStatusListResDto userStatusListResDto  = userStatusService.getAllUserStatus();
        return new ResponseEntity<>(userStatusListResDto, HttpStatus.OK);
    }

}
