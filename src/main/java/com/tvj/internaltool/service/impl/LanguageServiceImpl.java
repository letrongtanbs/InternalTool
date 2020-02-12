package com.tvj.internaltool.service.impl;

import com.tvj.internaltool.dto.res.LanguageListResDto;
import com.tvj.internaltool.dto.res.LanguageResDto;
import com.tvj.internaltool.entity.LanguageEntity;
import com.tvj.internaltool.repository.LanguageRepository;
import com.tvj.internaltool.service.LanguageService;
import com.tvj.internaltool.utils.ModelMapperUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LanguageServiceImpl implements LanguageService {

    private final LanguageRepository languageRepository;

    public LanguageServiceImpl(LanguageRepository languageRepository) {
        this.languageRepository = languageRepository;
    }


    @Override
    public LanguageListResDto getAllLanguage() {
        List<LanguageEntity> languageEntityList = languageRepository.findAll();
        List<LanguageResDto> languageResDtoList = ModelMapperUtils.mapAll(languageEntityList, LanguageResDto.class);
        return new LanguageListResDto(languageResDtoList);
    }
}
