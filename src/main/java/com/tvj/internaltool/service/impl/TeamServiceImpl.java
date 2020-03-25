package com.tvj.internaltool.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.tvj.internaltool.dto.res.TeamListResDto;
import com.tvj.internaltool.dto.res.TeamResDto;
import com.tvj.internaltool.entity.TeamEntity;
import com.tvj.internaltool.repository.TeamRepository;
import com.tvj.internaltool.service.TeamService;
import com.tvj.internaltool.utils.ModelMapperUtils;

@Service
public class TeamServiceImpl implements TeamService {

    private final TeamRepository teamRepository;

    public TeamServiceImpl(TeamRepository teamRepository) {
        this.teamRepository = teamRepository;
    }

    @Override
    public TeamListResDto getTeamByDepartment(String departmentId) {
        List<TeamEntity> teamEntityList = teamRepository.findByDepartmentId(departmentId);
        List<TeamResDto> teamResDtoList = ModelMapperUtils.mapAll(teamEntityList, TeamResDto.class);
        return new TeamListResDto(teamResDtoList);
    }

}
