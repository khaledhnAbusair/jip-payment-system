package com.progressoft.jip.view;

import com.progressoft.jip.datastructures.builder.AccountViewBuilder;

public class AccountView implements AccountViewBuilder {

    private String IBAN;

    public String iban() {
	return IBAN;
    }

    @Override
    public AccountViewBuilder setIban(String IBAN) {
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
