package com.tvj.internaltool.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "TBL_USER")
@Setter
@Getter
@NoArgsConstructor
public class UserEntity implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
    @Column(name = "user_id")
    private String userId;

    @Column(name = "user_setting_id")
    private String userSettingId;

    @Column(name = "role_id")
    private String roleId;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "title_id")
    private String titleId;

    @Column(name = "email")
    private String email;

    @Column(name = "is_activated")
    private boolean isActivated;

    @Column(name = "is_first_time_login")
    private boolean isFirstTimeLogin;

    @Column(name = "login_fail_count")
    private int loginFailCount;

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
    
    @Column(name = "last_login")
    private LocalDateTime lastLogin;
    
    @Column(name = "last_logout")
    private LocalDateTime lastLogout;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="role_id", insertable = false, updatable = false) // avoid insert and update reference table when modify current table
    private RoleEntity role;

    @OneToOne(cascade = CascadeType.ALL) // When we perform some action on the target entity, the same action will be applied to the associated entity.
    @JoinColumn(name = "user_setting_id", insertable = false, updatable = false) // avoid insert and update reference table when modify current table
    private UserSettingEntity userSettingEntity;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "title_id", insertable = false, updatable = false) // avoid insert and update reference table when modify current table
    private TitleEntity titleEntity;

}
