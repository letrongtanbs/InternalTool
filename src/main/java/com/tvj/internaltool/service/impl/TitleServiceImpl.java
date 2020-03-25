package com.tvj.internaltool.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.tvj.internaltool.dto.res.TitleListResDto;
import com.tvj.internaltool.dto.res.TitleResDto;
import com.tvj.internaltool.entity.TitleEntity;
import com.tvj.internaltool.repository.TitleRepository;
import com.tvj.internaltool.service.TitleService;
import com.tvj.internaltool.utils.ModelMapperUtils;

@Service
public class TitleServiceImpl implements TitleService {

    private final TitleRepository titleRepository;

    public TitleServiceImpl(TitleRepository titleRepository) {
        this.titleRepository = titleRepository;
    }

    @Override
    public TitleListResDto getAllTitle() {
        List<TitleEntity> titleEntityList = titleRepository.findAll();
        List<TitleResDto> titleResDtoList = ModelMapperUtils.mapAll(titleEntityList, TitleResDto.class);
        return new TitleListResDto(titleResDtoList);
    }

}
