package com.tvj.internaltool.dummy.dto.res;

import com.tvj.internaltool.dto.res.DepartmentResDto;

public class DepartmentResDtoDataDummy {

    public DepartmentResDto getDepartment1() {
        DepartmentResDto departmentResDto = new DepartmentResDto();
        departmentResDto.setDepartmentId("1");
        departmentResDto.setDepartmentCode("DEP001");
        departmentResDto.setDepartmentName("Department 1");
        return departmentResDto;
    }

    public DepartmentResDto getDepartment2() {
        DepartmentResDto departmentResDto = new DepartmentResDto();
        departmentResDto.setDepartmentId("2");
        departmentResDto.setDepartmentCode("DEP002");
        departmentResDto.setDepartmentName("Department 2");
        return departmentResDto;
    }

}
