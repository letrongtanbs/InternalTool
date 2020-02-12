package com.tvj.internaltool.repository;

import com.tvj.internaltool.entity.TeamEntity;

import java.util.List;

public interface TeamRepository extends CustomRepository<TeamEntity, String> {

    List<TeamEntity> findByDepartmentId(String departmentId);

}
