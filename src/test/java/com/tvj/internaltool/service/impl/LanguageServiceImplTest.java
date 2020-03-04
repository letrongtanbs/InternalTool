package com.tvj.internaltool.service.impl;

import com.tvj.internaltool.dto.res.LanguageListResDto;
import com.tvj.internaltool.dummy.entity.LanguageEntityDataDummy;
import com.tvj.internaltool.entity.LanguageEntity;
import com.tvj.internaltool.repository.LanguageRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class LanguageServiceImplTest {

    @InjectMocks
    private LanguageServiceImpl languageService; // cannot InjectMocks interface

    @Mock
    private LanguageRepository languageRepository;

    // ---------- getAllLanguage START ---------

    @Test
    public void getAllLanguage_success_returnZeroRecord() {
        when(languageRepository.findAll()).thenReturn(Collections.emptyList());

        LanguageListResDto languageListResDto = languageService.getAllLanguage();

        verify(languageRepository, times(1)).findAll();
        assertEquals(languageListResDto.getLanguageResDtoList().size(), 0);
    }

    @Test
    public void getAllLanguage_success_returnOneRecord() {
        LanguageEntityDataDummy languageEntityDataDummy = new LanguageEntityDataDummy();
        List<LanguageEntity> departmentEntityList = Collections.singletonList(
                languageEntityDataDummy.getLanguage1());

        when(languageRepository.findAll()).thenReturn(departmentEntityList);

        LanguageListResDto languageListResDto = languageService.getAllLanguage();

        verify(languageRepository, times(1)).findAll();
        assertEquals(languageListResDto.getLanguageResDtoList().size(), 1);
    }

    @Test
    public void getAllLanguage_success_returnTwoRecords() {
        LanguageEntityDataDummy languageEntityDataDummy = new LanguageEntityDataDummy();
        List<LanguageEntity> departmentEntityList = Arrays.asList(
                languageEntityDataDummy.getLanguage1(),
                languageEntityDataDummy.getLanguage2());

        when(languageRepository.findAll()).thenReturn(departmentEntityList);

        LanguageListResDto languageListResDto = languageService.getAllLanguage();

        verify(languageRepository, times(1)).findAll();
        assertEquals(languageListResDto.getLanguageResDtoList().size(), 2);
    }

    // ---------- getAllLanguage END ---------

}
