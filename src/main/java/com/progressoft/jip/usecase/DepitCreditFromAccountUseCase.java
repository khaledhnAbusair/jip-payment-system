package com.progressoft.jip.usecase;

import java.util.Objects;

import com.progressoft.jip.model.Account;
import com.progressoft.jip.repository.AccountRepository;
import com.progressoft.jip.usecase.exception.NullAccountsRepositoryException;

public class DepitCreditFromAccountUseCase {

	private AccountRepository repository;

	public DepitCreditFromAccountUseCase(AccountRepository repository) {
		if (Objects.isNull(repository))
			throw new NullAccountsRepositoryException();
		this.repository = repository;
	}

	private void depitFrom(Account orderingAccount, double amount) {
		orderingAccount.depit(amount);
		repository.updateAccount(orderingAccount);
	}

	private void creditTo(Account beneficiaryAccount, double amount) {
		beneficiaryAccount.credit(amount);
		repository.updateAccount(beneficiaryAccount);
	}

	public void depitCredit(String orderingIBAN, String beneficiaryIBAN, double amount) {
		depitFrom(repository.loadAccountByIBAN(orderingIBAN), amount);
		creditTo(repository.loadAccountByIBAN(beneficiaryIBAN), amount);
	}

}
