package com.tvj.internaltool.entity;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "TBL_ROLE_PERMISSION")
@Setter
@Getter
@NoArgsConstructor
public class RolePermissionEntity implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@EmbeddedId
    private RolePermissionId id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "role_id", insertable = false, updatable = false) // avoid insert and update reference table when modify current table
    private RoleEntity role;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "permission_id", insertable = false, updatable = false) // avoid insert and update reference table when modify current table
    private PermissionEntity permission;

}
