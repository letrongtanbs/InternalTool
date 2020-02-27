package com.tvj.internaltool.dummy.dto.res;

import com.tvj.internaltool.dto.res.UserSettingResDto;
import com.tvj.internaltool.enums.UserStatus;

import java.time.LocalDateTime;

public class UserSettingResDtoDataDummy {

    public UserSettingResDto getAdminUserSettingResDto1() {
        UserSettingResDto admin = new UserSettingResDto();
        admin.setUsername("admin1");
        admin.setFirstName("Ad");
        admin.setLastName("Min1");
        admin.setEmail("ngocdc@tinhvan.com");
        admin.setDepartmentId("1");
        admin.setDepartmentId("DEP001");
        admin.setTeamId("1");
        admin.setTeamName("TEAM001");
        admin.setTitle("Moderator");
        admin.setAddress("Ha Noi");
        admin.setPhone("0987654321");
        admin.setCountryId("1");
        admin.setCountryName("VietNam");
        admin.setLanguageId("1");
        admin.setLanguageName("English");
        admin.setStatus(UserStatus.AVAILABLE.getStatus());
        admin.setAvatar("F:\\TVJ\\file_upload\\avatar\\cv.jpg");
        admin.setUpdatedDate(LocalDateTime.now());
        return admin;
    }

}
