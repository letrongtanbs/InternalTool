package com.tvj.internaltool.dummy.dto.res;

import com.tvj.internaltool.dto.res.TeamResDto;

public class TeamResDtoDataDummy {

    public TeamResDto getTeam1() {
        TeamResDto teamResDto = new TeamResDto();
        teamResDto.setTeamId("1");
        teamResDto.setTeamCode("TEAM001");
        teamResDto.setTeamName("Team 1");
        return teamResDto;
    }

    public TeamResDto getTeam2() {
        TeamResDto teamResDto = new TeamResDto();
        teamResDto.setTeamId("2");
        teamResDto.setTeamCode("TEAM002");
        teamResDto.setTeamName("Team 2");
        return teamResDto;
    }

}
