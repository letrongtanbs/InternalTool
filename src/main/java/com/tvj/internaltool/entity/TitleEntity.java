package com.tvj.internaltool.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "TBL_TITLE")
@Setter
@Getter
@NoArgsConstructor
public class TitleEntity implements Serializable {

    @Id
    @Column(name = "title_id")
    private String titleId;

    @Column(name = "title_code")
    private String titleCode;

    @Column(name = "title_name")
    private String titleName;

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

    @OneToMany(mappedBy = "titleEntity", fetch = FetchType.EAGER)
    private Set<UserEntity> userEntity;

}
