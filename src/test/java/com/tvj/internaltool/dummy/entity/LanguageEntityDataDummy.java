package com.tvj.internaltool.dummy.entity;

import java.time.LocalDateTime;

import com.tvj.internaltool.entity.LanguageEntity;

public class LanguageEntityDataDummy {

    public LanguageEntity getLanguage1() {
        LanguageEntity languageEntity = new LanguageEntity();
        languageEntity.setLanguageId("1");
        languageEntity.setLanguageName("VietNamese");
        languageEntity.setCreatedBy("Dex");
        languageEntity.setCreatedDate(LocalDateTime.now());
        languageEntity.setUpdatedBy("Dexx");
        languageEntity.setUpdatedDate(LocalDateTime.now().plusHours(1));
        languageEntity.setDeletedBy(null);
        languageEntity.setDeletedDate(null);
        return languageEntity;
    }

    public LanguageEntity getLanguage2() {
        LanguageEntity languageEntity = new LanguageEntity();
        languageEntity.setLanguageId("2");
        languageEntity.setLanguageName("Japanese");
        languageEntity.setCreatedBy("Dex");
        languageEntity.setCreatedDate(LocalDateTime.now());
        languageEntity.setUpdatedBy("Dexx");
        languageEntity.setUpdatedDate(LocalDateTime.now().plusHours(1));
        languageEntity.setDeletedBy(null);
        languageEntity.setDeletedDate(null);
        return languageEntity;
    }

}
