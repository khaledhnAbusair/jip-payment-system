package com.progressoft.jip.repository.impl;

import java.util.ArrayList;
import java.util.List;

import com.progressoft.jip.datastructures.Currency;
import com.progressoft.jip.gateway.CurrencyGateway;
import com.progressoft.jip.model.CurrencyModel;

public class CurrencyRepositoryImpl {

	private CurrencyGateway currencyGateway;
	private List<CurrencyModel> currencies = new ArrayList<>();

	public CurrencyRepositoryImpl(CurrencyGateway currencyGateway) {
		this.currencyGateway = currencyGateway;
	}

	public Iterable<CurrencyModel> loadCurrencies() {
		Iterable<Currency> currencies = currencyGateway.loadCurrencies();
		currencies.forEach(currency -> this.currencies.add(new CurrencyModel(currency)));
		return this.currencies;
	}

	public CurrencyModel loadCurrencyByCode(String currencyCode) {
		Currency currency = currencyGateway.getCurrencyByCode(currencyCode);
		return new CurrencyModel(currency);
	}

	public void updatedCurrencyRateByCode(String currencyCode, double currencyRate) {
		currencyGateway.updateCurrencyRateByCode(currencyCode, currencyRate);

	}

}
