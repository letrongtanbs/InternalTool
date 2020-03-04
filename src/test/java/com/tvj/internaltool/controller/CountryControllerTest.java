package com.tvj.internaltool.controller;

import com.google.gson.Gson;
import com.tvj.internaltool.dto.res.CountryListResDto;
import com.tvj.internaltool.dummy.dto.res.CountryResDtoDataDummy;
import com.tvj.internaltool.service.CountryService;
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
public class CountryControllerTest {

    @InjectMocks
    private CountryController countryController;

    @Mock
    private CountryService countryService;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(countryController).build();
    }

    // ---------- /country/list START ----------

    @Test
    public void getAllCountry_success_returnZeroRecord() throws Exception {
        when(countryService.getAllCountry()).thenReturn(new CountryListResDto());

        MvcResult result = mockMvc.perform(get("/country/list"))
                .andExpect(status().isOk())
                .andReturn();

        String jsonString = result.getResponse().getContentAsString();
        CountryListResDto countryListResDto = new Gson().fromJson(jsonString, CountryListResDto.class);

        verify(countryService, times(1)).getAllCountry();
        assertNull(countryListResDto.getCountryResDtoList());
    }

    @Test
    public void getAllCountry_success_returnOneRecord() throws Exception {
        CountryResDtoDataDummy countryResDtoDataDummy = new CountryResDtoDataDummy();
        CountryListResDto countryListResDtoDummy = new CountryListResDto();
        countryListResDtoDummy.setCountryResDtoList(Collections.singletonList(
                countryResDtoDataDummy.getCountry1()));

        when(countryService.getAllCountry()).thenReturn(countryListResDtoDummy);

        MvcResult result = mockMvc.perform(get("/country/list"))
                .andExpect(status().isOk())
                .andReturn();

        String jsonString = result.getResponse().getContentAsString();
        CountryListResDto countryListResDto = new Gson().fromJson(jsonString, CountryListResDto.class);

        verify(countryService, times(1)).getAllCountry();
        assertEquals(countryListResDto.getCountryResDtoList().size(), 1);
    }

    @Test
    public void getAllCountry_success_returnTwoRecords() throws Exception {
        CountryResDtoDataDummy countryResDtoDataDummy = new CountryResDtoDataDummy();
        CountryListResDto countryListResDtoDummy = new CountryListResDto();
        countryListResDtoDummy.setCountryResDtoList(Arrays.asList(
                countryResDtoDataDummy.getCountry1(),
                countryResDtoDataDummy.getCountry2()));

        when(countryService.getAllCountry()).thenReturn(countryListResDtoDummy);

        MvcResult result = mockMvc.perform(get("/country/list"))
                .andExpect(status().isOk())
                .andReturn();

        String jsonString = result.getResponse().getContentAsString();
        CountryListResDto countryListResDto = new Gson().fromJson(jsonString, CountryListResDto.class);

        verify(countryService, times(1)).getAllCountry();
        assertEquals(countryListResDto.getCountryResDtoList().size(), 2);
    }

    // ---------- /country/list END ----------

}