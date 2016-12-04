package com.progressoft.jip.gateway;

import com.progressoft.jip.datastructures.CurrencyDataStructure;

public interface CurrencyGateway {

	Iterable<CurrencyDataStructure> loadCurrencies();

	CurrencyDataStructure loadCurrencyByCode(String currencyCode);

}
