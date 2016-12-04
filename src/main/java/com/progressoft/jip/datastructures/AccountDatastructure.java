package com.progressoft.jip.datastructures;

public class AccountDatastructure {

    private String iban;
    private String accountType;
    private double balance;
    private Status status;
    private String currencyCode;

    public enum Status {
	ACTIVE, INACTIVE;
	public static Status fromStatus(String status) {
	    return status.equalsIgnoreCase("active") ? Status.ACTIVE : INACTIVE;
	}
    }

    public String getType() {
	return accountType;
    }

    public AccountDatastructure setType(String type) {
	this.accountType = type;
	return this;
    }

    public String getIban() {
	return iban;
    }

    public AccountDatastructure setIban(String iban) {
	this.iban = iban;
	return this;
    }

    public double getBalance() {
	return balance;
    }

    public AccountDatastructure setBalance(double balance) {
	this.balance = balance;
	return this;
    }

    public String getCurrencyCode() {
	return currencyCode;
    }

    public AccountDatastructure setCurrencyCode(String currencyCode) {
	this.currencyCode = currencyCode;
	return this;
    }

    public Status getStatus() {
	return status;
    }

    public AccountDatastructure setStatus(Status status) {
	this.status = status;
	return this;
    }

}
