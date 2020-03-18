package com.tvj.internaltool.service.impl;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.tvj.internaltool.dto.res.DepartmentListResDto;
import com.tvj.internaltool.dummy.entity.DepartmentEntityDataDummy;
import com.tvj.internaltool.entity.DepartmentEntity;
import com.tvj.internaltool.repository.DepartmentRepository;

@RunWith(MockitoJUnitRunner.class)
public class DepartmentServiceImplTest {

    @InjectMocks
    private DepartmentServiceImpl departmentService; // cannot InjectMocks interface

    @Mock
    private DepartmentRepository departmentRepository;

    // ---------- getAllDepartment START ---------

    @Test
    public void getAllDepartment_success_returnZeroRecord() {
        when(departmentRepository.findAll()).thenReturn(Collections.emptyList());

        DepartmentListResDto departmentResDto = departmentService.getAllDepartment();

        verify(departmentRepository, times(1)).findAll();
        assertEquals(departmentResDto.getDepartmentResDtoList().size(), 0);
    }

    @Test
    public void getAllDepartment_success_returnOneRecord() {
        DepartmentEntityDataDummy departmentEntityDataDummy = new DepartmentEntityDataDummy();
        List<DepartmentEntity> departmentEntityList = Collections.singletonList(
                departmentEntityDataDummy.getDepartment1());

        when(departmentRepository.findAll()).thenReturn(departmentEntityList);

        DepartmentListResDto departmentResDto = departmentService.getAllDepartment();

        verify(departmentRepository, times(1)).findAll();
        assertEquals(departmentResDto.getDepartmentResDtoList().size(), 1);
    }

    @Test
    public void getAllDepartment_success_returnTwoRecord2() {
        DepartmentEntityDataDummy departmentEntityDataDummy = new DepartmentEntityDataDummy();
        List<DepartmentEntity> departmentEntityList = Arrays.asList(
                departmentEntityDataDummy.getDepartment1(),
                departmentEntityDataDummy.getDepartment2());

        when(departmentRepository.findAll()).thenReturn(departmentEntityList);

        DepartmentListResDto departmentResDto = departmentService.getAllDepartment();

        verify(departmentRepository, times(1)).findAll();
        assertEquals(departmentResDto.getDepartmentResDtoList().size(), 2);
    }

    // ---------- getAllDepartment END ---------

}