package com.progressoft.jip.repository;

import java.util.Map;

import com.progressoft.jip.model.Account;

public interface AccountsRepository {

    public Map<String, Account> loadAccounts();

    public Account loadAccountByIBAN(String IBAN);

    public void updateAccount(String IBAN, Account account);

}