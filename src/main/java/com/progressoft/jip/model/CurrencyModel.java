package com.progressoft.jip.model;

import com.progressoft.jip.datastructures.Currency;

public class CurrencyModel {

	private Currency currency;

	public CurrencyModel(Currency currency) {
		this.currency = currency;
	}

	public double getCurrencyRate() {
		return currency.getCurrencyRate();
	}

	public String getCurrencyCode() {
		return currency.getCurrencyCode();
	}

}
