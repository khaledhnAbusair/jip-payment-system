package com.progressoft.jip.repository.impl;

import java.util.ArrayList;
import java.util.List;

import com.progressoft.jip.datastructures.CurrencyDataStructure;
import com.progressoft.jip.gateway.CurrencyGateway;
import com.progressoft.jip.model.Currency;

public class CurrencyRepositoryImpl {

	private CurrencyGateway currencyGateway;
	private List<Currency> currencies = new ArrayList<>();

	public CurrencyRepositoryImpl(CurrencyGateway currencyGateway) {
		this.currencyGateway = currencyGateway;
	}

	public Iterable<Currency> loadCurrencies() {
		Iterable<CurrencyDataStructure> currencies = currencyGateway.loadCurrencies();
		currencies.forEach(currency -> this.currencies.add(new Currency(currency)));
		return this.currencies;
	}

	public Currency loadCurrencyByCode(String currencyCode) {
		CurrencyDataStructure currency = currencyGateway.getCurrencyByCode(currencyCode);
		return new Currency(currency);
	}

	public void updatedCurrencyRateByCode(String currencyCode, double currencyRate) {
		currencyGateway.updateCurrencyRateByCode(currencyCode, currencyRate);

	}

}
