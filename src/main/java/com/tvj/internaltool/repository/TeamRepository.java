package com.tvj.internaltool.repository;

import java.util.List;

import com.tvj.internaltool.entity.TeamEntity;

public interface TeamRepository extends CustomRepository<TeamEntity, String> {

    List<TeamEntity> findByDepartmentId(String departmentId);

}
