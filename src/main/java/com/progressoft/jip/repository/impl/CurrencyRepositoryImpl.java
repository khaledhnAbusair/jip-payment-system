package com.progressoft.jip.repository.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.progressoft.jip.datastructures.CurrencyDataStructure;
import com.progressoft.jip.gateway.CurrencyGateway;
import com.progressoft.jip.model.Currency;
import com.progressoft.jip.repository.CurrencyRepository;

public class CurrencyRepositoryImpl implements CurrencyRepository {

	private CurrencyGateway currencyGateway;
	private List<Currency> currencies = new ArrayList<>();

	public CurrencyRepositoryImpl(CurrencyGateway currencyGateway) {
		this.currencyGateway = currencyGateway;
	}

	@Override
	public Collection<Currency> loadCurrencies() {
		Iterable<CurrencyDataStructure> currencies = currencyGateway.loadCurrencies();
		currencies.forEach(currency -> this.currencies.add(new Currency(currency)));
		return this.currencies;
	}

	@Override
	public Currency loadCurrencyByCode(String currencyCode) {
		CurrencyDataStructure currency = currencyGateway.loadCurrencyByCode(currencyCode);
		return new Currency(currency);
	}

	static class CurrencyNotUpdatedExeption extends RuntimeException {
		private static final long serialVersionUID = 1L;

	}

}
