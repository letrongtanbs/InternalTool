package com.tvj.internaltool.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "TBL_USER_SETTING")
@Setter
@Getter
@NoArgsConstructor
public class UserSettingEntity implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
    @Column(name = "user_setting_id")
    private String userSettingId;

    @Column(name = "team_id")
    private String teamId;

    @Column(name = "address")
    private String address;

    @Column(name = "phone")
    private String phone;

    @Column(name = "country_id")
    private String countryId;

    @Column(name = "language_id")
    private String languageId;

    @Column(name = "status_id")
    private String statusId;

    @Column(name = "avatar")
    private String avatar;

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

    @OneToOne(mappedBy = "userSettingEntity")
    private UserEntity userEntity;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "team_id", insertable = false, updatable = false) // avoid insert and update reference table when modify current table
    private TeamEntity teamEntity;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "country_id", insertable = false, updatable = false) // avoid insert and update reference table when modify current table
    private CountryEntity countryEntity;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "language_id", insertable = false, updatable = false) // avoid insert and update reference table when modify current table
    private LanguageEntity languageEntity;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "status_id", insertable = false, updatable = false) // avoid insert and update reference table when modify current table
    private UserStatusEntity userStatusEntity;

}
