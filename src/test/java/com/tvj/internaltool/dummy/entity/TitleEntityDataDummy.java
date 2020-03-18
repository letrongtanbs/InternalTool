package com.tvj.internaltool.dummy.entity;

import java.time.LocalDateTime;

import com.tvj.internaltool.entity.TitleEntity;

public class TitleEntityDataDummy {

    public TitleEntity getTitle1() {
        TitleEntity titleEntity = new TitleEntity();
        titleEntity.setTitleId("1");
        titleEntity.setTitleCode("DEV001");
        titleEntity.setTitleName("Developer level 1");
        titleEntity.setCreatedBy("Dex");
        titleEntity.setCreatedDate(LocalDateTime.now());
        titleEntity.setUpdatedBy("Dexx");
        titleEntity.setUpdatedDate(LocalDateTime.now().plusHours(1));
        titleEntity.setDeletedBy(null);
        titleEntity.setDeletedDate(null);
        return titleEntity;
    }

    public TitleEntity getTitle2() {
        TitleEntity titleEntity = new TitleEntity();
        titleEntity.setTitleId("2");
        titleEntity.setTitleCode("DEV002");
        titleEntity.setTitleName("Developer level 2");
        titleEntity.setCreatedBy("Dex");
        titleEntity.setCreatedDate(LocalDateTime.now());
        titleEntity.setUpdatedBy("Dexx");
        titleEntity.setUpdatedDate(LocalDateTime.now().plusHours(1));
        titleEntity.setDeletedBy(null);
        titleEntity.setDeletedDate(null);
        return titleEntity;
    }

    public TitleEntity getTitle3() {
        TitleEntity titleEntity = new TitleEntity();
        titleEntity.setTitleId("3");
        titleEntity.setTitleCode("DEV003");
        titleEntity.setTitleName("Developer level 3");
        titleEntity.setCreatedBy("Dex");
        titleEntity.setCreatedDate(LocalDateTime.now());
        titleEntity.setUpdatedBy("Dexx");
        titleEntity.setUpdatedDate(LocalDateTime.now().plusHours(1));
        titleEntity.setDeletedBy(null);
        titleEntity.setDeletedDate(null);
        return titleEntity;
    }

}
