package com.javarush.stepanov.config;
import lombok.Data;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Set;

@Data
@Component
@ConfigurationProperties(prefix = "com.javarush")
@ToString
public class AppConfig {
    public String QUESTIONSPATH;
}
