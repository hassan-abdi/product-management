package com.galvanize.productmanagement.controller;

import com.galvanize.productmanagement.dto.ProductCreationRequest;
import com.galvanize.productmanagement.dto.ProductResponse;
import com.galvanize.productmanagement.dto.RestError;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ProductControllerIT {
    @LocalServerPort
    private int port;

    private String base;

    @Autowired
    private TestRestTemplate template;

    @BeforeEach
    public void setUp() {
        this.base = "http://localhost:" + port + "/api/product";
    }

    @Test
    public void testCreateProduct() {
        ProductCreationRequest request = new ProductCreationRequest();
        request.setName("TV");
        request.setDescription("TV - Apple");
        request.setPrice(BigDecimal.valueOf(1200));
        ProductResponse response = template.postForObject(base, request, ProductResponse.class);
        assertThat(response.getId()).isNotNull();
        assertThat(response.getViewCount()).isEqualTo(0L);
        assertThat(response.getPrice().getCurrency().getCurrencyCode()).isEqualTo("USD");
    }

    @Test
    public void testRetrieveProduct() {
        ProductCreationRequest request = new ProductCreationRequest();
        request.setName("iPhone 13 plus");
        request.setPrice(BigDecimal.valueOf(900));
        ProductResponse response = template.postForObject(base, request, ProductResponse.class);
        assertThat(response.getId()).isNotNull();
        assertThat(response.getViewCount()).isEqualTo(0L);
        ProductResponse visitedProduct = template.getForObject(base.concat("/").concat(response.getId().toString()), ProductResponse.class);
        assertThat(visitedProduct.getViewCount()).isEqualTo(1L);
    }

    @Test

    public void testRetrieveNotFoundProduct() {
        ResponseEntity<RestError> entity = template.getForEntity(base.concat("/544568"), RestError.class);
        assertThat(entity.getStatusCode().isError()).isTrue();
        assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    void testDeleteProduct() {
        ProductCreationRequest request = new ProductCreationRequest();
        request.setName("Galaxy Note i9");
        request.setPrice(BigDecimal.valueOf(700));
        ProductResponse response = template.postForObject(base, request, ProductResponse.class);
        assertThat(response.getId()).isNotNull();
        template.delete(base.concat("/").concat(response.getId().toString()));
    }

    @Test
    void testFindMostVisitedProducts() {
        ProductCreationRequest request = new ProductCreationRequest();
        request.setName("Mouse");
        request.setPrice(BigDecimal.valueOf(10));
        ProductResponse response = template.postForObject(base, request, ProductResponse.class);
        assertThat(response.getId()).isNotNull();
        assertThat(response.getViewCount()).isEqualTo(0L);
        ProductResponse visitedProduct = template.getForObject(base.concat("/").concat(response.getId().toString()), ProductResponse.class);
        assertThat(visitedProduct.getViewCount()).isEqualTo(1L);
        ProductResponse[] mostVisitedProducts = template.getForObject(base.concat("/top?limit=5&currency=EUR"), ProductResponse[].class);
        assertThat(mostVisitedProducts).isNotEmpty();
    }

}