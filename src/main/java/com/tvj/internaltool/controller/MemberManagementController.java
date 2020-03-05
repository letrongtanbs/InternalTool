package com.tvj.internaltool.controller;

import com.tvj.internaltool.utils.UserUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/member-management")
public class MemberManagementController {

    @GetMapping(value = "/list/")
    public ResponseEntity<?> getAllMember() {

        return new ResponseEntity<>("Hello: " + UserUtils.getCurrentUsername(), HttpStatus.OK);
    }


}
