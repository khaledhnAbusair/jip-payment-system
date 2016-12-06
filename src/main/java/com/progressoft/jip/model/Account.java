package com.progressoft.jip.model;

import com.progressoft.jip.datastructures.AccountDatastructure;

public class Account {
    
    private AccountDatastructure accountDatastructure;
    private Currency currency;

    public Account(AccountDatastructure accountDatastructure, Currency currency) {
	this.accountDatastructure = accountDatastructure;
	this.currency = currency;
    }

    public String getIban() {
	return accountDatastructure.getIban();
    }

    public Currency getCurrency() {
	return currency;
    }

}
