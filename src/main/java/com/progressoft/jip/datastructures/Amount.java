package com.progressoft.jip.datastructures;

import com.progressoft.jip.model.CurrencyExchangeRate;
import com.progressoft.jip.repository.CurrencyExchangeRateRepository;

public class Amount {
	private double amountValue;
	private String currencyCode;
	private CurrencyExchangeRateRepository currencyExchangeRateRepository;

	public Amount(CurrencyExchangeRateRepository currenctExchangeRateRepository, double amount, String currencyCode) {
		this.currencyExchangeRateRepository = currenctExchangeRateRepository;
		this.amountValue = amount;
		this.currencyCode = currencyCode;
	}

	public double getAmount() {
		return amountValue;
	}

	public String getCurrencyCode() {
		return currencyCode;
	}

	public double valueIn(String targetCurrencyCode) {
		CurrencyExchangeRate loadCurrencyExchangeRate = currencyExchangeRateRepository
				.loadCurrencyExchangeRate(currencyCode, targetCurrencyCode);
		return loadCurrencyExchangeRate.convert(amountValue);
	}

}
