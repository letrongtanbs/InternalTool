package com.tvj.internaltool.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "TBL_TEAM")
@Setter
@Getter
@NoArgsConstructor
public class TeamEntity implements Serializable {

    @Id
    @Column(name = "team_id")
    private String teamId;

    @Column(name = "team_code")
    private String teamCode;

    @Column(name = "team_name")
    private String teamName;

    @Column(name = "department_id")
    private String departmentId;

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

    @OneToMany(mappedBy = "teamEntity", fetch = FetchType.EAGER)
    Set<UserSettingEntity> userSettingEntity;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "department_id", insertable = false, updatable = false) // avoid insert and update reference table when modify current table
    private DepartmentEntity departmentEntity;

}
