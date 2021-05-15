package com.galvanize.productmanagement.controller;

import com.galvanize.productmanagement.domain.Product;
import com.galvanize.productmanagement.dto.ProductCreationRequest;
import com.galvanize.productmanagement.dto.ProductResponse;
import com.galvanize.productmanagement.mapper.Mapper;
import com.galvanize.productmanagement.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.money.CurrencyUnit;
import javax.validation.Valid;
import java.util.List;

@RestController
public class ProductController {

    private final ProductService service;
    private final Mapper<ProductCreationRequest, Product> productCreationRequestMapper;

    @Autowired
    public ProductController(ProductService service, Mapper<ProductCreationRequest, Product> productCreationRequestMapper) {
        this.service = service;
        this.productCreationRequestMapper = productCreationRequestMapper;
    }

    @ResponseBody
    @PostMapping(value = "/api/product")
    public ProductResponse create(@RequestBody @Valid ProductCreationRequest request){
        return service.create(productCreationRequestMapper.apply(request));
    }

    @ResponseBody
    @GetMapping(value = "/api/product/{id}")
    public ProductResponse get(@PathVariable("id") Long id, @RequestParam(value = "currency",
            defaultValue = "${product.currency.default}") CurrencyUnit currency){
        return service.getToView(id, currency);
    }

    @DeleteMapping(value = "/api/product/{id}")
    public void delete(@PathVariable("id") Long id){
        service.softDelete(id);
    }

    @ResponseBody
    @GetMapping(value = "/api/product/top")
    public List<ProductResponse> findMostVisitedProducts(
            @RequestParam(value = "limit", defaultValue = "${product.top.limit}") Integer limit,
            @RequestParam(value = "currency", defaultValue = "${product.currency.default}") CurrencyUnit currency){
        return service.findMostVisitedProducts(limit, currency);
    }
}
