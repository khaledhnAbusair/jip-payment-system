package com.progressoft.jip.repository.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.apache.commons.dbcp2.BasicDataSource;
import org.junit.Before;
import org.junit.Test;

import com.progressoft.jip.factory.impl.AccountGatewayDBBehaviorsFactoryImpl;
import com.progressoft.jip.factory.impl.CurrencyGatewayDBBehaviorsFactoryImpl;
import com.progressoft.jip.gateway.AccountGateway;
import com.progressoft.jip.gateway.CurrencyGateway;
import com.progressoft.jip.gateway.exception.AccountNotFoundException;
import com.progressoft.jip.gateway.exception.NullAccountIBANException;
import com.progressoft.jip.gateway.impl.MySqlAccountGateway;
import com.progressoft.jip.gateway.impl.MySqlCurrencyGateway;
import com.progressoft.jip.model.Account;
import com.progressoft.jip.repository.AccountRepository;
import com.progressoft.jip.utilities.DataBaseSettings;

public class AccountRepositoryTest {

    private AccountRepository accountRepository;

    @Before
    public void setup() {
	BasicDataSource dataSource = connectionConfiguration();
	CurrencyGateway currencyGateway = new MySqlCurrencyGateway(dataSource, new CurrencyGatewayDBBehaviorsFactoryImpl());
	CurrencyRepositoryImpl currencyRepository = new CurrencyRepositoryImpl(currencyGateway);
	AccountGateway accountGateway = new MySqlAccountGateway(dataSource,new AccountGatewayDBBehaviorsFactoryImpl());
	accountRepository = new AccountRepositoryImpl(accountGateway, currencyRepository);
    }

    private BasicDataSource connectionConfiguration() {
	BasicDataSource dataSource = new BasicDataSource();
	DataBaseSettings dbSettings = DataBaseSettings.getInstance();
	dataSource.setUrl(dbSettings.url());
	dataSource.setUsername(dbSettings.username());
	dataSource.setPassword(dbSettings.password());
	dataSource.setDriverClassName(dbSettings.driverClassName());
	return dataSource;
    }

    @Test(expected = NullAccountIBANException.class)
    public void givenMySQLAccountRepository_CallingLoadAccountByIBAN_PassingNullIBANCode_ShouldThrowNullAccountIBAN() {
	accountRepository.loadAccountByIBAN(null);
    }

    @Test(expected = NullAccountIBANException.class)
    public void givenMySQLAccountRepository_CallingLoadAccountByIBAN_PassingEmptyIBANCode_ShouldThrowNullAccountIBAN() {
	accountRepository.loadAccountByIBAN("");
    }

    @Test(expected = AccountNotFoundException.class)
    public void givenMySQLAccountRepository_CallingLoadAccountByIBAN_PassingUnavailableIBANCode_ShouldThrowNullAccountIBAN() {
	accountRepository.loadAccountByIBAN("ekjsfnefk");
    }

    @Test
    public void givenMySQLAccountRepository_CallingLoadAccountByIBAN_PassingAvailableIBANCode_ShouldReturnAccount() {
	Account account = accountRepository.loadAccountByIBAN("JO94CBJO0010000000000131000302");
	//assertEquals("JO94CBJO0010000000000131000302", account.getIban());
    }

    @Test
    public void givenAccountsRepository_CallingLoadAccounts_ShouldReturnAccounts() {
	Iterable<Account> account = accountRepository.loadAccounts();
	assertTrue(account.iterator().hasNext());
    }

}
