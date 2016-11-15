package com.progressoft.jip.gateway;

import java.util.Map;

import com.progressoft.jip.datastructures.AccountDatastructure;

public interface AccountsGateway {

    public Map<String, AccountDatastructure> loadAccounts();

    public AccountDatastructure loadAccountByIBAN(String IBAN);

    public void updateAccount(String IBAN, AccountDatastructure account);

}