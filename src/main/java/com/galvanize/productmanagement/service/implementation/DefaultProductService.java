package com.galvanize.productmanagement.service.implementation;

import com.galvanize.productmanagement.domain.Product;
import com.galvanize.productmanagement.dto.ProductResponse;
import com.galvanize.productmanagement.repository.ProductRepository;
import com.galvanize.productmanagement.service.MoneyExchangeService;
import com.galvanize.productmanagement.service.ProductService;
import com.galvanize.productmanagement.service.exception.ProductNotFoundException;
import org.javamoney.moneta.Money;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.money.CurrencyUnit;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DefaultProductService implements ProductService {
    private final ProductRepository repository;
    private final MoneyExchangeService exchangeService;
    private CurrencyUnit defaultCurrency;

    @Autowired
    public DefaultProductService(ProductRepository repository, MoneyExchangeService exchangeService) {
        this.repository = repository;
        this.exchangeService = exchangeService;
    }

    @Override
    public ProductResponse create(Product product) {
        Product persistedProduct = repository.save(product);
        return toProductResponse(persistedProduct, defaultCurrency);
    }

    @Override
    @Transactional
    public ProductResponse getToView(Long id, CurrencyUnit toCurrency) {
        Optional<Product> productOptional = repository.getByIdAndDeletedIsFalse(id);
        if (productOptional.isEmpty()){
            throw new ProductNotFoundException();
        }
        Product product = productOptional.get();
        product.setViewCount(product.getViewCount() + 1);
        repository.save(product);
        return toProductResponse(product, toCurrency);
    }

    @Override
    public void softDelete(Long id) {
        Optional<Product> productOptional = repository.findById(id);
        if (productOptional.isEmpty()){
            throw new ProductNotFoundException();
        }
        Product product = productOptional.get();
        product.setDeleted(true);
        repository.save(product);
    }

    @Override
    public List<ProductResponse> findMostVisitedProducts(Integer limit, CurrencyUnit toCurrency) {
        return repository.findMostVisitedProducts(PageRequest.of(0, limit, Sort.by(Sort.Order.by("viewCount"))))
                .stream().map(item -> toProductResponse(item, toCurrency))
                .collect(Collectors.toList());
    }

    private ProductResponse toProductResponse(Product product, CurrencyUnit toCurrency) {
        ProductResponse response = new ProductResponse();
        response.setId(product.getId());
        response.setName(product.getName());
        response.setDescription(product.getDescription());
        response.setViewCount(product.getViewCount());
        response.setPrice(exchange(product.getPrice(), toCurrency));
        return response;
    }

    private Money exchange(BigDecimal price, CurrencyUnit toCurrency) {
        return exchangeService.exchange(Money.of(price, defaultCurrency), toCurrency);
    }

    @Value("${product.currency.default}")
    public void setDefaultCurrency(CurrencyUnit defaultCurrency) {
        this.defaultCurrency = defaultCurrency;
    }
}