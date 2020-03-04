package com.tvj.internaltool.controller;

import com.google.gson.Gson;
import com.tvj.internaltool.dto.res.DepartmentListResDto;
import com.tvj.internaltool.dummy.dto.res.DepartmentResDtoDataDummy;
import com.tvj.internaltool.service.DepartmentService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.Collections;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(MockitoJUnitRunner.class)
public class DepartmentControllerTest {

    @InjectMocks
    private DepartmentController departmentController;

    @Mock
    private DepartmentService departmentService;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(departmentController).build();
    }

    // ---------- /department/list START ----------

    @Test
    public void getAllDepartment_success_returnZeroRecord() throws Exception {
        when(departmentService.getAllDepartment()).thenReturn(new DepartmentListResDto());

        MvcResult result = mockMvc.perform(get("/department/list"))
                .andExpect(status().isOk())
                .andReturn();

        String jsonString = result.getResponse().getContentAsString();
        DepartmentListResDto departmentListResDto = new Gson().fromJson(jsonString, DepartmentListResDto.class);

        verify(departmentService, times(1)).getAllDepartment();
        assertNull(departmentListResDto.getDepartmentResDtoList());
    }

    @Test
    public void getAllDepartment_success_returnOneRecord() throws Exception {
        DepartmentResDtoDataDummy departmentResDtoDataDummy = new DepartmentResDtoDataDummy();
        DepartmentListResDto departmentListResDtoDummy = new DepartmentListResDto();
        departmentListResDtoDummy.setDepartmentResDtoList(Collections.singletonList(
                departmentResDtoDataDummy.getDepartment1()));

        when(departmentService.getAllDepartment()).thenReturn(departmentListResDtoDummy);

        MvcResult result = mockMvc.perform(get("/department/list"))
                .andExpect(status().isOk())
                .andReturn();

        String jsonString = result.getResponse().getContentAsString();
        DepartmentListResDto departmentListResDto = new Gson().fromJson(jsonString, DepartmentListResDto.class);

        verify(departmentService, times(1)).getAllDepartment();
        assertEquals(departmentListResDto.getDepartmentResDtoList().size(), 1);
    }

    @Test
    public void getAllDepartment_success_returnTwoRecords() throws Exception {
        DepartmentResDtoDataDummy departmentResDtoDataDummy = new DepartmentResDtoDataDummy();
        DepartmentListResDto departmentListResDtoDummy = new DepartmentListResDto();
        departmentListResDtoDummy.setDepartmentResDtoList(Arrays.asList(
                departmentResDtoDataDummy.getDepartment1(),
                departmentResDtoDataDummy.getDepartment2()));

        when(departmentService.getAllDepartment()).thenReturn(departmentListResDtoDummy);

        MvcResult result = mockMvc.perform(get("/department/list"))
                .andExpect(status().isOk())
                .andReturn();

        String jsonString = result.getResponse().getContentAsString();
        DepartmentListResDto departmentListResDto = new Gson().fromJson(jsonString, DepartmentListResDto.class);

        verify(departmentService, times(1)).getAllDepartment();
        assertEquals(departmentListResDto.getDepartmentResDtoList().size(), 2);

    }

    // ---------- /department/list END ----------

}