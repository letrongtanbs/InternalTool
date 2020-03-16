package com.tvj.internaltool.repository;

import com.tvj.internaltool.entity.UserEntity;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends CustomRepository<UserEntity, String>, CustomUserRepository {

    @Query("SELECT u FROM UserEntity u WHERE u.username = :username AND u.isActivated = true")
    UserEntity findActivatedUserByUsername(String username);

}
