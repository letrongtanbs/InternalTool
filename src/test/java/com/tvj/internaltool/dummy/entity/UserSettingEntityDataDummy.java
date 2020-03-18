package com.tvj.internaltool.dummy.entity;

import java.time.LocalDateTime;

import com.tvj.internaltool.entity.UserSettingEntity;

public class UserSettingEntityDataDummy {
    
    CountryEntityDataDummy countryEntityDataDummy = new CountryEntityDataDummy();
    LanguageEntityDataDummy languageEntityDataDummy = new LanguageEntityDataDummy();
    TeamEntityDataDummy teamEntityDataDummy = new TeamEntityDataDummy();
    UserStatusEntityDataDummy userStatusEntityDataDummy = new UserStatusEntityDataDummy();

    public UserSettingEntity getAdminUserSetting1() {
        UserSettingEntity admin = new UserSettingEntity();
        admin.setUserSettingId("1");
        admin.setTeamId("1");
        admin.setAddress("Ha Noi");
        admin.setPhone("0987654321");
        admin.setCountryId("1");
        admin.setLanguageId("1");
        admin.setStatusId("1");
        admin.setAvatar("This/is/base64/String");
        admin.setCreatedBy("Dex");
        admin.setCreatedDate(LocalDateTime.now());
        admin.setUpdatedBy("Dexx");
        admin.setUpdatedDate(LocalDateTime.now().plusHours(1));
        admin.setDeletedBy(null);
        admin.setDeletedDate(null);
        admin.setTeamEntity(teamEntityDataDummy.getTeam1());
        admin.setCountryEntity(countryEntityDataDummy.getCountry1());
        admin.setLanguageEntity(languageEntityDataDummy.getLanguage1());
        admin.setUserStatusEntity(userStatusEntityDataDummy.getAvailable());
        return admin;
    }

    public UserSettingEntity getAdminUserSetting2() {
        UserSettingEntity admin = new UserSettingEntity();
        admin.setUserSettingId("2");
        admin.setTeamId("2");
        admin.setAddress("Ha Noi");
        admin.setPhone("0987654321");
        admin.setCountryId("2");
        admin.setLanguageId("2");
        admin.setStatusId("1");
        admin.setAvatar("This/is/base64/String");
        admin.setCreatedBy("Dex");
        admin.setCreatedDate(LocalDateTime.now());
        admin.setUpdatedBy("Dexx");
        admin.setUpdatedDate(LocalDateTime.now().plusHours(1));
        admin.setDeletedBy(null);
        admin.setDeletedDate(null);
        admin.setTeamEntity(teamEntityDataDummy.getTeam2());
        admin.setCountryEntity(countryEntityDataDummy.getCountry2());
        admin.setLanguageEntity(languageEntityDataDummy.getLanguage2());
        admin.setUserStatusEntity(userStatusEntityDataDummy.getAvailable());
        return admin;
    }

    public UserSettingEntity getUserUserSetting1() {
        UserSettingEntity user = new UserSettingEntity();
        user.setUserSettingId("3");
        user.setTeamId("3");
        user.setAddress("Ha Noi");
        user.setPhone("0987654321");
        user.setCountryId("1");
        user.setLanguageId("1");
        user.setStatusId("1");
        user.setAvatar("This/is/base64/String");
        user.setCreatedBy("Dex");
        user.setCreatedDate(LocalDateTime.now());
        user.setUpdatedBy("Dexx");
        user.setUpdatedDate(LocalDateTime.now().plusHours(1));
        user.setDeletedBy(null);
        user.setDeletedDate(null);
        user.setTeamEntity(teamEntityDataDummy.getTeam3());
        user.setCountryEntity(countryEntityDataDummy.getCountry1());
        user.setLanguageEntity(languageEntityDataDummy.getLanguage1());
        user.setUserStatusEntity(userStatusEntityDataDummy.getAvailable());
        return user;
    }
}
