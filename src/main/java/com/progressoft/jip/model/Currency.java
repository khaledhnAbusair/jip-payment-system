package com.progressoft.jip.model;

import com.progressoft.jip.datastructures.CurrencyDataStructure;
import com.progressoft.jip.datastructures.builder.CurrencyViewBuilder;

public class Currency {

	private CurrencyDataStructure currency;

	public Currency(CurrencyDataStructure currency) {
		this.currency = currency;
	}

	public CurrencyViewBuilder buildCurrencyView(CurrencyViewBuilder builder) {
		return builder.setCode(currency.getCurrencyCode());
	}
}
