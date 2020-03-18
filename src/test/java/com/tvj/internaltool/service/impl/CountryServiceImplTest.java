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

import com.tvj.internaltool.dto.res.CountryListResDto;
import com.tvj.internaltool.dummy.entity.CountryEntityDataDummy;
import com.tvj.internaltool.entity.CountryEntity;
import com.tvj.internaltool.repository.CountryRepository;

@RunWith(MockitoJUnitRunner.class)
public class CountryServiceImplTest {

    @InjectMocks
    private CountryServiceImpl countryService; // cannot InjectMocks interface

    @Mock
    private CountryRepository countryRepository;

    // ---------- getAllCountry START ---------

    @Test
    public void getAllCountry_success_returnZeroRecord() {
        when(countryRepository.findAll()).thenReturn(Collections.emptyList());

        CountryListResDto countryListResDto = countryService.getAllCountry();

        verify(countryRepository, times(1)).findAll();
        assertEquals(countryListResDto.getCountryResDtoList().size(), 0);
    }

    @Test
    public void getAllCountry_success_returnOneRecord() {
        CountryEntityDataDummy countryEntityDataDummy = new CountryEntityDataDummy();
        List<CountryEntity> countryEntityList = Collections.singletonList(
                countryEntityDataDummy.getCountry1());

        when(countryRepository.findAll()).thenReturn(countryEntityList);

        CountryListResDto countryListResDto = countryService.getAllCountry();

        verify(countryRepository, times(1)).findAll();
        assertEquals(countryListResDto.getCountryResDtoList().size(), 1);
    }

    @Test
    public void getAllCountry_success_returnTwoRecords() {
        CountryEntityDataDummy countryEntityDataDummy = new CountryEntityDataDummy();
        List<CountryEntity> countryEntityList = Arrays.asList(
                countryEntityDataDummy.getCountry1(),
                countryEntityDataDummy.getCountry2());

        when(countryRepository.findAll()).thenReturn(countryEntityList);

        CountryListResDto countryListResDto = countryService.getAllCountry();

        verify(countryRepository, times(1)).findAll();
        assertEquals(countryListResDto.getCountryResDtoList().size(), 2);
    }

    // ---------- getAllCountry END ---------

}
