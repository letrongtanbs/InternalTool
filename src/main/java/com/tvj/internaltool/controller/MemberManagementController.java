package com.tvj.internaltool.controller;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tvj.internaltool.dto.req.MemberAddReqDto;
import com.tvj.internaltool.dto.req.MemberSearchReqDto;
import com.tvj.internaltool.dto.res.MemberListResDto;
import com.tvj.internaltool.dto.res.MessageResDto;
import com.tvj.internaltool.service.MemberManagementService;
import com.tvj.internaltool.utils.ResponseCode;
import com.tvj.internaltool.utils.ResponseMessage;

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

    @PostMapping(value = "/add-member")
    public ResponseEntity<?> addMember(@Valid @RequestBody MemberAddReqDto memberAddReqDto) {
        boolean isMemberAdded = memberManagementService.addMember(memberAddReqDto);
        if (isMemberAdded) {
            return new ResponseEntity<>(
                    new MessageResDto(ResponseCode.ADD_NEW_MEMBER_SUCCESS, ResponseMessage.ADD_NEW_MEMBER_SUCCESS),
                    HttpStatus.OK);
        }
        return new ResponseEntity<>(
                new MessageResDto(ResponseCode.ADD_NEW_MEMBER_FAILED, ResponseMessage.ADD_NEW_MEMBER_FAILED),
                HttpStatus.BAD_REQUEST);
    }

}
