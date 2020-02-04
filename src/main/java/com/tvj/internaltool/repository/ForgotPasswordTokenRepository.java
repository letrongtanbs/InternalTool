package com.tvj.internaltool.repository;

import com.tvj.internaltool.entity.ForgotPasswordTokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface ForgotPasswordTokenRepository extends JpaRepository<ForgotPasswordTokenEntity, String> {

    @Transactional
    @Modifying
    @Query("DELETE FROM ForgotPasswordTokenEntity fpte WHERE fpte.userId = :userId")
    void deleteTokenByUserId(String userId);

    ForgotPasswordTokenEntity findByTokenString(String token);

}
