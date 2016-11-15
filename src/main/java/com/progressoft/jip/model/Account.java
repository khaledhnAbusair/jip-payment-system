package com.progressoft.jip.model;

import com.progressoft.jip.datastructures.AccountDatastructure;

public class Account {

    private AccountDatastructure accountDatastructure;

    private Account(AccountDatastructure accountDatastructure) {
	this.accountDatastructure = AccountDatastructure.copyOf(accountDatastructure);
    }

    public AccountDatastructure asDataStructure() {
	return accountDatastructure;
    }

    public static Account newAccountModel(AccountDatastructure accountDatastructure) {
	return new Account(accountDatastructure);
    }

    public static Account copyOf(Account account) {
	return Account.newAccountModel(account.asDataStructure());
    }

}
