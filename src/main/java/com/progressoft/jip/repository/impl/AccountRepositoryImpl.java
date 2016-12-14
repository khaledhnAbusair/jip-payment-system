package com.progressoft.jip.repository.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.dbcp.BasicDataSource;

import com.progressoft.jip.datastructures.AccountDatastructure;
import com.progressoft.jip.factory.impl.CurrencyGatewayDBBehaviorsFactoryImpl;
import com.progressoft.jip.gateway.AccountGateway;
import com.progressoft.jip.gateway.impl.MySqlCurrencyGateway;
import com.progressoft.jip.model.Account;
import com.progressoft.jip.model.Currency;
import com.progressoft.jip.repository.AccountRepository;
import com.progressoft.jip.repository.CurrencyRepository;
import com.progressoft.jip.utilities.DataBaseSettings;

public class AccountRepositoryImpl implements AccountRepository {

    private AccountGateway accountGateway;
    private CurrencyRepository currencyRepository;

    public AccountRepositoryImpl(AccountGateway accountGateway) {
	this.accountGateway = accountGateway;
	this.currencyRepository = new CurrencyRepositoryImpl(new MySqlCurrencyGateway(initDataSource(),
		new CurrencyGatewayDBBehaviorsFactoryImpl()));
    }

    public AccountRepositoryImpl(AccountGateway accountGateway, CurrencyRepository currencyRepository) {
	this.accountGateway = accountGateway;
	this.currencyRepository = currencyRepository;
    }

    @Override
    public Account loadAccountByIBAN(String iban) {
	AccountDatastructure loadedAccount = accountGateway.loadAccountByIBAN(iban);
	Currency currency = getAccountCurrency(loadedAccount);
	Account account = new Account(loadedAccount, currency);
	return account;
    }

    @Override
    public Collection<Account> loadAccounts() {
	List<Account> accounts = new ArrayList<>();
	for (AccountDatastructure accountDS : accountGateway.loadAccounts()) {
	    Currency currency = getAccountCurrency(accountDS);
	    accounts.add(new Account(accountDS, currency));
	}
	return accounts;
    }

    private Currency getAccountCurrency(AccountDatastructure accountDS) {
	return currencyRepository.loadCurrencyByCode(accountDS.getCurrencyCode());
    }

    private BasicDataSource initDataSource() {
	BasicDataSource dataSource = new BasicDataSource();
	dataSource.setUsername(DataBaseSettings.getInstance().username());
	dataSource.setPassword(DataBaseSettings.getInstance().password());
	dataSource.setUrl(DataBaseSettings.getInstance().url());
	dataSource.setDriverClassName(DataBaseSettings.getInstance().driverClassName());
	return dataSource;
    }

}
