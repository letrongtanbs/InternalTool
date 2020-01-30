package com.tvj.internaltool.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@Setter
@Getter
@NoArgsConstructor
public class RolePermissionId implements Serializable {

    @Column(name = "role_id")
    private String roleId;

    @Column(name = "permission_id")
    private String permissionId;
}
