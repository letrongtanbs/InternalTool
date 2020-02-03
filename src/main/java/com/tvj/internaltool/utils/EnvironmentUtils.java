package com.tvj.internaltool.utils;

import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

@Service
public class EnvironmentUtils {

    private final Environment environment;

    public EnvironmentUtils(Environment environment) {
        this.environment = environment;
    }

    public String getPort() {
        return environment.getProperty("server.port");
    }

}
