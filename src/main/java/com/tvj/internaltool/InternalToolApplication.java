package com.tvj.internaltool;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication
public class InternalToolApplication {

    public static void main(String[] args) {
        SpringApplication.run(InternalToolApplication.class, args);
    }

}
