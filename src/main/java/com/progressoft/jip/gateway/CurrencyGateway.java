package com.progressoft.jip.gateway;

import com.progressoft.jip.datastructures.Currency;

public interface CurrencyGateway {

	Iterable<Currency> loadCurrencies();

	Currency getCurrencyByCode(String currencyCode);

	void updateCurrencyRateByCode(String currencyCode ,double currencyRate);

	void createCurrency(Currency currency);

	void deleteCurrency(Currency currency);


}
