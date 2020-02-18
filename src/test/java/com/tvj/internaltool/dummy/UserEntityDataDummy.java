package com.tvj.internaltool.dummy;

import com.tvj.internaltool.entity.UserEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;

public class UserEntityDataDummy {

    private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public UserEntity getAdminUser1() {
        UserSettingEntityDataDummy userSettingEntityDataDummy = new UserSettingEntityDataDummy();
        RoleEntityDataDummy roleEntityDataDummy = new RoleEntityDataDummy();

        UserEntity admin = new UserEntity();
        admin.setUserId("1");
        admin.setUserSettingId("1");
        admin.setRoleId("1");
        admin.setUsername("admin1");
        admin.setPassword(passwordEncoder.encode("12345678"));
        admin.setFirstName("Ad1");
        admin.setLastName("Min1");
        admin.setEmail("ngocdc@tinhvan.com");
        admin.setActive(true);
        admin.setLoginFailCount(0);
        admin.setCreatedBy("Dex");
        admin.setCreatedDate(LocalDateTime.now());
        admin.setUpdatedBy("Dexx");
        admin.setUpdatedDate(LocalDateTime.now().plusHours(1));
        admin.setDeletedBy(null);
        admin.setDeletedDate(null);
        admin.setRole(roleEntityDataDummy.getAdminRole());
        admin.setUserSettingEntity(userSettingEntityDataDummy.getAdminUserSetting1());
        return admin;
    }

    public UserEntity getAdminUser2() {
        UserSettingEntityDataDummy userSettingEntityDataDummy = new UserSettingEntityDataDummy();
        RoleEntityDataDummy roleEntityDataDummy = new RoleEntityDataDummy();

        UserEntity admin = new UserEntity();
        admin.setUserId("2");
        admin.setUserSettingId("2");
        admin.setRoleId("1");
        admin.setUsername("admin2");
        admin.setPassword(passwordEncoder.encode("12345678"));
        admin.setFirstName("Ad2");
        admin.setLastName("Min2");
        admin.setEmail("ngocdc@tinhvan.com");
        admin.setActive(true);
        admin.setLoginFailCount(0);
        admin.setCreatedBy("Dex");
        admin.setCreatedDate(LocalDateTime.now());
        admin.setUpdatedBy("Dexx");
        admin.setUpdatedDate(LocalDateTime.now().plusHours(1));
        admin.setDeletedBy(null);
        admin.setDeletedDate(null);
        admin.setRole(roleEntityDataDummy.getAdminRole());
        admin.setUserSettingEntity(userSettingEntityDataDummy.getAdminUserSetting2());
        return admin;
    }

    public UserEntity getNormalUser1() {
        UserSettingEntityDataDummy userSettingEntityDataDummy = new UserSettingEntityDataDummy();
        RoleEntityDataDummy roleEntityDataDummy = new RoleEntityDataDummy();

        UserEntity user = new UserEntity();
        user.setUserId("3");
        user.setUserSettingId("3");
        user.setRoleId("2");
        user.setUsername("user1");
        user.setPassword(passwordEncoder.encode("12345678"));
        user.setFirstName("U1");
        user.setLastName("Ser1");
        user.setEmail("ngocdc@tinhvan.com");
        user.setActive(true);
        user.setLoginFailCount(0);
        user.setCreatedBy("Dex");
        user.setCreatedDate(LocalDateTime.now());
        user.setUpdatedBy("Dexx");
        user.setUpdatedDate(LocalDateTime.now().plusHours(1));
        user.setDeletedBy(null);
        user.setDeletedDate(null);
        user.setRole(roleEntityDataDummy.getUserRole());
        user.setUserSettingEntity(userSettingEntityDataDummy.getUserUserSetting1());
        return user;
    }

}
