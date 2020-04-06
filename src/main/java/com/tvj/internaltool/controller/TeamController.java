package com.tvj.internaltool.controller;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tvj.internaltool.dto.res.TeamListResDto;
import com.tvj.internaltool.service.TeamService;

@RestController
@RequestMapping("/team")
@Validated // Enable validation for both request parameters and path variables
public class TeamController {

    private final TeamService teamService;

    public TeamController(TeamService teamService) {
        this.teamService = teamService;
    }

    @GetMapping(value = "/list-by-department")
    public ResponseEntity<?> getTeamByDepartment(
            @NotBlank(message = "department id must not be blank!!") 
            @Size(min = 1, max = 40, message = "size of department id must between 1 and 40!!") 
            @RequestParam("departmentId") String departmentId) {
        TeamListResDto teamListResDto = teamService.getTeamByDepartment(departmentId);
        return new ResponseEntity<>(teamListResDto, HttpStatus.OK);
    }

}
