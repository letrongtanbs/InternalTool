package com.tvj.internaltool.dummy.entity;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import com.tvj.internaltool.entity.RoleEntity;
import com.tvj.internaltool.entity.RolePermissionEntity;

public class RoleEntityDataDummy {

    public RoleEntity getAdminRole() {
        RolePermissionEntityDataDummy rolePermissionEntityDataDummy = new RolePermissionEntityDataDummy();
        Set<RolePermissionEntity> rolePermissionEntitySet = new HashSet<>();
        rolePermissionEntitySet.add(rolePermissionEntityDataDummy.getAdminRolePermission1());
        rolePermissionEntitySet.add(rolePermissionEntityDataDummy.getAdminRolePermission2());

        RoleEntity admin = new RoleEntity();
        admin.setRoleId("1");
        admin.setRoleName("ADMIN");
        admin.setCreatedBy("Dex");
        admin.setCreatedDate(LocalDateTime.now());
        admin.setUpdatedBy("Dexx");
        admin.setUpdatedDate(LocalDateTime.now().plusHours(1));
        admin.setDeletedBy(null);
        admin.setDeletedDate(null);
        admin.setRolePermission(rolePermissionEntitySet);

        return admin;
    }

    public RoleEntity getUserRole() {
        RolePermissionEntityDataDummy rolePermissionEntityDataDummy = new RolePermissionEntityDataDummy();
        Set<RolePermissionEntity> rolePermissionEntitySet = new HashSet<>();
        rolePermissionEntitySet.add(rolePermissionEntityDataDummy.getUserRolePermission1());

        RoleEntity user = new RoleEntity();
        user.setRoleId("2");
        user.setRoleName("USER");
        user.setCreatedBy("Dex");
        user.setCreatedDate(LocalDateTime.now());
        user.setUpdatedBy("Dexx");
        user.setUpdatedDate(LocalDateTime.now().plusHours(1));
        user.setDeletedBy(null);
        user.setDeletedDate(null);
        user.setRolePermission(rolePermissionEntitySet);

        return user;
    }

}
