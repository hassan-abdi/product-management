package com.galvanize.productmanagement.config;

import com.galvanize.productmanagement.dto.SerializableMoney;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import javax.money.MonetaryAmount;
import java.util.Collections;

@Configuration
public class SpringFoxConfig {

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .directModelSubstitute(MonetaryAmount.class, SerializableMoney.class)
                .apiInfo(getApiInfo())
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo getApiInfo() {
        return new ApiInfo("Product Management - Galvanize", "Rest API", "1.0", "",
                new Contact("Hassan Abdi", "", "hassan.abdi@outlook.com"),
                "", "", Collections.emptyList()
        );
    }
}