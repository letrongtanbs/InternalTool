package com.tvj.internaltool.service.impl;

import com.tvj.internaltool.dto.req.MemberSearchReqDto;
import com.tvj.internaltool.dto.res.MemberListResDto;
import com.tvj.internaltool.dto.res.MemberResDto;
import com.tvj.internaltool.entity.UserEntity;
import com.tvj.internaltool.repository.UserRepository;
import com.tvj.internaltool.service.MemberManagementService;
import com.tvj.internaltool.utils.ModelMapperUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MemberManagementServiceImpl implements MemberManagementService {

    private final UserRepository userRepository;

    public MemberManagementServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public MemberListResDto searchMember(MemberSearchReqDto memberSearchReqDto) {
        List<UserEntity> userEntityList = userRepository.searchMember(memberSearchReqDto);
        long userEntityListTotal = userRepository.searchMemberTotal(memberSearchReqDto);
        List<MemberResDto> memberResDtoList = ModelMapperUtils.mapAll(userEntityList, MemberResDto.class);
        return new MemberListResDto(userEntityListTotal, memberResDtoList);
    }


}
