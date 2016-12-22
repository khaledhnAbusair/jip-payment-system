package com.progressoft.jip.repository.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.progressoft.jip.datastructures.AccountDatastructure;
import com.progressoft.jip.gateway.AccountGateway;
import com.progressoft.jip.model.Account;
import com.progressoft.jip.repository.AccountRepository;
import com.progressoft.jip.view.AccountView;

public class AccountRepositoryImpl implements AccountRepository {

	private AccountGateway accountGateway;

	public AccountRepositoryImpl(AccountGateway accountGateway) {
		this.accountGateway = accountGateway;
	}

	@Override
	public Account loadAccountByIBAN(String iban) {
		AccountDatastructure loadedAccount = accountGateway.loadAccountByIBAN(iban);
		Account account = new Account(loadedAccount);
		return account;
	}

	@Override
	public Collection<Account> loadAccounts() {
		List<Account> accounts = new ArrayList<>();
		for (AccountDatastructure accountDS : accountGateway.loadAccounts()) {
			accounts.add(new Account(accountDS));
		}
		return accounts;
	}

	@Override
	public void updateAccount(Account account) {
		AccountDatastructure accountDS = new AccountDatastructure();
		AccountView view = new AccountView();
		account.buildAccountView(view);
		view = view.build();
		accountDS.setIban(view.getIBAN());
		accountDS.setBalance(view.getBalance());
		accountDS.setCurrencyCode(view.getCurrencyCode());
		accountDS.setAccountType(view.getType());
		accountDS.setStatus(view.getStatus());
		accountGateway.updateAccount(accountDS);
	}

	@Override
	public void createAccount(Account account) {
		AccountDatastructure accountDS = new AccountDatastructure();
		AccountView view = new AccountView();
		account.buildAccountView(view);
		view = view.build();
		accountDS.setIban(view.getIBAN());
		accountDS.setBalance(view.getBalance());
		accountDS.setCurrencyCode(view.getCurrencyCode());
		accountDS.setAccountType(view.getType());
		accountDS.setStatus(view.getStatus());
		accountGateway.createAccount(accountDS);
	}

}
