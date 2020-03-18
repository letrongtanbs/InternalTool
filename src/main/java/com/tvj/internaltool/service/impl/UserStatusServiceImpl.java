package com.tvj.internaltool.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.tvj.internaltool.dto.res.UserStatusListResDto;
import com.tvj.internaltool.dto.res.UserStatusResDto;
import com.tvj.internaltool.entity.UserStatusEntity;
import com.tvj.internaltool.repository.UserStatusRepository;
import com.tvj.internaltool.service.UserStatusService;
import com.tvj.internaltool.utils.ModelMapperUtils;

@Service
public class UserStatusServiceImpl implements UserStatusService {

    private final UserStatusRepository userStatusRepository;

    public UserStatusServiceImpl(UserStatusRepository userStatusRepository) {
        this.userStatusRepository = userStatusRepository;
    }

    @Override
    public UserStatusListResDto getAllUserStatus() {
        List<UserStatusEntity> userStatusEntityList = userStatusRepository.findAll();
        List<UserStatusResDto> userStatusResDtoList = ModelMapperUtils.mapAll(userStatusEntityList, UserStatusResDto.class);
        return new UserStatusListResDto(userStatusResDtoList);
    }

}
