package com.galvanize.productmanagement.service;

import org.javamoney.moneta.Money;

import javax.money.CurrencyUnit;

public interface MoneyExchangeService {
    Money exchange(Money amount, CurrencyUnit toCurrency);
}
