package com.tvj.internaltool.repository;

import com.tvj.internaltool.dto.req.MemberSearchReqDto;
import com.tvj.internaltool.entity.UserEntity;

import java.util.List;

public interface CustomUserRepository {

    List<UserEntity> searchMember(MemberSearchReqDto memberSearchReqDto);

    long searchMemberTotal(MemberSearchReqDto memberSearchReqDto);

}
