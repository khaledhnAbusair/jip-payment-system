package com.progressoft.jip.model;

import com.progressoft.jip.datastructures.AccountDatastructure;
import com.progressoft.jip.datastructures.builder.AccountInfoBuilder;

public class Account {

    private AccountDatastructure accountDatastructure;
    private Currency currency;

    public Account(AccountDatastructure accountDatastructure, Currency currency) {
	this.accountDatastructure = accountDatastructure;
	this.currency = currency;
    }

    public void buildAccountInfo(AccountInfoBuilder accountInfoBuilder) {
	accountInfoBuilder.setIban(accountDatastructure.getIban());
    }

}
