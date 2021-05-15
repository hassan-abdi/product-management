package com.galvanize.productmanagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
@ConfigurationPropertiesScan("com.galvanize.productmanagement")
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
