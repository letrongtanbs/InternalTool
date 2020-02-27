package com.tvj.internaltool.dummy.dto.res;

import com.tvj.internaltool.dto.res.UserLoginResDto;

public class UserLoginResDtoDataDummy {

    public UserLoginResDto getAdminUserResDto1() {
        UserLoginResDto admin = new UserLoginResDto();
        admin.setToken("sampleToken");
        admin.setFirstName("Ad");
        admin.setLastName("Min1");
        admin.setRoleName("ADMIN");
        admin.setFirstTimeLogin(false);
        return admin;
    }

}
