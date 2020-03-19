package com.tvj.internaltool;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.tvj.internaltool.properties.FileStorageProperties;
import com.tvj.internaltool.repository.impl.CustomRepositoryImpl;

@SpringBootApplication
@EnableScheduling
@EnableJpaRepositories(repositoryBaseClass = CustomRepositoryImpl.class)
@EnableConfigurationProperties({FileStorageProperties.class})
public class InternalToolApplication {

    public static void main(String[] args) {
        SpringApplication.run(InternalToolApplication.class, args);
    }

}
