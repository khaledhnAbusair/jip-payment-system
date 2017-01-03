package com.progressoft.jip.datastructures.builder.impl;

import com.progressoft.jip.datastructures.AccountDatastructure;
import com.progressoft.jip.datastructures.builder.AccountDataStructureBuilder;

public class AccountDataStructureBuilderImpl implements AccountDataStructureBuilder {

	private String IBAN;
	private String type;
	private String status;
	private double balance;
	private String rule;
	private String currencyCode;

	@Override
	public AccountDataStructureBuilder setIBAN(String IBAN) {
		this.IBAN = IBAN;
		return this;
	}

	@Override
	public AccountDataStructureBuilder setType(String type) {
		this.type = type;
		return this;
	}

	@Override
	public AccountDataStructureBuilder setBalance(double balance) {
		this.balance = balance;
		return this;
	}

	@Override
	public AccountDataStructureBuilder setStatus(String status) {
		this.status = status;
		return this;
	}

	@Override
	public AccountDataStructureBuilder setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
		return this;
	}

	@Override
	public AccountDataStructureBuilder setRule(String rule) {
		this.rule = rule;
		return this;
	}

	@Override
	public AccountDatastructure build() {
		AccountDatastructure accountDataStructure = new AccountDatastructure();
		accountDataStructure.setIban(IBAN);
		accountDataStructure.setAccountType(type);
		accountDataStructure.setBalance(balance);
		accountDataStructure.setStatus(status);
		accountDataStructure.setRule(rule);
		accountDataStructure.setCurrencyCode(currencyCode);
		return accountDataStructure;
	}

}
