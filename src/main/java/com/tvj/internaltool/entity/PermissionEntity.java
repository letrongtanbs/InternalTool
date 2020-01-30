package com.tvj.internaltool.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "TBL_PERMISSION")
@Setter
@Getter
@NoArgsConstructor
public class PermissionEntity implements Serializable {

    @Id
    @Column(name = "permission_id")
    private String permissionId;

    @Column(name = "permission_name")
    private String permissionName;

    @Column(name = "permission_code")
    private String permissionCode;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "created_date")
    private LocalDateTime createdDate;

    @Column(name = "updated_by")
    private String updatedBy;

    @Column(name = "updated_date")
    private LocalDateTime updatedDate;

    @Column(name = "deleted_by")
    private String deletedBy;

    @Column(name = "deleted_date")
    private LocalDateTime deletedDate;

    @OneToMany(mappedBy = "permission", fetch = FetchType.EAGER)
    Set<RolePermissionEntity> rolePermission;

}
