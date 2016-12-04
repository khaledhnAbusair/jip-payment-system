package com.progressoft.jip.repository.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.progressoft.jip.datastructures.AccountDatastructure;
import com.progressoft.jip.gateway.AccountGateway;
import com.progressoft.jip.model.Account;
import com.progressoft.jip.model.Currency;
import com.progressoft.jip.repository.AccountRepository;

public class AccountsRepositoryImpl implements AccountRepository {

    private AccountGateway accountGateway;
    private CurrencyRepositoryImpl currencyRepository;

    public AccountsRepositoryImpl(AccountGateway accountGateway, CurrencyRepositoryImpl currencyRepository) {
	this.accountGateway = accountGateway;
	this.currencyRepository = currencyRepository;
    }

    @Override
    public Account loadAccountByIBAN(String iban) {
	AccountDatastructure loadedAccount = accountGateway.loadAccountByIBAN(iban);
	Currency currency = getAccountCurrency(loadedAccount);
	Account account = new Account(loadedAccount, currency);
	return account;
    }

    @Override
    public Collection<Account> loadAccounts() {
	Iterable<AccountDatastructure> accountsBeans = accountGateway.loadAccounts();
	List<Account> accounts = new ArrayList<>();
	for (AccountDatastructure accountDS : accountsBeans) {
	    Currency currency = getAccountCurrency(accountDS);
	    accounts.add(new Account(accountDS, currency));
	}
	return accounts;
    }

    private Currency getAccountCurrency(AccountDatastructure accountDS) {
	return currencyRepository.loadCurrencyByCode(accountDS.getCurrencyCode());
    }

}
