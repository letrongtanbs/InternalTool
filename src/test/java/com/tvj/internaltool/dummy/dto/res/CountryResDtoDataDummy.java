package com.tvj.internaltool.dummy.dto.res;

import com.tvj.internaltool.dto.res.CountryResDto;

public class CountryResDtoDataDummy {

    public CountryResDto getCountry1() {
        CountryResDto countryResDto = new CountryResDto();
        countryResDto.setCountryId("1");
        countryResDto.setCountryName("Vietnam");
        return countryResDto;
    }

    public CountryResDto getCountry2() {
        CountryResDto countryResDto = new CountryResDto();
        countryResDto.setCountryId("2");
        countryResDto.setCountryName("Japan");
        return countryResDto;
    }

}
