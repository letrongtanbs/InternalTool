package com.tvj.internaltool.dummy.entity;

import java.time.LocalDateTime;

import com.tvj.internaltool.entity.UserStatusEntity;

public class UserStatusEntityDataDummy {

    public UserStatusEntity getAvailable() {
        UserStatusEntity status = new UserStatusEntity();
        status.setStatusId("1");
        status.setStatusName("Available");
        status.setCreatedBy("Dex");
        status.setCreatedDate(LocalDateTime.now());
        status.setUpdatedBy("Dexx");
        status.setUpdatedDate(LocalDateTime.now().plusHours(1));
        status.setDeletedBy(null);
        status.setDeletedDate(null);
        return status;
    }

    public UserStatusEntity getAway() {
        UserStatusEntity status = new UserStatusEntity();
        status.setStatusId("2");
        status.setStatusName("Away");
        status.setCreatedBy("Dex");
        status.setCreatedDate(LocalDateTime.now());
        status.setUpdatedBy("Dexx");
        status.setUpdatedDate(LocalDateTime.now().plusHours(1));
        status.setDeletedBy(null);
        status.setDeletedDate(null);
        return status;
    }

}
