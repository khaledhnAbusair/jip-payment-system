package com.progressoft.jip.gateway;

import com.progressoft.jip.datastructures.CurrencyDataStructure;

public interface CurrencyGateway {

	Iterable<CurrencyDataStructure> loadCurrencies();

	CurrencyDataStructure getCurrencyByCode(String currencyCode);

	int updateCurrencyRateByCode(String currencyCode ,double currencyRate);

	int createCurrency(CurrencyDataStructure currency);

	int deleteCurrency(CurrencyDataStructure currency);


}
