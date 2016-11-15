package com.progressoft.jip.datastructures;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("Balance")
public class Balance {

    private double amount;
    private Currency currency;

    public Balance(double amount, Currency currency) {
	this.amount = amount;
	this.currency = currency;
    }

    public double getAmount() {
	return amount;
    }

    public void setAmount(double amount) {
	this.amount = amount;
    }

    public Currency getCurrency() {
	return currency;
    }

    public void setCurrency(Currency currency) {
	this.currency = currency;
    }

}
