package com.progressoft.jip.datastructures;
//dto
public class AccountDatastructure {

    private String iban;
    private String accountType;
    private double balance;
    private String status;
    private String currencyCode;

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

    @Override
    public String toString() {
	return "AccountDatastructure [iban=" + iban + ", accountType=" + accountType + ", balance=" + balance
		+ ", status=" + status + ", currencyCode=" + currencyCode + "]";
    }

}
