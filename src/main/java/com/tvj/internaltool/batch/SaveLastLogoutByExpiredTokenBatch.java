package com.tvj.internaltool.batch;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.tvj.internaltool.entity.UserEntity;
import com.tvj.internaltool.repository.UserRepository;

@Component
public class SaveLastLogoutByExpiredTokenBatch {

    private final UserRepository userRepository;

    public SaveLastLogoutByExpiredTokenBatch(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    
    @Value("${jwt.token.validity-in-second}")
    private long JWT_TOKEN_VALIDITY_IN_SECOND;

    @Scheduled(cron = "${cron-expression.validate-last-logout-by-token}")
    public void cronJobSaveLastLogout() {
        LocalDateTime now = LocalDateTime.now();
        List<UserEntity> userEntityList = userRepository.getNoneLogoutUserWithExpiredLoginToken(JWT_TOKEN_VALIDITY_IN_SECOND);
        userEntityList.forEach(u -> u.setLastLogout(now));
        userRepository.saveAll(userEntityList);
        
        System.out.println("Save all last logout for non logout user at: " + now);
    }

}
