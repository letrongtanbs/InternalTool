package com.tvj.internaltool.service.impl;

import com.tvj.internaltool.dummy.UserEntityDataDummy;
import com.tvj.internaltool.entity.UserEntity;
import org.junit.jupiter.api.Test;

class UserServiceImplTest {

    @Test
    void processLogin() {

        UserEntityDataDummy userEntityDataDummy = new UserEntityDataDummy();
        UserEntity userEntity = userEntityDataDummy.getAdminUser1();

    }
}