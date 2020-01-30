package com.tvj.internaltool.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "TBL_ROLE_PERMISSION")
@Setter
@Getter
@NoArgsConstructor
public class RolePermissionEntity implements Serializable {

    @EmbeddedId
    private RolePermissionId id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "role_id", insertable = false, updatable = false) // avoid insert and update reference table when modify current table
    private RoleEntity role;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "permission_id", insertable = false, updatable = false) // avoid insert and update reference table when modify current table
    private PermissionEntity permission;

}
