package com.progressoft.jip.datastructures.builder.impl;

import com.progressoft.jip.datastructures.builder.AccountInfoBuilder;

public class AccountView implements AccountInfoBuilder {

    private String IBAN;

    public String iban() {
	return IBAN;
    }

    @Override
    public AccountInfoBuilder setIban(String IBAN) {
	this.IBAN = IBAN;
	return this;
    }

    @Override
    public AccountView build() {
	AccountView accountInfo = new AccountView();
	accountInfo.setIban(IBAN);
	return accountInfo;
    }

}
