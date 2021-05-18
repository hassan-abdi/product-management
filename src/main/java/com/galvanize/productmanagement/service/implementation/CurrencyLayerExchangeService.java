package com.galvanize.productmanagement.service.implementation;

import com.galvanize.productmanagement.config.ExchangeServiceConfig;
import com.galvanize.productmanagement.service.MoneyExchangeService;
import org.javamoney.moneta.Money;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.money.CurrencyUnit;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Component
@Profile("production")
public class CurrencyLayerExchangeService implements MoneyExchangeService {

    private final ExchangeServiceConfig config;
    private final Map<String, Float> rates = new ConcurrentHashMap<>();
    private final RestTemplate service = new RestTemplate();
    private CurrencyUnit defaultCurrency;

    @Autowired
    public CurrencyLayerExchangeService(ExchangeServiceConfig config) {
        this.config = config;
    }

    @Override
    public Money exchange(Money amount, CurrencyUnit toCurrency) {
        //todo: Integrate this code with Java Money JSR
        Float rate = rates.get(amount.getCurrency().getCurrencyCode().concat(toCurrency.getCurrencyCode()));
        float v = amount.getNumber().floatValue() * rate;
        return Money.of(v, toCurrency);
    }

    @Scheduled(initialDelay = 1L, fixedDelayString = "${product.exchange.interval}")
    public void reloadRates(){
        Objects.requireNonNull(service.getForObject(buildUrl(), CurrencyLayerResponse.class))
                .getQuotes()
                .forEach(rates::put);
    }

    private String buildUrl() {
        String supportedCurrenciesStr = config.getSupportedCurrencies().stream().map(CurrencyUnit::getCurrencyCode)
                .collect(Collectors.joining(","));
        return config.getServiceUrl().concat("?access_key=").concat(config.getAccessKey())
                .concat("&source=").concat(defaultCurrency.getCurrencyCode())
                .concat("&currencies=").concat(supportedCurrenciesStr);
    }

    @Value("${product.currency.default}")
    public void setDefaultCurrency(CurrencyUnit defaultCurrency) {
        this.defaultCurrency = defaultCurrency;
    }

    static class CurrencyLayerResponse {
        private Map<String, Float> quotes;


        public Map<String, Float> getQuotes() {
            return quotes;
        }

        public void setQuotes(Map<String, Float> quotes) {
            this.quotes = quotes;
        }
    }
}
