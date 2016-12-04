package com.progressoft.jip.repository.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.apache.commons.dbcp2.BasicDataSource;
import org.junit.Before;
import org.junit.Test;

import com.progressoft.jip.exception.AccountNotFoundException;
import com.progressoft.jip.exception.NullAccountIBANException;
import com.progressoft.jip.gateway.AccountGateway;
import com.progressoft.jip.gateway.CurrencyGateway;
import com.progressoft.jip.gateway.impl.MySqlAccountGateway;
import com.progressoft.jip.gateway.impl.SQLCurrencyGateway;
import com.progressoft.jip.model.Account;
import com.progressoft.jip.repository.AccountRepository;

public class MySQLAccountRepositoryTest {
    private static final String DRIVER_CLASS_NAME = "com.mysql.jdbc.Driver";
    private static final String DRIVER_USERNAME = "root";
    private static final String DRIVER_PASSWORD = "root";
    private static final String DRIVER_URL = "jdbc:mysql://localhost:3306/payment_system";

    private AccountRepository accountRepository;

    @Before
    public void setup() {
	BasicDataSource dataSource = connectionConfiguration();
	CurrencyGateway currencyGateway = new SQLCurrencyGateway(dataSource);
	CurrencyRepositoryImpl currencyRepository = new CurrencyRepositoryImpl(currencyGateway);
	AccountGateway accountGateway = new MySqlAccountGateway(dataSource);
	accountRepository = new AccountsRepositoryImpl(accountGateway, currencyRepository);
    }

    private BasicDataSource connectionConfiguration() {
	BasicDataSource dataSource = new BasicDataSource();
	dataSource.setUrl(DRIVER_URL);
	dataSource.setUsername(DRIVER_USERNAME);
	dataSource.setPassword(DRIVER_PASSWORD);
	dataSource.setDriverClassName(DRIVER_CLASS_NAME);
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
	assertEquals("JO94CBJO0010000000000131000302", account.getIban());
    }

    @Test
    public void givenAccountsRepository_CallingLoadAccounts_ShouldReturnAccounts() {
	Iterable<Account> account = accountRepository.loadAccounts();
	assertTrue(account.iterator().hasNext());
    }

}
