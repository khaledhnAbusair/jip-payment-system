package com.progressoft.jip.datastructures;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("Balance")
public class Balance {

    private double amount;
    private CurrencyDataStructure currency;

    public Balance(double amount, CurrencyDataStructure currency) {
	this.amount = amount;
	this.currency = currency;
    }

    public double getAmount() {
	return amount;
    }

    public void setAmount(double amount) {
	this.amount = amount;
    }

    public CurrencyDataStructure getCurrency() {
	return currency;
    }

    public void setCurrency(CurrencyDataStructure currency) {
	this.currency = currency;
    }

}
