package com.tvj.internaltool.dummy.entity;

import java.time.LocalDateTime;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.tvj.internaltool.entity.UserEntity;

public class UserEntityDataDummy {

    private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public UserEntity getAdminUser1() {
        UserSettingEntityDataDummy userSettingEntityDataDummy = new UserSettingEntityDataDummy();
        RoleEntityDataDummy roleEntityDataDummy = new RoleEntityDataDummy();
        TitleEntityDataDummy titleEntityDataDummy = new TitleEntityDataDummy();

        UserEntity admin = new UserEntity();
        admin.setUserId("1");
        admin.setUserSettingId("1");
        admin.setRoleId("1");
        admin.setUsername("admin1");
        admin.setPassword(passwordEncoder.encode("12345678"));
        admin.setFirstName("Ad1");
        admin.setLastName("Min1");
        admin.setTitleId("1");
        admin.setEmail("ngocdc@tinhvan.com");
        admin.setActivated(true);
        admin.setLoginFailCount(0);
        admin.setCreatedBy("Dex");
        admin.setCreatedDate(LocalDateTime.now());
        admin.setUpdatedBy("Dexx");
        admin.setUpdatedDate(LocalDateTime.now().plusHours(1));
        admin.setDeletedBy(null);
        admin.setDeletedDate(null);
        admin.setRole(roleEntityDataDummy.getAdminRole());
        admin.setUserSettingEntity(userSettingEntityDataDummy.getAdminUserSetting1());
        admin.setTitleEntity(titleEntityDataDummy.getTitle1());
        return admin;
    }

    public UserEntity getAdminUser2() {
        UserSettingEntityDataDummy userSettingEntityDataDummy = new UserSettingEntityDataDummy();
        RoleEntityDataDummy roleEntityDataDummy = new RoleEntityDataDummy();
        TitleEntityDataDummy titleEntityDataDummy = new TitleEntityDataDummy();

        UserEntity admin = new UserEntity();
        admin.setUserId("2");
        admin.setUserSettingId("2");
        admin.setRoleId("1");
        admin.setUsername("admin2");
        admin.setPassword(passwordEncoder.encode("12345678"));
        admin.setFirstName("Ad2");
        admin.setLastName("Min2");
        admin.setTitleId("2");
        admin.setEmail("ngocdc@tinhvan.com");
        admin.setActivated(true);
        admin.setLoginFailCount(0);
        admin.setCreatedBy("Dex");
        admin.setCreatedDate(LocalDateTime.now());
        admin.setUpdatedBy("Dexx");
        admin.setUpdatedDate(LocalDateTime.now().plusHours(1));
        admin.setDeletedBy(null);
        admin.setDeletedDate(null);
        admin.setRole(roleEntityDataDummy.getAdminRole());
        admin.setUserSettingEntity(userSettingEntityDataDummy.getAdminUserSetting2());
        admin.setTitleEntity(titleEntityDataDummy.getTitle2());
        return admin;
    }

    public UserEntity getNormalUser1() {
        UserSettingEntityDataDummy userSettingEntityDataDummy = new UserSettingEntityDataDummy();
        RoleEntityDataDummy roleEntityDataDummy = new RoleEntityDataDummy();
        TitleEntityDataDummy titleEntityDataDummy = new TitleEntityDataDummy();

        UserEntity user = new UserEntity();
        user.setUserId("3");
        user.setUserSettingId("3");
        user.setRoleId("2");
        user.setUsername("user1");
        user.setPassword(passwordEncoder.encode("12345678"));
        user.setFirstName("U1");
        user.setLastName("Ser1");
        user.setTitleId("3");
        user.setEmail("ngocdc@tinhvan.com");
        user.setActivated(true);
        user.setLoginFailCount(0);
        user.setCreatedBy("Dex");
        user.setCreatedDate(LocalDateTime.now());
        user.setUpdatedBy("Dexx");
        user.setUpdatedDate(LocalDateTime.now().plusHours(1));
        user.setDeletedBy(null);
        user.setDeletedDate(null);
        user.setRole(roleEntityDataDummy.getUserRole());
        user.setUserSettingEntity(userSettingEntityDataDummy.getUserUserSetting1());
        user.setTitleEntity(titleEntityDataDummy.getTitle3());
        return user;
    }

}
