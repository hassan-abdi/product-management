package com.galvanize.productmanagement.service;

import org.javamoney.moneta.Money;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import javax.money.CurrencyUnit;

@Profile("test")
@Component
class FakeMoneyExchangeService implements MoneyExchangeService {

    @Override
    public Money exchange(Money amount, CurrencyUnit toCurrency) {
        return Money.of(amount.getNumber(), toCurrency);
    }
}