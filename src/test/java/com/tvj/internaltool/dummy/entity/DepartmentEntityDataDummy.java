package com.tvj.internaltool.dummy.entity;

import java.time.LocalDateTime;

import com.tvj.internaltool.entity.DepartmentEntity;

public class DepartmentEntityDataDummy {

    public DepartmentEntity getDepartment1() {
        DepartmentEntity departmentEntity = new DepartmentEntity();
        departmentEntity.setDepartmentId("1");
        departmentEntity.setDepartmentCode("DEP001");
        departmentEntity.setDepartmentName("Department 1");
        departmentEntity.setCreatedBy("Dex");
        departmentEntity.setCreatedDate(LocalDateTime.now());
        departmentEntity.setUpdatedBy("Dexx");
        departmentEntity.setUpdatedDate(LocalDateTime.now().plusHours(1));
        departmentEntity.setDeletedBy(null);
        departmentEntity.setDeletedDate(null);
        return departmentEntity;
    }

    public DepartmentEntity getDepartment2() {
        DepartmentEntity departmentEntity = new DepartmentEntity();
        departmentEntity.setDepartmentId("2");
        departmentEntity.setDepartmentCode("DEP002");
        departmentEntity.setDepartmentName("Department 2");
        departmentEntity.setCreatedBy("Dex");
        departmentEntity.setCreatedDate(LocalDateTime.now());
        departmentEntity.setUpdatedBy("Dexx");
        departmentEntity.setUpdatedDate(LocalDateTime.now().plusHours(1));
        departmentEntity.setDeletedBy(null);
        departmentEntity.setDeletedDate(null);
        return departmentEntity;
    }
}
