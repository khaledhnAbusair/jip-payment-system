package com.progressoft.jip.view;

import com.progressoft.jip.datastructures.builder.AccountViewBuilder;

public class AccountView implements AccountViewBuilder {

	private String IBAN;
	private String type;
	private double balance;
	private String status;
	private String currencyCode;

	public String getIBAN() {
		return IBAN;
	}

	public AccountViewBuilder setIBAN(String iBAN) {
		IBAN = iBAN;
		return this;
	}

	public String getType() {
		return type;
	}

	public AccountViewBuilder setType(String type) {
		this.type = type;
		return this;
	}

	public double getBalance() {
		return balance;
	}

	public AccountViewBuilder setBalance(double balance) {
		this.balance = balance;
		return this;
	}

	public String getStatus() {
		return status;
	}

	public AccountViewBuilder setStatus(String status) {
		this.status = status;
		return this;
	}

	public String getCurrencyCode() {
		return currencyCode;
	}

	public AccountViewBuilder setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
		return this;
	}

	public String getIban() {
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
		accountInfo.setBalance(balance);
		accountInfo.setCurrencyCode(currencyCode);
		accountInfo.setType(type);
		accountInfo.setStatus(status);
		return accountInfo;
	}

}
