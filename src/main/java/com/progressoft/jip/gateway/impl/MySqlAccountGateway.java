package com.progressoft.jip.gateway.impl;

import java.util.Collection;

import javax.sql.DataSource;

import com.progressoft.jip.datastructures.AccountDatastructure;
import com.progressoft.jip.factory.AccountGatewayDBBehaviorsFactory;
import com.progressoft.jip.factory.Behavior;
import com.progressoft.jip.gateway.AbstractGateway;
import com.progressoft.jip.gateway.AccountGateway;

public class MySqlAccountGateway extends AbstractGateway implements AccountGateway {

	private Behavior<AccountDatastructure> loadAccountByIBAN;
	private Behavior<Collection<AccountDatastructure>> loadAccounts;
	private Behavior<Void> updateAccount;
	private Behavior<Void> createAccount;

	public MySqlAccountGateway(DataSource dataSource, AccountGatewayDBBehaviorsFactory factory) {
		super(dataSource);
		this.loadAccountByIBAN = factory.loadAccountByIBAN();
		this.loadAccounts = factory.loadAccounts();
		this.updateAccount = factory.updateAccount();
		this.createAccount = factory.createAccount();
	}

	@Override
	public AccountDatastructure loadAccountByIBAN(String IBAN) {
		return loadAccountByIBAN.execute(dataSource, IBAN);
	}

	@Override
	public Collection<AccountDatastructure> loadAccounts() {
		return loadAccounts.execute(dataSource);
	}

	@Override
	public void updateAccount(AccountDatastructure account) {
		updateAccount.execute(dataSource, account);
	}

	@Override
	public void createAccount(AccountDatastructure newAccount) {
//		createAccount.execute(dataSource, newAccount);
	}

}
