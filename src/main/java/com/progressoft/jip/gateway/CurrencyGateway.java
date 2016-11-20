package com.progressoft.jip.gateway;

import com.progressoft.jip.datastructures.Currency;

public interface CurrencyGateway {

	Iterable<Currency> loadCurrencies();

	Currency getCurrencyByCode(String currencyCode);

	int updateCurrencyRateByCode(String currencyCode ,double currencyRate);

	int createCurrency(Currency currency);

	int deleteCurrency(Currency currency);


}
