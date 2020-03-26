package com.tvj.internaltool.controller;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tvj.internaltool.dto.req.MemberActivateStatusUpdateReqDto;
import com.tvj.internaltool.dto.req.MemberAddReqDto;
import com.tvj.internaltool.dto.req.MemberSearchReqDto;
import com.tvj.internaltool.dto.req.MemberUpdateReqDto;
import com.tvj.internaltool.dto.res.MemberListResDto;
import com.tvj.internaltool.dto.res.MemberResDto;
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

    @PutMapping(value = "/update-member")
    public ResponseEntity<?> updateMember(@Valid @RequestBody MemberUpdateReqDto memberUpdateReqDto) {
        boolean isMemberUpdated = memberManagementService.updateMember(memberUpdateReqDto);
        if (isMemberUpdated) {
            return new ResponseEntity<>(
                    new MessageResDto(ResponseCode.UPDATE_MEMBER_SUCCESS, ResponseMessage.UPDATE_MEMBER_SUCCESS),
                    HttpStatus.OK);
        }
        return new ResponseEntity<>(
                new MessageResDto(ResponseCode.UPDATE_MEMBER_FAILED, ResponseMessage.UPDATE_MEMBER_FAILED),
                HttpStatus.BAD_REQUEST);
    }

    @GetMapping(value = "/view-member")
    public ResponseEntity<?> viewMember(@NotBlank @RequestParam("username") String username) {
        MemberResDto memberResDto = memberManagementService.viewMember(username);
        if (memberResDto != null) {
            return new ResponseEntity<>(memberResDto, HttpStatus.OK);
        }
        return new ResponseEntity<>(
                new MessageResDto(ResponseCode.VIEW_MEMBER_FAILED, ResponseMessage.VIEW_MEMBER_FAILED),
                HttpStatus.BAD_REQUEST);
    }

    @PatchMapping(value = "/update-member-activate-status")
    public ResponseEntity<?> updateMemberActivateStatus(@Valid @RequestBody MemberActivateStatusUpdateReqDto memberActivateStatusUpdateReqDto) {
        boolean isMemberActivateStatusUpdated = memberManagementService.updateMemberActivateStatus(memberActivateStatusUpdateReqDto);
        if (isMemberActivateStatusUpdated) {
            return new ResponseEntity<>(new MessageResDto(ResponseCode.UPDATE_MEMBER_ACTIVATED_STATUS_SUCCESS,
                    ResponseMessage.UPDATE_MEMBER_ACTIVATED_STATUS_SUCCESS), HttpStatus.OK);
        }
        return new ResponseEntity<>(new MessageResDto(ResponseCode.UPDATE_MEMBER_ACTIVATED_STATUS_FAILED,
                ResponseMessage.UPDATE_MEMBER_ACTIVATED_STATUS_FAILED), HttpStatus.BAD_REQUEST);
    }

}
