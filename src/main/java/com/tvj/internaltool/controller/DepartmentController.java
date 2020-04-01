package com.tvj.internaltool.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tvj.internaltool.dto.res.DepartmentListResDto;
import com.tvj.internaltool.service.DepartmentService;

@RestController
@RequestMapping("/department")
@Validated // Enable validation for both request parameters and path variables
public class DepartmentController {

    private final DepartmentService departmentService;

    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @GetMapping(value = "/list")
    public ResponseEntity<?> getAllDepartment() {
        DepartmentListResDto departmentListResDto = departmentService.getAllDepartment();
        return new ResponseEntity<>(departmentListResDto, HttpStatus.OK);
    }

}
