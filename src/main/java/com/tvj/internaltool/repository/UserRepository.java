package com.tvj.internaltool.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

import com.tvj.internaltool.entity.UserEntity;

public interface UserRepository extends CustomRepository<UserEntity, String>, CustomUserRepository {

    @Query("SELECT u FROM UserEntity u WHERE u.username = :username AND u.isActivated = true")
    UserEntity findActivatedUserByUsername(String username);
    
    @Query("SELECT u FROM UserEntity u WHERE "
            + " u.lastLogout IS NULL "
            + " AND u.lastLogin IS NOT NULL "
            + " AND TIMESTAMPDIFF(SECOND, u.lastLogin, NOW()) > :tokenValidityInSecond ")
    List<UserEntity> getNoneLogoutUserWithExpiredLoginToken(long tokenValidityInSecond);
    
    @Query("SELECT u FROM UserEntity u WHERE u.username = :username AND u.deletedDate IS NULL")
    UserEntity findNonDeletedUserByUsername(String username);

}
