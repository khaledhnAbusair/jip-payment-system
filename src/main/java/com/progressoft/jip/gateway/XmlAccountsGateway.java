package com.progressoft.jip.gateway;

import java.util.Map;

import com.progressoft.jip.datastructures.AccountDatastructure;
import com.progressoft.jip.datastructures.Accounts;
import com.progressoft.jip.exception.NoneExistedIBANException;
import com.progressoft.jip.exception.NoneMatchedIBANException;

public class XmlAccountsGateway implements AccountsGateway {

    private FileManager<Accounts> fileManager;

    public XmlAccountsGateway(FileManager<Accounts> fileManager) {
	this.fileManager = fileManager;
    }

    @Override
    public Map<String, AccountDatastructure> loadAccounts() {
	return fileManager.loadFileContent().getAccounts();
    }

    @Override
    public AccountDatastructure loadAccountByIBAN(String IBAN) {
	if (!loadAccounts().containsKey(IBAN))
	    throw new NoneExistedIBANException();
	return loadAccounts().get(IBAN);
    }

    @Override
    public void updateAccount(String IBAN, AccountDatastructure account) {
	Map<String, AccountDatastructure> accounts = loadAccounts();
	if (!accounts.containsKey(IBAN))
	    throw new NoneExistedIBANException();
	if (!IBAN.equals(account.getIban()))
	    throw new NoneMatchedIBANException();
	accounts.put(IBAN, account);
	fileManager.updateFileContent(new Accounts(accounts));
    }

}
