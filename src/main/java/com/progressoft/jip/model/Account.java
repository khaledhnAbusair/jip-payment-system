package com.progressoft.jip.model;

import com.progressoft.jip.datastructures.AccountDatastructure;
import com.progressoft.jip.datastructures.builder.AccountViewBuilder;
import com.progressoft.jip.usecase.exception.AccountDoesNotHaveEnoughBalanceException;
import com.progressoft.jip.usecase.exception.InvalidAmountException;

public class Account {

	private AccountDatastructure accountDatastructure;

	public Account(AccountDatastructure accountDatastructure) {
		this.accountDatastructure = accountDatastructure;
	}

	public void buildAccountView(AccountViewBuilder accountViewBuilder) {
		accountViewBuilder.setIban(accountDatastructure.getIban());
		accountViewBuilder.setBalance(accountDatastructure.getBalance());
		accountViewBuilder.setCurrencyCode(accountDatastructure.getCurrencyCode());
		accountViewBuilder.setStatus(accountDatastructure.getStatus());
		accountViewBuilder.setType(accountDatastructure.getAccountType());
	}

	public void depit(double amount) {
		if (!isHaveEnoughBalance(amount))
			throw new AccountDoesNotHaveEnoughBalanceException();
		if (!isValidAmount(amount))
			throw new InvalidAmountException();
		accountDatastructure.setBalance(accountDatastructure.getBalance() - amount);
	}

	private boolean isHaveEnoughBalance(double amount) {
		return amount <= accountDatastructure.getBalance();
	}

	public void credit(double amount) {
		if (!isValidAmount(amount))
			throw new InvalidAmountException();
		accountDatastructure.setBalance(accountDatastructure.getBalance() + amount);
	}

	private boolean isValidAmount(double amount) {
		return amount >= 0;
	}

}
