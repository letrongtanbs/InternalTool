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

import com.tvj.internaltool.dto.res.TitleListResDto;
import com.tvj.internaltool.dummy.entity.TitleEntityDataDummy;
import com.tvj.internaltool.entity.TitleEntity;
import com.tvj.internaltool.repository.TitleRepository;

@RunWith(MockitoJUnitRunner.class)
public class TitleServiceImplTest {

    @InjectMocks
    private TitleServiceImpl titleService; // cannot InjectMocks interface

    @Mock
    private TitleRepository titleRepository;

    // ---------- getAllTitle START ---------

    @Test
    public void getAllTitle_success_returnZeroRecord() {
        when(titleRepository.findAll()).thenReturn(Collections.emptyList());

        TitleListResDto titleListResDto = titleService.getAllTitle();

        verify(titleRepository, times(1)).findAll();
        assertEquals(titleListResDto.getTitleResDtoList().size(), 0);
    }

    @Test
    public void getAllTitle_success_returnOneRecord() {
        TitleEntityDataDummy titleEntityDataDummy = new TitleEntityDataDummy();
        List<TitleEntity> titleEntityList = Collections.singletonList(
                titleEntityDataDummy.getTitle1());

        when(titleRepository.findAll()).thenReturn(titleEntityList);

        TitleListResDto titleListResDto = titleService.getAllTitle();

        verify(titleRepository, times(1)).findAll();
        assertEquals(titleListResDto.getTitleResDtoList().size(), 1);
    }

    @Test
    public void getAllTitle_success_returnTwoRecords() {
        TitleEntityDataDummy titleEntityDataDummy = new TitleEntityDataDummy();
        List<TitleEntity> titleEntityList = Arrays.asList(
                titleEntityDataDummy.getTitle1(),
                titleEntityDataDummy.getTitle2());

        when(titleRepository.findAll()).thenReturn(titleEntityList);

        TitleListResDto titleListResDto = titleService.getAllTitle();

        verify(titleRepository, times(1)).findAll();
        assertEquals(titleListResDto.getTitleResDtoList().size(), 2);
    }

    // ---------- getAllTitle END ---------

}
