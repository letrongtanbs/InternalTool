package com.tvj.internaltool.repository;

import com.tvj.internaltool.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, String> {

    UserEntity findByUsername(String username);

}
