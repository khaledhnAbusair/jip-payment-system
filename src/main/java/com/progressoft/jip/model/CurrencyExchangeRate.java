package com.progressoft.jip.model;

import com.progressoft.jip.datastructures.CurrencyExchangeRateDataStructure;

public class CurrencyExchangeRate {

	private CurrencyExchangeRateDataStructure currencyExchangeRateDataStructure;

	public CurrencyExchangeRate(CurrencyExchangeRateDataStructure currencyExchangeRateDataStructure) {
		this.currencyExchangeRateDataStructure = currencyExchangeRateDataStructure;
	}
	
	
	public double convert(double amount){
		return amount * currencyExchangeRateDataStructure.getRate();
	}

	// convert method, takes amount and uses the rate we have, returns converted
	// amount

}
