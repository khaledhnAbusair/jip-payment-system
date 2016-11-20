package com.progressoft.jip.repository.impl;

import java.util.HashMap;
import java.util.Map;

import com.progressoft.jip.datastructures.AccountDatastructure;
import com.progressoft.jip.exception.NoneExistedIBANException;
import com.progressoft.jip.gateway.AccountsGateway;
import com.progressoft.jip.model.Account;
import com.progressoft.jip.repository.AccountsRepository;

public class AccountsRepositoryImpl implements AccountsRepository {

    private AccountsGateway accountsGateway;
    private Map<String, Account> accounts = new HashMap<>();

    public AccountsRepositoryImpl(AccountsGateway accountsGateway) {
	this.accountsGateway = accountsGateway;
    }

    @Override
    public Map<String, Account> loadAccounts() {
	Map<String, AccountDatastructure> accountsDsMap = accountsGateway.loadAccounts();
	for (AccountDatastructure accountDs : accountsDsMap.values())
	    accounts.put(accountDs.getIban(), Account.newAccountModel(accountDs));
	return this.accounts;
    }

    @Override
    public Account loadAccountByIBAN(String IBAN) {
	if (accounts.isEmpty())
	    loadAccounts();
	if (!accounts.containsKey(IBAN))
	    throw new NoneExistedIBANException();
	return accounts.get(IBAN);
    }

    @Override
    public void updateAccount(String IBAN, Account account) {
	accountsGateway.updateAccount(IBAN, account.asDataStructure());
	accounts.put(IBAN, account);
    }

}
