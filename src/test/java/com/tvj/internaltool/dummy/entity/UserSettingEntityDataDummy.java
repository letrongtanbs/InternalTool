package com.tvj.internaltool.dummy.entity;

import com.tvj.internaltool.entity.UserSettingEntity;

import java.time.LocalDateTime;

public class UserSettingEntityDataDummy {

    public UserSettingEntity getAdminUserSetting1() {
        CountryEntityDataDummy countryEntityDataDummy = new CountryEntityDataDummy();
        LanguageEntityDataDummy languageEntityDataDummy = new LanguageEntityDataDummy();
        TeamEntityDataDummy teamEntityDataDummy = new TeamEntityDataDummy();

        UserSettingEntity admin = new UserSettingEntity();
        admin.setUserSettingId("1");
        admin.setTeamId("1");
        admin.setTitle("Moderator");
        admin.setAddress("Ha Noi");
        admin.setPhone("0987654321");
        admin.setCountryId("1");
        admin.setLanguageId("1");
        admin.setStatus(1);
        admin.setAvatar("F:\\TVJ\\file_upload\\avatar\\cv.jpg");
        admin.setCreatedBy("Dex");
        admin.setCreatedDate(LocalDateTime.now());
        admin.setUpdatedBy("Dexx");
        admin.setUpdatedDate(LocalDateTime.now().plusHours(1));
        admin.setDeletedBy(null);
        admin.setDeletedDate(null);
        admin.setTeamEntity(teamEntityDataDummy.getTeam1());
        admin.setCountryEntity(countryEntityDataDummy.getCountry1());
        admin.setLanguageEntity(languageEntityDataDummy.getLanguage1());
        return admin;
    }

    public UserSettingEntity getAdminUserSetting2() {
        CountryEntityDataDummy countryEntityDataDummy = new CountryEntityDataDummy();
        LanguageEntityDataDummy languageEntityDataDummy = new LanguageEntityDataDummy();
        TeamEntityDataDummy teamEntityDataDummy = new TeamEntityDataDummy();

        UserSettingEntity admin = new UserSettingEntity();
        admin.setUserSettingId("2");
        admin.setTeamId("2");
        admin.setTitle("Moderator2");
        admin.setAddress("Ha Noi");
        admin.setPhone("0987654321");
        admin.setCountryId("2");
        admin.setLanguageId("2");
        admin.setStatus(1);
        admin.setAvatar("F:\\TVJ\\file_upload\\avatar\\cv.jpg");
        admin.setCreatedBy("Dex");
        admin.setCreatedDate(LocalDateTime.now());
        admin.setUpdatedBy("Dexx");
        admin.setUpdatedDate(LocalDateTime.now().plusHours(1));
        admin.setDeletedBy(null);
        admin.setDeletedDate(null);
        admin.setTeamEntity(teamEntityDataDummy.getTeam2());
        admin.setCountryEntity(countryEntityDataDummy.getCountry2());
        admin.setLanguageEntity(languageEntityDataDummy.getLanguage2());
        return admin;
    }

    public UserSettingEntity getUserUserSetting1() {
        CountryEntityDataDummy countryEntityDataDummy = new CountryEntityDataDummy();
        LanguageEntityDataDummy languageEntityDataDummy = new LanguageEntityDataDummy();
        TeamEntityDataDummy teamEntityDataDummy = new TeamEntityDataDummy();

        UserSettingEntity user = new UserSettingEntity();
        user.setUserSettingId("3");
        user.setTeamId("3");
        user.setTitle("Dev1");
        user.setAddress("Ha Noi");
        user.setPhone("0987654321");
        user.setCountryId("1");
        user.setLanguageId("1");
        user.setStatus(1);
        user.setAvatar("F:\\TVJ\\file_upload\\avatar\\cv.jpg");
        user.setCreatedBy("Dex");
        user.setCreatedDate(LocalDateTime.now());
        user.setUpdatedBy("Dexx");
        user.setUpdatedDate(LocalDateTime.now().plusHours(1));
        user.setDeletedBy(null);
        user.setDeletedDate(null);
        user.setTeamEntity(teamEntityDataDummy.getTeam3());
        user.setCountryEntity(countryEntityDataDummy.getCountry1());
        user.setLanguageEntity(languageEntityDataDummy.getLanguage1());
        return user;
    }
}