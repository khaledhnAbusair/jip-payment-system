package com.progressoft.jip.datastructures;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("Account")
public class AccountDatastructure {

    private long id;
    private String type;
    private String iban;
    private Balance balance;
    private Currency currency;

    public AccountDatastructure(long id, String type, String iban, Balance balance, Currency currency) {
	this.id = id;
	this.type = type;
	this.iban = iban;
	this.balance = balance;
	this.currency = currency;
    }

    public long getId() {
	return id;
    }

    public void setId(long id) {
	this.id = id;
    }

    public String getType() {
	return type;
    }

    public void setType(String type) {
	this.type = type;
    }

    public String getIban() {
	return iban;
    }

    public void setIban(String iban) {
	this.iban = iban;
    }

    public Balance getBalance() {
	return balance;
    }

    public void setBalance(Balance balance) {
	this.balance = balance;
    }

    public Currency getCurrency() {
	return currency;
    }

    public void setCurrency(Currency currency) {
	this.currency = currency;
    }
    
    public static AccountDatastructure copyOf(AccountDatastructure account) {
	return new AccountDatastructure(account.getId(),account.getType(),account.getIban(),account.getBalance(),account.getCurrency());
    }
    
}
