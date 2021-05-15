package com.galvanize.productmanagement.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import javax.money.CurrencyUnit;
import java.util.List;

@ConfigurationProperties(prefix="product.exchange")
public class ExchangeServiceConfig {
    private String serviceUrl;
    private String accessKey;
    private List<CurrencyUnit> supportedCurrencies;

    public String getServiceUrl() {
        return serviceUrl;
    }

    public void setServiceUrl(String serviceUrl) {
        this.serviceUrl = serviceUrl;
    }

    public String getAccessKey() {
        return accessKey;
    }

    public void setAccessKey(String accessKey) {
        this.accessKey = accessKey;
    }

    public List<CurrencyUnit> getSupportedCurrencies() {
        return supportedCurrencies;
    }

    public void setSupportedCurrencies(List<CurrencyUnit> supportedCurrencies) {
        this.supportedCurrencies = supportedCurrencies;
    }
}
