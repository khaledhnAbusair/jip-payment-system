package com.progressoft.jip.usecase;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.progressoft.jip.model.Account;
import com.progressoft.jip.repository.AccountRepository;
import com.progressoft.jip.view.AccountView;

public class LoadAccountsUseCase {

	private AccountRepository accountRepository;

	public LoadAccountsUseCase(AccountRepository repository) {
		this.accountRepository = repository;
	}

	public Collection<AccountView> loadAccounts() {
		List<AccountView> accounts = new ArrayList<>();
		AccountView accountInfo = new AccountView();
		for (Account account : accountRepository.loadAccounts()) {
			account.buildAccountView(accountInfo);
			accounts.add(accountInfo.build());
		}
		return accounts;
	}
}
