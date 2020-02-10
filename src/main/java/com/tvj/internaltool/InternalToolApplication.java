package com.tvj.internaltool;

import com.tvj.internaltool.repository.impl.CustomRepositoryImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@EnableJpaRepositories(repositoryBaseClass = CustomRepositoryImpl.class)
public class InternalToolApplication {

    public static void main(String[] args) {
        SpringApplication.run(InternalToolApplication.class, args);
    }

}
