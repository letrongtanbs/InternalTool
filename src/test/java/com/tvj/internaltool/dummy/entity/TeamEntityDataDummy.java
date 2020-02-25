package com.tvj.internaltool.dummy.entity;

import com.tvj.internaltool.entity.TeamEntity;

import java.time.LocalDateTime;

public class TeamEntityDataDummy {

    public TeamEntity getTeam1() {
        DepartmentEntityDataDummy departmentEntityDataDummy = new DepartmentEntityDataDummy();

        TeamEntity teamEntity = new TeamEntity();
        teamEntity.setTeamId("1");
        teamEntity.setTeamCode("TEAM001");
        teamEntity.setTeamName("Team 1");
        teamEntity.setDepartmentId("1");
        teamEntity.setCreatedBy("Dex");
        teamEntity.setCreatedDate(LocalDateTime.now());
        teamEntity.setUpdatedBy("Dexx");
        teamEntity.setUpdatedDate(LocalDateTime.now().plusHours(1));
        teamEntity.setDeletedBy(null);
        teamEntity.setDeletedDate(null);
        teamEntity.setDepartmentEntity(departmentEntityDataDummy.getDepartment1());
        return teamEntity;
    }

    public TeamEntity getTeam2() {
        DepartmentEntityDataDummy departmentEntityDataDummy = new DepartmentEntityDataDummy();

        TeamEntity teamEntity = new TeamEntity();
        teamEntity.setTeamId("2");
        teamEntity.setTeamCode("TEAM002");
        teamEntity.setTeamName("Team 2");
        teamEntity.setDepartmentId("1");
        teamEntity.setCreatedBy("Dex");
        teamEntity.setCreatedDate(LocalDateTime.now());
        teamEntity.setUpdatedBy("Dexx");
        teamEntity.setUpdatedDate(LocalDateTime.now().plusHours(1));
        teamEntity.setDeletedBy(null);
        teamEntity.setDeletedDate(null);
        teamEntity.setDepartmentEntity(departmentEntityDataDummy.getDepartment1());
        return teamEntity;
    }

    public TeamEntity getTeam3() {
        DepartmentEntityDataDummy departmentEntityDataDummy = new DepartmentEntityDataDummy();

        TeamEntity teamEntity = new TeamEntity();
        teamEntity.setTeamId("3");
        teamEntity.setTeamCode("TEAM003");
        teamEntity.setTeamName("Team 3");
        teamEntity.setDepartmentId("2");
        teamEntity.setCreatedBy("Dex");
        teamEntity.setCreatedDate(LocalDateTime.now());
        teamEntity.setUpdatedBy("Dexx");
        teamEntity.setUpdatedDate(LocalDateTime.now().plusHours(1));
        teamEntity.setDeletedBy(null);
        teamEntity.setDeletedDate(null);
        teamEntity.setDepartmentEntity(departmentEntityDataDummy.getDepartment2());
        return teamEntity;
    }

}
