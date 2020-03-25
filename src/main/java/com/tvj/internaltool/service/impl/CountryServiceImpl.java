package com.tvj.internaltool.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.tvj.internaltool.dto.res.CountryListResDto;
import com.tvj.internaltool.dto.res.CountryResDto;
import com.tvj.internaltool.entity.CountryEntity;
import com.tvj.internaltool.repository.CountryRepository;
import com.tvj.internaltool.service.CountryService;
import com.tvj.internaltool.utils.ModelMapperUtils;

@Service
public class CountryServiceImpl implements CountryService {

    private final CountryRepository countryRepository;

    public CountryServiceImpl(CountryRepository countryRepository) {
        this.countryRepository = countryRepository;
    }

    @Override
    public CountryListResDto getAllCountry() {
        List<CountryEntity> countryEntityList = countryRepository.findAll();
        List<CountryResDto> countryResDtoList = ModelMapperUtils.mapAll(countryEntityList, CountryResDto.class);
        return new CountryListResDto(countryResDtoList);
    }

}
