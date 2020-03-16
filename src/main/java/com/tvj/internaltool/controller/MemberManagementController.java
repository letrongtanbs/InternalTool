package com.tvj.internaltool.controller;

import com.tvj.internaltool.dto.req.MemberSearchReqDto;
import com.tvj.internaltool.dto.res.MemberListResDto;
import com.tvj.internaltool.service.MemberManagementService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/member-management")
public class MemberManagementController {

    private final MemberManagementService memberManagementService;

    public MemberManagementController(MemberManagementService memberManagementService) {
        this.memberManagementService = memberManagementService;
    }

    @GetMapping(value = "/search")
    public ResponseEntity<?> searchMember(@Valid MemberSearchReqDto memberSearchReqDto) {
        MemberListResDto memberListResDto = memberManagementService.searchMember(memberSearchReqDto);
        return new ResponseEntity<>(memberListResDto, HttpStatus.OK);
    }

}
