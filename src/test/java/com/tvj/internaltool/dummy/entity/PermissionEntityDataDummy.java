package com.tvj.internaltool.dummy.entity;

import com.tvj.internaltool.entity.PermissionEntity;

import java.time.LocalDateTime;

public class PermissionEntityDataDummy {

    public PermissionEntity getPermission1() {
        PermissionEntity admin = new PermissionEntity();
        admin.setPermissionId("1");
        admin.setPermissionName("SCREEN001");
        admin.setPermissionUrl("/user/{id}/list1/{id}");
        admin.setCreatedBy("Dex");
        admin.setCreatedDate(LocalDateTime.now());
        admin.setUpdatedBy("Dexx");
        admin.setUpdatedDate(LocalDateTime.now().plusHours(1));
        admin.setDeletedBy(null);
        admin.setDeletedDate(null);
        return admin;
    }

    public PermissionEntity getPermission2() {
        PermissionEntity admin = new PermissionEntity();
        admin.setPermissionId("2");
        admin.setPermissionName("SCREEN001");
        admin.setPermissionUrl("/user/{id}/list2/{id}");
        admin.setCreatedBy("Dex");
        admin.setCreatedDate(LocalDateTime.now());
        admin.setUpdatedBy("Dexx");
        admin.setUpdatedDate(LocalDateTime.now().plusHours(1));
        admin.setDeletedBy(null);
        admin.setDeletedDate(null);
        return admin;
    }

}
