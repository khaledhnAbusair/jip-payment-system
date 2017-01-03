package com.progressoft.jip.model;

import java.time.LocalDate;

import com.progressoft.jip.datastructures.AccountDatastructure;
import com.progressoft.jip.datastructures.Amount;
import com.progressoft.jip.datastructures.builder.AccountViewBuilder;
import com.progressoft.jip.datastructures.builder.impl.AccountDataStructureBuilderImpl;
import com.progressoft.jip.model.actions.Rule;
import com.progressoft.jip.model.actions.impl.Rules;
import com.progressoft.jip.model.exception.AccountRuleViolationException;
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

	public void debit(Amount amount) {
		String currencyCode = accountDatastructure.getCurrencyCode();
		double debitAmount = amount.valueIn(currencyCode);
		if (!isHaveEnoughBalance(debitAmount))
			throw new AccountDoesNotHaveEnoughBalanceException();
		if (!isValidAmount(debitAmount))
			throw new InvalidAmountException();
		accountDatastructure.setBalance(accountDatastructure.getBalance() - debitAmount);
	}

	private boolean isHaveEnoughBalance(double amount) {
		return amount <= accountDatastructure.getBalance();
	}

	public void build(AccountDataStructureBuilderImpl builder) {
		builder.setIBAN(accountDatastructure.getIban()).setType(accountDatastructure.getAccountType())
				.setBalance(accountDatastructure.getBalance()).setCurrencyCode(accountDatastructure.getCurrencyCode())
				.setStatus(accountDatastructure.getStatus()).setRule(accountDatastructure.getRule());
	}

	public void credit(Amount amount) {
		String currencyCode = accountDatastructure.getCurrencyCode();
		double creditAmount = amount.valueIn(currencyCode);
		if (!isValidAmount(creditAmount))
			throw new InvalidAmountException();
		accountDatastructure.setBalance(accountDatastructure.getBalance() + creditAmount);
	}

	private boolean isValidAmount(double amount) {
		return amount >= 0;
	}

	public void checkPaymentRule(LocalDate paymentDate) throws AccountRuleViolationException {
		Rule rule = Rules.valueOf(accountDatastructure.getRule()).getRule();
		if (!rule.satistfy(paymentDate))
			throw new AccountRuleViolationException(accountDatastructure.getRule());
	}

	@Override
	public String toString() {
		return "Account [accountDatastructure=" + accountDatastructure.toString() + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((accountDatastructure == null) ? 0 : accountDatastructure.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Account other = (Account) obj;
		if (accountDatastructure == null) {
			if (other.accountDatastructure != null)
				return false;
		} else if (!accountDatastructure.equals(other.accountDatastructure))
			return false;
		return true;
	}

}
