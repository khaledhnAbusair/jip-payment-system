package com.progressoft.jip.datastructures;

import java.util.HashMap;
import java.util.Map;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

@XStreamAlias("Accounts")
public class Accounts {

    @XStreamImplicit(itemFieldName = "Account", keyFieldName = "iban")
    private Map<String, AccountDatastructure> accounts = new HashMap<>();

    public Accounts(Map<String, AccountDatastructure> accounts) {
	this.accounts = accounts;
    }

    public Map<String, AccountDatastructure> getAccounts() {
	return this.accounts;
    }

    public void put(String iban, AccountDatastructure account) {
	accounts.put(iban, account);
    }

}
