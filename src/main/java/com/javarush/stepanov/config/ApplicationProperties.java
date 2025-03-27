package com.javarush.stepanov.config;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.FileReader;
import java.net.URI;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.Properties;


@Slf4j
@Component
public class ApplicationProperties extends Properties {

    public static final String HIBERNATE_CONNECTION_URL = "hibernate.connection.url";
    public static final String HIBERNATE_CONNECTION_USERNAME = "hibernate.connection.username";
    public static final String HIBERNATE_CONNECTION_PASSWORD = "hibernate.connection.password";
    public static final String HIBERNATE_CONNECTION_DRIVER_CLASS = "hibernate.connection.driver_class";

    @SneakyThrows
    public ApplicationProperties(
            @Value("/application.properties") String path,
            @Value("${hibernate.connection.driver_class}") String driver
    ) {
        this.load(new FileReader(CLASSES_ROOT + path));
        injectEnvironmentVariables();
        try {
            Class.forName(driver);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void injectEnvironmentVariables() {
        log.info("Environment size: {}", System.getenv().size());
        this.forEach((key, value) -> {
            String strKey = key.toString();
            String strValue = value.toString();
            if (strValue.startsWith("${") && strValue.endsWith("}")) {
                String[] valueParts = strValue
                        .replace("${", "")
                        .replace("}", "")
                        .split(":", 2);
                if (valueParts.length == 2) {
                    strValue = valueParts[1];
                }
                String keyEnvironmentVariable = valueParts[0];
                String valueEnvironmentVariable = System.getenv(keyEnvironmentVariable);
                if (keyEnvironmentVariable != null && valueEnvironmentVariable != null) {
                    strValue = valueEnvironmentVariable;
                }
                this.setProperty(strKey, strValue);
            }
        });
    }

    //any runtime
    public final static Path CLASSES_ROOT = Paths.get(URI.create(
            Objects.requireNonNull(
                    ApplicationProperties.class.getResource("/")
            ).toString()));

    //only in Tomcat (not use in tests)
    public final static Path WEB_INF = CLASSES_ROOT.getParent();
}
