package com.progressoft.jip.gateway.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.apache.commons.dbcp2.BasicDataSource;
import org.junit.Before;
import org.junit.Test;

import com.progressoft.jip.datastructures.AccountDatastructure;
import com.progressoft.jip.exception.AccountNotFoundException;
import com.progressoft.jip.exception.NullAccountIBANException;
import com.progressoft.jip.gateway.AccountGateway;

public class MySQLAccountGatewayTest {

	private static final String DRIVER_CLASS_NAME = "com.mysql.jdbc.Driver";
	private static final String DRIVER_USERNAME = "root";
	private static final String DRIVER_PASSWORD = "root";
	private static final String DRIVER_URL = "jdbc:mysql://localhost:3306/payment_system";
	private AccountGateway accountsGateway;

	@Before
	public void setUp() {
		accountsGateway = new MySqlAccountGateway(connectionConfiguration());
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
