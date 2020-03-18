package com.tvj.internaltool.dummy.dto.res;

import com.tvj.internaltool.dto.res.UserStatusResDto;
import com.tvj.internaltool.enums.UserStatus;

public class UserStatusResDtoDataDummy {

    public UserStatusResDto getAvailable() {
        UserStatusResDto userStatusResDto = new UserStatusResDto();
        userStatusResDto.setStatusId(UserStatus.AVAILABLE.getStatus());
        userStatusResDto.setStatusName("Available");
        return userStatusResDto;
    }

    public UserStatusResDto getAway() {
        UserStatusResDto userStatusResDto = new UserStatusResDto();
        userStatusResDto.setStatusId(UserStatus.AWAY.getStatus());
        userStatusResDto.setStatusName("Away");
        return userStatusResDto;
    }

}
