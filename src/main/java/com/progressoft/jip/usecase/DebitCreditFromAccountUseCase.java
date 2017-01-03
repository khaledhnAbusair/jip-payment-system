package com.progressoft.jip.usecase;

import java.util.Objects;

import com.progressoft.jip.datastructures.Amount;
import com.progressoft.jip.model.Account;
import com.progressoft.jip.repository.AccountRepository;
import com.progressoft.jip.repository.CurrencyExchangeRateRepository;
import com.progressoft.jip.usecase.exception.NullAccountsRepositoryException;

// This waits for an event
// Needs an observer
// If payment date is today, call debitCredit
public class DebitCreditFromAccountUseCase {

	private AccountRepository repository;
	private CurrencyExchangeRateRepository currencyExchangeRateRepo;

	public DebitCreditFromAccountUseCase(AccountRepository repository,
			CurrencyExchangeRateRepository currencyExchangeRateRepo) {
		this.currencyExchangeRateRepo = currencyExchangeRateRepo;
		if (Objects.isNull(repository))
			throw new NullAccountsRepositoryException();
		this.repository = repository;
	}

	private void debitFrom(Account orderingAccount, Amount amount) {
		orderingAccount.debit(amount);
		repository.updateAccount(orderingAccount);
	}

	private void creditTo(Account beneficiaryAccount, Amount amount) {
		beneficiaryAccount.credit(amount);
		repository.updateAccount(beneficiaryAccount);
	}

	public void debitCredit(String orderingIBAN, String beneficiaryIBAN, double paymentAmount, String currencyCode) {

		Amount amount = new Amount(currencyExchangeRateRepo, paymentAmount, currencyCode);

		Account orderingAccount = repository.loadAccountByIBAN(orderingIBAN);
		Account beneficiaryAccount = repository.loadAccountByIBAN(beneficiaryIBAN);

		debitFrom(orderingAccount, amount);
		creditTo(beneficiaryAccount, amount);
	}

}
