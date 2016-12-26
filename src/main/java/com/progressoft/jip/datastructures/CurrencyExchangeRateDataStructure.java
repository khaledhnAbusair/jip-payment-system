package com.progressoft.jip.datastructures;

public class CurrencyExchangeRateDataStructure {

	private String codeFrom;
	private String codeTo;
	private double rate;

	public CurrencyExchangeRateDataStructure(String codeFrom, String codeTo, double rate) {
		this.codeFrom = codeFrom;
		this.codeTo = codeTo;
		this.rate = rate;
	}

	public String getCodeFrom() {
		return codeFrom;
	}

	public void setCodeFrom(String codeFrom) {
		this.codeFrom = codeFrom;
	}

	public String getCodeTo() {
		return codeTo;
	}

	public void setCodeTo(String codeTo) {
		this.codeTo = codeTo;
	}

	public double getRate() {
		return rate;
	}

	public void setRate(double rate) {
		this.rate = rate;
	}

}
