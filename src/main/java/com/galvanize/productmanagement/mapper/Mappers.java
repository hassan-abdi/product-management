package com.galvanize.productmanagement.mapper;

import com.galvanize.productmanagement.domain.Product;
import com.galvanize.productmanagement.dto.ProductCreationRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Mappers {
    @Bean
    public Mapper<ProductCreationRequest, Product> productCreationRequestMapper() {
        return target -> {
            Product result = new Product();
            result.setName(target.getName());
            result.setPrice(target.getPrice());
            result.setDescription(target.getDescription());
            return result;
        };
    }
}
