package com.tvj.internaltool.repository;

import com.tvj.internaltool.entity.RolePermissionEntity;
import com.tvj.internaltool.entity.RolePermissionId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RolePermissionRepository extends JpaRepository<RolePermissionEntity, RolePermissionId> {


}
