package com.tvj.internaltool.repository;

import com.tvj.internaltool.entity.ForgotPasswordTokenEntity;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface ForgotPasswordTokenRepository extends CustomRepository<ForgotPasswordTokenEntity, String> {

    @Transactional
    @Modifying
    @Query("DELETE FROM ForgotPasswordTokenEntity fpte WHERE fpte.userId = :userId")
    void deleteTokenByUserId(String userId);

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM ForgotPasswordTokenEntity fpte WHERE fpte.tokenExpiredDate < CURRENT_TIMESTAMP")
    void deleteOutOfDateToken();

    ForgotPasswordTokenEntity findByTokenString(String token);

}
