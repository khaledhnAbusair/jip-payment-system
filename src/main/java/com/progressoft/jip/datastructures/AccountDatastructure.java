package com.progressoft.jip.datastructures;

public class AccountDatastructure {

	private String iban;
	private String accountType;
	private double balance;
	private String status;
	private String currencyCode;
	private String rule;

	public String getAccountType() {
		return accountType;
	}

	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}

	public String getIban() {
		return iban;
	}

	public void setIban(String iban) {
		this.iban = iban;
	}

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}

	public String getCurrencyCode() {
		return currencyCode;
	}

	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getRule() {
		return rule;
	}

	public void setRule(String rule) {
		this.rule = rule;
	}

	@Override
	public String toString() {
		return "AccountDatastructure [iban=" + iban + ", accountType=" + accountType + ", balance=" + balance
				+ ", status=" + status + ", currencyCode=" + currencyCode + ", rule=" + rule + "]";
	}
	
	

}
