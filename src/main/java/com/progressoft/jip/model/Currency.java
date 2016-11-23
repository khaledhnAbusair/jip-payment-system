package com.progressoft.jip.model;

import com.progressoft.jip.datastructures.CurrencyDataStructure;

public class Currency {

	private CurrencyDataStructure currency;

	public Currency(CurrencyDataStructure currency) {
		this.currency = currency;
	}

	public double getCurrencyRate() {
		return currency.getCurrencyRate();
	}

	public String getCurrencyCode() {
		return currency.getCurrencyCode();
	}

}