package com.tvj.internaltool.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.Collections;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.google.gson.Gson;
import com.tvj.internaltool.dto.res.TitleListResDto;
import com.tvj.internaltool.dummy.dto.res.TitleResDtoDataDummy;
import com.tvj.internaltool.service.TitleService;
import com.tvj.internaltool.utils.CustomRestExceptionHandler;

@RunWith(MockitoJUnitRunner.class)
public class TitleControllerTest {

    @InjectMocks
    private TitleController titleController;

    @Mock
    private TitleService titleService;

    private MockMvc mockMvc;

    @Before // Execute before each test method
    public void setup() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(titleController)
                .setControllerAdvice(new CustomRestExceptionHandler()) // add ControllerAdvice to controller test
                .build();
    }

    // ---------- /title/list START ----------

    @Test
    public void getAllTitle_success_returnZeroRecord() throws Exception {
        when(titleService.getAllTitle()).thenReturn(new TitleListResDto());

        MvcResult result = mockMvc.perform(get("/title/list")).andExpect(status().isOk()).andReturn();

        String jsonString = result.getResponse().getContentAsString();
        TitleListResDto titleListResDto = new Gson().fromJson(jsonString, TitleListResDto.class);

        verify(titleService, times(1)).getAllTitle();
        assertNull(titleListResDto.getTitleResDtoList());
    }

    @Test
    public void getAllTitle_success_returnOneRecord() throws Exception {
        TitleResDtoDataDummy titleResDtoDataDummy = new TitleResDtoDataDummy();
        TitleListResDto titleListResDtoDummy = new TitleListResDto();
        titleListResDtoDummy.setTitleResDtoList(Collections.singletonList(titleResDtoDataDummy.getTitle1()));

        when(titleService.getAllTitle()).thenReturn(titleListResDtoDummy);

        MvcResult result = mockMvc.perform(get("/title/list")).andExpect(status().isOk()).andReturn();

        String jsonString = result.getResponse().getContentAsString();
        TitleListResDto titleListResDto = new Gson().fromJson(jsonString, TitleListResDto.class);

        verify(titleService, times(1)).getAllTitle();
        assertEquals(titleListResDto.getTitleResDtoList().size(), 1);
    }

    @Test
    public void getAllTitle_success_returnTwoRecords() throws Exception {
        TitleResDtoDataDummy titleResDtoDataDummy = new TitleResDtoDataDummy();
        TitleListResDto titleListResDtoDummy = new TitleListResDto();
        titleListResDtoDummy
                .setTitleResDtoList(Arrays.asList(titleResDtoDataDummy.getTitle1(), titleResDtoDataDummy.getTitle2()));

        when(titleService.getAllTitle()).thenReturn(titleListResDtoDummy);

        MvcResult result = mockMvc.perform(get("/title/list")).andExpect(status().isOk()).andReturn();

        String jsonString = result.getResponse().getContentAsString();
        TitleListResDto titleListResDto = new Gson().fromJson(jsonString, TitleListResDto.class);

        verify(titleService, times(1)).getAllTitle();
        assertEquals(titleListResDto.getTitleResDtoList().size(), 2);
    }
    
    @Test
    public void getAllTitle_invalidHttpRequestMethodPOST() throws Exception {
        mockMvc.perform(post("/title/list")).andExpect(status().isMethodNotAllowed()).andReturn();
    }

    @Test
    public void getAllTitle_invalidHttpRequestMethodPUT() throws Exception {
        mockMvc.perform(put("/title/list")).andExpect(status().isMethodNotAllowed()).andReturn();
    }
    
    @Test
    public void getAllTitle_invalidHttpRequestMethodPATCH() throws Exception {
        mockMvc.perform(patch("/title/list")).andExpect(status().isMethodNotAllowed()).andReturn();
    }
    
    @Test
    public void getAllTitle_invalidHttpRequestMethodDELETE() throws Exception {
        mockMvc.perform(delete("/title/list")).andExpect(status().isMethodNotAllowed()).andReturn();
    }
    
    // ---------- /title/list END ----------

}