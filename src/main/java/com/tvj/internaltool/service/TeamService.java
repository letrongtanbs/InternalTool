package com.tvj.internaltool.service;

import com.tvj.internaltool.dto.res.TeamListResDto;

public interface TeamService {

    TeamListResDto getTeamByDepartment(String departmentId);

}
