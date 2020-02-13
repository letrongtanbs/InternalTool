package com.tvj.internaltool.dummy;

import com.tvj.internaltool.entity.UserEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;

public class UserEntityDataDummy {

    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public UserEntity getRootUser() {
        UserEntity root = new UserEntity();
        root.setUserId("1");
        root.setUserSettingId("1");
        root.setRoleId("1");
        root.setUsername("root");
        root.setPassword(passwordEncoder.encode("12345678"));
        root.setFirstName("FF");
        root.setLastName("LL");
        root.setEmail("ngocdc@tinhvan.com");
        root.setActive(true);
        root.setLoginFailCount(0);
        root.setCreatedBy("Dex");
        root.setCreatedDate(LocalDateTime.now());
        root.setUpdatedBy("Dexx");
        root.setUpdatedDate(LocalDateTime.now().plusHours(1));
        root.setDeletedBy(null);
        root.setDeletedDate(null);

        root.setRole(null);
        root.setUserSettingEntity(null);
        return root;
    }

}
