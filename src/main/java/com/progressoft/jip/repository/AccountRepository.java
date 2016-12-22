package com.progressoft.jip.repository;

import java.util.Collection;

import com.progressoft.jip.model.Account;

public interface AccountRepository {

	Account loadAccountByIBAN(String string);

	Collection<Account> loadAccounts();

	void updateAccount(Account account);

	void createAccount(Account account);

}