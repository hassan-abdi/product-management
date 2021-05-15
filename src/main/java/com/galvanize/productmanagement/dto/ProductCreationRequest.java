package com.galvanize.productmanagement.dto;

import javax.validation.constraints.*;
import java.math.BigDecimal;

public class ProductCreationRequest {
    @NotBlank
    @Size(min = 1, max = 100)
    private String name;

    @NotNull
    @Min(1)
    @Max(9999999)
    private BigDecimal price;

    @Size(max = 1000)
    private String description;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
