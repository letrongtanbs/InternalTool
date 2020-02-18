package com.tvj.internaltool.dummy;

import com.tvj.internaltool.entity.CountryEntity;

import java.time.LocalDateTime;

public class CountryEntityDataDummy {

    public CountryEntity getCountry1() {
        CountryEntity country = new CountryEntity();
        country.setCountryId("1");
        country.setCountryName("VietNam");
        country.setCreatedBy("Dex");
        country.setCreatedDate(LocalDateTime.now());
        country.setUpdatedBy("Dexx");
        country.setUpdatedDate(LocalDateTime.now().plusHours(1));
        country.setDeletedBy(null);
        country.setDeletedDate(null);
        return country;
    }

    public CountryEntity getCountry2() {
        CountryEntity country = new CountryEntity();
        country.setCountryId("2");
        country.setCountryName("Japan");
        country.setCreatedBy("Dex");
        country.setCreatedDate(LocalDateTime.now());
        country.setUpdatedBy("Dexx");
        country.setUpdatedDate(LocalDateTime.now().plusHours(1));
        country.setDeletedBy(null);
        country.setDeletedDate(null);
        return country;
    }

}
