package com.progressoft.jip.gateway.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.apache.commons.dbcp2.BasicDataSource;
import org.junit.Before;
import org.junit.Test;

import com.progressoft.jip.datastructures.AccountDatastructure;
import com.progressoft.jip.factory.impl.AccountGatewayDBBehaviorsFactoryImpl;
import com.progressoft.jip.gateway.AccountGateway;
import com.progressoft.jip.gateway.exception.AccountNotFoundException;
import com.progressoft.jip.gateway.exception.NullAccountIBANException;
import com.progressoft.jip.utilities.DataBaseSettings;

public class MySQLAccountGatewayTest {

    private AccountGateway accountsGateway;

    @Before
    public void setUp() {
	accountsGateway = new MySqlAccountGateway(connectionConfiguration(),new AccountGatewayDBBehaviorsFactoryImpl());
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
    public void givenMySQLAccountGateway_CallingLoadAccountByIBAN_PassingNullIBANCode_ShouldThrowNullAccountIBAN() {
	accountsGateway.loadAccountByIBAN(null);
    }

    @Test(expected = NullAccountIBANException.class)
    public void givenMySQLAccountGateway_CallingLoadAccountByIBAN_PassingEmptyIBANCode_ShouldThrowNullAccountIBAN() {
	accountsGateway.loadAccountByIBAN("");
    }

    @Test(expected = AccountNotFoundException.class)
    public void givenMySQLAccountGateway_CallingLoadAccountByIBAN_PassingUnavailableIBANCode_ShouldThrowNullAccountIBAN() {
	accountsGateway.loadAccountByIBAN("ekjsfnefk");
    }

    @Test
    public void givenMySQLAccountGateway_CallingLoadAccountByIBAN_PassingAvailableIBANCode_ShouldReturnAccount() {
	AccountDatastructure accounts = accountsGateway.loadAccountByIBAN("JO94CBJO0010000000000131000302");
	assertEquals("JO94CBJO0010000000000131000302", accounts.getIban());
    }

    @Test
    public void givenAccountsGateway_CallingLoadAccounts_ShouldReturnAccounts() {
	Iterable<AccountDatastructure> account = accountsGateway.loadAccounts();
	assertTrue(account.iterator().hasNext());
    }

}
