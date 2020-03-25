package com.tvj.internaltool.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.Data;

@Embeddable
@Data
public class RolePermissionId implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Column(name = "role_id")
    private String roleId;

    @Column(name = "permission_id")
    private String permissionId;
}
