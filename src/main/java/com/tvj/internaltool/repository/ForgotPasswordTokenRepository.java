package com.tvj.internaltool.repository;

import com.tvj.internaltool.entity.ForgotPasswordTokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ForgotPasswordTokenRepository extends JpaRepository<ForgotPasswordTokenEntity, String> {


}
