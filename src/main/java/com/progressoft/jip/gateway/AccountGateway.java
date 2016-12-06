package com.progressoft.jip.gateway;

import java.util.Collection;

import com.progressoft.jip.datastructures.AccountDatastructure;

public interface AccountGateway {

    public AccountDatastructure loadAccountByIBAN(String IBAN);

    public Collection<AccountDatastructure> loadAccounts();

}