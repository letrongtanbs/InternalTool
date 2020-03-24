package com.tvj.internaltool.service;

import com.tvj.internaltool.dto.req.MemberAddReqDto;
import com.tvj.internaltool.dto.req.MemberSearchReqDto;
import com.tvj.internaltool.dto.req.MemberUpdateReqDto;
import com.tvj.internaltool.dto.res.MemberListResDto;

public interface MemberManagementService {

    MemberListResDto searchMember(MemberSearchReqDto memberSearchReqDto);

    boolean addMember(MemberAddReqDto memberAddReqDto);

    boolean updateMember(MemberUpdateReqDto memberUpdateReqDto);

}
