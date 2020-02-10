package com.tvj.internaltool.repository;

import com.tvj.internaltool.entity.UserEntity;

public interface UserRepository extends CustomRepository<UserEntity, String> {

    UserEntity findByUsername(String username);

}
