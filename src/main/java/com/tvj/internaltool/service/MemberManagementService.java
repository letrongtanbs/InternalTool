package com.tvj.internaltool.service;

import com.tvj.internaltool.dto.req.MemberActivateStatusUpdateReqDto;
import com.tvj.internaltool.dto.req.MemberAddReqDto;
import com.tvj.internaltool.dto.req.MemberDeleteReqDto;
import com.tvj.internaltool.dto.req.MemberSearchReqDto;
import com.tvj.internaltool.dto.req.MemberUpdateReqDto;
import com.tvj.internaltool.dto.res.MemberListResDto;
import com.tvj.internaltool.dto.res.MemberResDto;

public interface MemberManagementService {

    MemberListResDto searchMember(MemberSearchReqDto memberSearchReqDto);

    boolean addMember(MemberAddReqDto memberAddReqDto);

    boolean updateMember(MemberUpdateReqDto memberUpdateReqDto);

    MemberResDto viewMember(String username);

    boolean updateMemberActivateStatus(MemberActivateStatusUpdateReqDto memberActivateStatusUpdateReqDto);

    boolean deleteMember(MemberDeleteReqDto memberDeleteReqDto);

}
