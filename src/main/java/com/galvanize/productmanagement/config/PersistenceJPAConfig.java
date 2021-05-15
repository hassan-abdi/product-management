package com.galvanize.productmanagement.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackages = "com.galvanize.productmanagement.repository")
public class PersistenceJPAConfig {
}
