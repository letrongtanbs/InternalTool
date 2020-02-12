package com.tvj.internaltool.controller;

import com.tvj.internaltool.dto.res.DepartmentListResDto;
import com.tvj.internaltool.service.DepartmentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/department")
@CrossOrigin
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
