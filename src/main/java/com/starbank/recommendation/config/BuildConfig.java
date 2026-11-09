package com.starbank.recommendation.config;

import org.springframework.boot.info.BuildProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

@Configuration
public class BuildConfig {

    @Bean
    public BuildProperties buildProperties() {
        Properties props = new Properties();
        props.setProperty("artifact", "recommendation");
        props.setProperty("version", "0.0.1-SNAPSHOT");
        return new BuildProperties(props);
    }
}