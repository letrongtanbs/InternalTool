package com.tvj.internaltool.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.tvj.internaltool.dto.res.DepartmentListResDto;
import com.tvj.internaltool.dto.res.DepartmentResDto;
import com.tvj.internaltool.entity.DepartmentEntity;
import com.tvj.internaltool.repository.DepartmentRepository;
import com.tvj.internaltool.service.DepartmentService;
import com.tvj.internaltool.utils.ModelMapperUtils;

@Service
public class DepartmentServiceImpl implements DepartmentService {

    private final DepartmentRepository departmentRepository;

    public DepartmentServiceImpl(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    @Override
    public DepartmentListResDto getAllDepartment() {
        List<DepartmentEntity> departmentEntityList = departmentRepository.findAll();
        List<DepartmentResDto> departmentResDtoList = ModelMapperUtils.mapAll(departmentEntityList, DepartmentResDto.class);
        return new DepartmentListResDto(departmentResDtoList);
    }

}
