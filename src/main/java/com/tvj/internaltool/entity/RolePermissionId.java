package com.tvj.internaltool.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

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
