package com.tvj.internaltool.dummy.entity;

import com.tvj.internaltool.entity.RolePermissionEntity;
import com.tvj.internaltool.entity.RolePermissionId;

public class RolePermissionEntityDataDummy {

    public RolePermissionEntity getAdminRolePermission1() {
        RolePermissionEntity rolePermissionEntity = new RolePermissionEntity();
        PermissionEntityDataDummy permissionEntityDataDummy = new PermissionEntityDataDummy();

        RolePermissionId rolePermissionId = new RolePermissionId();
        rolePermissionId.setRoleId("1");
        rolePermissionId.setPermissionId("1");

        rolePermissionEntity.setId(rolePermissionId);
        rolePermissionEntity.setPermission(permissionEntityDataDummy.getPermission1());
        return rolePermissionEntity;
    }

    public RolePermissionEntity getAdminRolePermission2() {
        RolePermissionEntity rolePermissionEntity = new RolePermissionEntity();
        PermissionEntityDataDummy permissionEntityDataDummy = new PermissionEntityDataDummy();

        RolePermissionId rolePermissionId = new RolePermissionId();
        rolePermissionId.setRoleId("1");
        rolePermissionId.setPermissionId("2");

        rolePermissionEntity.setId(rolePermissionId);
        rolePermissionEntity.setPermission(permissionEntityDataDummy.getPermission2());
        return rolePermissionEntity;
    }

    public RolePermissionEntity getUserRolePermission1() {
        RolePermissionEntity rolePermissionEntity = new RolePermissionEntity();
        PermissionEntityDataDummy permissionEntityDataDummy = new PermissionEntityDataDummy();

        RolePermissionId rolePermissionId = new RolePermissionId();
        rolePermissionId.setRoleId("2");
        rolePermissionId.setPermissionId("1");

        rolePermissionEntity.setId(rolePermissionId);
        rolePermissionEntity.setPermission(permissionEntityDataDummy.getPermission1());
        return rolePermissionEntity;
    }

}
