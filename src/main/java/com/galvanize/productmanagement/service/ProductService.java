package com.galvanize.productmanagement.service;

import com.galvanize.productmanagement.domain.Product;
import com.galvanize.productmanagement.dto.ProductResponse;

import javax.money.CurrencyUnit;
import java.util.List;

public interface ProductService {
    ProductResponse create(Product product);
    ProductResponse getToView(Long id, CurrencyUnit currency);
    void softDelete(Long id);
    List<ProductResponse> findMostVisitedProducts(Integer limit, CurrencyUnit currency);
}
