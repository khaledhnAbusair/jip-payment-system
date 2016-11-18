package com.progressoft.jip.datastructures;



import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("Currency")
public class Currency {

	private String currencyCode;
	private double currencyRate;
	private String currencyDescription;
	//private java.util.Currency currency;
	
	
	
	
	public String getCurrencyCode() {
		
		return currencyCode;
	}

	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	public double getCurrencyRate() {
		return currencyRate;
	}

	public void setCurrencyRate(double currencyRate) {
		this.currencyRate = currencyRate;
	}

	public String getCurrencyDescription() {
		return currencyDescription;
	}

	public void setCurrencyDescription(String currencyDescription) {
		this.currencyDescription = currencyDescription;
	}

}
