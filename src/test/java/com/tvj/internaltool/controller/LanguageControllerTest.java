package com.tvj.internaltool.controller;

import com.google.gson.Gson;
import com.tvj.internaltool.dto.res.LanguageListResDto;
import com.tvj.internaltool.dummy.dto.res.LanguageResDtoDataDummy;
import com.tvj.internaltool.service.LanguageService;
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
public class LanguageControllerTest {

    @InjectMocks
    private LanguageController languageController;

    @Mock
    private LanguageService languageService;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(languageController).build();
    }

    // ---------- /language/list START ----------

    @Test
    public void getAllLanguage_success_returnZeroRecord() throws Exception {
        when(languageService.getAllLanguage()).thenReturn(new LanguageListResDto());

        MvcResult result = mockMvc.perform(get("/language/list"))
                .andExpect(status().isOk())
                .andReturn();

        String jsonString = result.getResponse().getContentAsString();
        LanguageListResDto languageListResDto = new Gson().fromJson(jsonString, LanguageListResDto.class);

        verify(languageService, times(1)).getAllLanguage();
        assertNull(languageListResDto.getLanguageResDtoList());
    }

    @Test
    public void getAllLanguage_success_returnOneRecord() throws Exception {
        LanguageResDtoDataDummy languageResDtoDataDummy = new LanguageResDtoDataDummy();
        LanguageListResDto languageListResDtoDummy = new LanguageListResDto();
        languageListResDtoDummy.setLanguageResDtoList(Collections.singletonList(
                languageResDtoDataDummy.getLanguage1()));

        when(languageService.getAllLanguage()).thenReturn(languageListResDtoDummy);

        MvcResult result = mockMvc.perform(get("/language/list"))
                .andExpect(status().isOk())
                .andReturn();

        String jsonString = result.getResponse().getContentAsString();
        LanguageListResDto languageListResDto = new Gson().fromJson(jsonString, LanguageListResDto.class);

        verify(languageService, times(1)).getAllLanguage();
        assertEquals(languageListResDto.getLanguageResDtoList().size(), 1);
    }

    @Test
    public void getAllLanguage_success_returnTwoRecords() throws Exception {
        LanguageResDtoDataDummy languageResDtoDataDummy = new LanguageResDtoDataDummy();
        LanguageListResDto languageListResDtoDummy = new LanguageListResDto();
        languageListResDtoDummy.setLanguageResDtoList(Arrays.asList(
                languageResDtoDataDummy.getLanguage1(),
                languageResDtoDataDummy.getLanguage2()));

        when(languageService.getAllLanguage()).thenReturn(languageListResDtoDummy);

        MvcResult result = mockMvc.perform(get("/language/list"))
                .andExpect(status().isOk())
                .andReturn();

        String jsonString = result.getResponse().getContentAsString();
        LanguageListResDto languageListResDto = new Gson().fromJson(jsonString, LanguageListResDto.class);

        verify(languageService, times(1)).getAllLanguage();
        assertEquals(languageListResDto.getLanguageResDtoList().size(), 2);
    }

    // ---------- /language/list END ----------

}
