package com.javarush.stepanov;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class JavaEducationApplication {
    public static void main(String[] args) {
        SpringApplication.run(JavaEducationApplication.class, args);
    }
}
