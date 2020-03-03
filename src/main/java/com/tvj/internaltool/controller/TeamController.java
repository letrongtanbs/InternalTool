package com.tvj.internaltool.controller;

import com.tvj.internaltool.dto.res.TeamListResDto;
import com.tvj.internaltool.service.TeamService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotBlank;

@RestController
@RequestMapping("/team")
public class TeamController {

    private final TeamService teamService;

    public TeamController(TeamService teamService) {
        this.teamService = teamService;
    }

    @GetMapping(value = "/list-by-department")
    public ResponseEntity<?> getTeamByDepartment(@NotBlank @RequestParam("departmentId") String departmentId) {
        TeamListResDto teamListResDto = teamService.getTeamByDepartment(departmentId);
        return new ResponseEntity<>(teamListResDto, HttpStatus.OK);
    }

}
