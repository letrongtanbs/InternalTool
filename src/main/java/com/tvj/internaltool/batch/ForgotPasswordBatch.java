package com.tvj.internaltool.batch;

import java.time.LocalDateTime;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.tvj.internaltool.repository.ForgotPasswordTokenRepository;

@Component
public class ForgotPasswordBatch {

    private final ForgotPasswordTokenRepository forgotPasswordTokenRepository;

    public ForgotPasswordBatch(ForgotPasswordTokenRepository forgotPasswordTokenRepository) {
        this.forgotPasswordTokenRepository = forgotPasswordTokenRepository;
    }

    @Scheduled(cron = "${cron-expression.forgot-password}")
    public void cronJobDeleteExpiredForgotPasswordToken() {
        forgotPasswordTokenRepository.deleteExpiredForgotPasswordToken();
        System.out.println("Delete all expired forgot password tokens at: " + LocalDateTime.now().toString());
    }

}
