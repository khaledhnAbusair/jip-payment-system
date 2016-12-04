package com.progressoft.jip.repository;

import java.util.Collection;

import com.progressoft.jip.model.Currency;

public interface CurrencyRepository {

	Collection<Currency> loadCurrencies();

	Currency loadCurrencyByCode(String currencyCode);

}