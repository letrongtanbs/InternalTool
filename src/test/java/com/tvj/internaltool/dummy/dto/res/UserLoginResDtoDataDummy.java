package com.tvj.internaltool.dummy.dto.res;

import com.tvj.internaltool.dto.res.UserLoginResDto;

public class UserLoginResDtoDataDummy {

    public UserLoginResDto getAdminUserResDto1() {
        UserLoginResDto admin1 = new UserLoginResDto();
        admin1.setToken("sampleToken");
        admin1.setFirstName("Ad");
        admin1.setLastName("Min1");
        admin1.setRoleName("ADMIN");
        admin1.setFirstTimeLogin(false);
        return admin1;
    }

}
