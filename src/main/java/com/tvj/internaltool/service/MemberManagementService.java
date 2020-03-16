package com.tvj.internaltool.service;

import com.tvj.internaltool.dto.req.MemberSearchReqDto;
import com.tvj.internaltool.dto.res.MemberListResDto;

public interface MemberManagementService {

    MemberListResDto searchMember(MemberSearchReqDto memberSearchReqDto);

}
