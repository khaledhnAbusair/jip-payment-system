package com.progressoft.jip.gateway.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.sql.SQLException;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.commons.dbutils.QueryRunner;
import org.junit.Before;
import org.junit.Test;

import com.progressoft.jip.datastructures.AccountDatastructure;
import com.progressoft.jip.factory.impl.AccountGatewayDBBehaviorsFactoryImpl;
import com.progressoft.jip.gateway.AccountGateway;
import com.progressoft.jip.gateway.exception.AccountNotFoundException;
import com.progressoft.jip.gateway.exception.InvalidBalanceException;
import com.progressoft.jip.gateway.exception.NoAccountInsertedException;
import com.progressoft.jip.gateway.exception.NullAccountIBANException;
import com.progressoft.jip.utilities.DataBaseSettings;

public class MySQLAccountGatewayTest {

	private AccountGateway accountsGateway;
	private AccountDatastructure accountDataStructure = new AccountDatastructure();

	@Before
	public void setUp() {

		BasicDataSource datasource = connectionConfiguration();
		accountsGateway = new MySqlAccountGateway(datasource, new AccountGatewayDBBehaviorsFactoryImpl());
		try {
			new QueryRunner(datasource).update("delete from ACCOUNT where IBAN='JO94CBJO0010000000000131000399'");
		} catch (SQLException e) {
			throw new IllegalStateException("Couldn't prepare database");
		}

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
	public void givenMySQLAccountGateway_CallingUpdateAcount_PassingExistingAccount_ThenCallingLoadAccountByIBAN_ShouldReturnUpdatedAccount() {
		AccountDatastructure originalAccount = new AccountDatastructure();
		originalAccount.setIban("JO94CBJO0010000000000131000302");
		originalAccount.setAccountType("TYPE");
		double newBalance = Math.random() * 500;
		originalAccount.setBalance(newBalance);
		originalAccount.setStatus("ACTIVE");
		originalAccount.setCurrencyCode("USD");
		originalAccount.setRule("THIS_MONTH");
		accountsGateway.updateAccount(originalAccount);
		AccountDatastructure updatedAccount = accountsGateway.loadAccountByIBAN("JO94CBJO0010000000000131000302");
		assertTrue(Math.abs(newBalance - updatedAccount.getBalance()) <= 1e-3);
	}

	@Test(expected = InvalidBalanceException.class)
	public void givenMySQLAccountGateway_CallingUpdateAcount_PassingNegativeBalance_ShouldThrowInvalidBalance() {
		AccountDatastructure originalAccount = new AccountDatastructure();
		originalAccount.setIban("JO94CBJO0010000000000131000302");
		originalAccount.setAccountType("TYPE");
		originalAccount.setBalance(-200);
		originalAccount.setStatus("ACTIVE");
		accountsGateway.updateAccount(originalAccount);
	}

	@Test
	public void givenMySQLAccountGateway_CallingInsertAccount_ShouldCreateAccount() {
		accountDataStructure.setIban("JO94CBJO0010000000000131000399");
		accountDataStructure.setBalance(500);
		accountDataStructure.setCurrencyCode("JOD");
		accountDataStructure.setAccountType("investment");
		accountDataStructure.setStatus("ACTIVE");
		accountDataStructure.setRule("THIS_MONTH");
		accountsGateway.createAccount(accountDataStructure);
		assertEquals(accountDataStructure.getIban(),
				accountsGateway.loadAccountByIBAN("JO94CBJO0010000000000131000399").getIban());
	}

	@Test(expected = NoAccountInsertedException.class)
	public void givenMySQLAccountGateway_CallingInsertAccount_PassingInvalidData_ShouldThrowNoAccountInsertedException() {
		accountDataStructure.setIban(null);
		accountDataStructure.setBalance(500);
		accountDataStructure.setCurrencyCode("JOD");
		accountDataStructure.setAccountType("investment");
		accountDataStructure.setStatus("ACTIVE");
		accountsGateway.createAccount(accountDataStructure);
		assertEquals(accountDataStructure.getIban(),
				accountsGateway.loadAccountByIBAN("JO94CBJO0010000000000131000321").getIban());
	}

	@Test
	public void givenAccountsGateway_CallingLoadAccounts_ShouldReturnAccounts() {
		Iterable<AccountDatastructure> account = accountsGateway.loadAccounts();
		assertTrue(account.iterator().hasNext());
	}

}
