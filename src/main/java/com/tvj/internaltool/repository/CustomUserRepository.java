package com.tvj.internaltool.repository;

import java.util.List;

import com.tvj.internaltool.dto.req.MemberSearchReqDto;
import com.tvj.internaltool.entity.UserEntity;

public interface CustomUserRepository {

    List<UserEntity> searchMember(MemberSearchReqDto memberSearchReqDto);

    long searchMemberTotal(MemberSearchReqDto memberSearchReqDto);

}
