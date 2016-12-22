package com.progressoft.jip.repository.impl;

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
import com.progressoft.jip.gateway.exception.NullAccountIBANException;
import com.progressoft.jip.gateway.impl.MySqlAccountGateway;
import com.progressoft.jip.model.Account;
import com.progressoft.jip.repository.AccountRepository;
import com.progressoft.jip.utilities.DataBaseSettings;
import com.progressoft.jip.view.AccountView;

public class AccountRepositoryTest {

	private AccountRepository accountRepository;

	@Before
	public void setup() {
		BasicDataSource dataSource = connectionConfiguration();
		AccountGateway accountGateway = new MySqlAccountGateway(dataSource, new AccountGatewayDBBehaviorsFactoryImpl());
		accountRepository = new AccountRepositoryImpl(accountGateway);
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
	public void givenAccountRepository_CallingLoadAccountByIBAN_PassingNullIBANCode_ShouldThrowNullAccountIBAN() {
		accountRepository.loadAccountByIBAN(null);
	}

	@Test(expected = NullAccountIBANException.class)
	public void givenAccountRepository_CallingLoadAccountByIBAN_PassingEmptyIBANCode_ShouldThrowNullAccountIBAN() {
		accountRepository.loadAccountByIBAN("");
	}

	@Test(expected = AccountNotFoundException.class)
	public void givenAccountRepository_CallingLoadAccountByIBAN_PassingUnavailableIBANCode_ShouldThrowNullAccountIBAN() {
		accountRepository.loadAccountByIBAN("ekjsfnefk");
	}

	@Test
	public void givenAccountRepository_CallingLoadAccountByIBAN_PassingAvailableIBANCode_ShouldReturnAccount() {
		Account account = accountRepository.loadAccountByIBAN("JO94CBJO0010000000000131000302");
		AccountView view = new AccountView();
		account.buildAccountView(view);
		assertEquals("JO94CBJO0010000000000131000302", view.build().getIban());
	}

	@Test
	public void givenAccountRepository_CallingUpdateAcount_PassingExistingAccount_ThenCallingLoadAccountByIBAN_ShouldReturnUpdatedAccount() {
		AccountDatastructure originalAccount = new AccountDatastructure();
		originalAccount.setIban("JO94CBJO0010000000000131000302");
		originalAccount.setAccountType("TYPE");
		double newBalance = Math.random() * 500;
		originalAccount.setBalance(newBalance);
		originalAccount.setStatus("ACTIVE");
		accountRepository.updateAccount(new Account(originalAccount));
		Account updatedAccount = accountRepository.loadAccountByIBAN("JO94CBJO0010000000000131000302");
		AccountView view = new AccountView();
		updatedAccount.buildAccountView(view);
		assertTrue(Math.abs(view.getBalance() - originalAccount.getBalance()) <= 1e-3);
	}

	@Test(expected = InvalidBalanceException.class)
	public void givenMySQLAccountGateway_CallingUpdateAcount_PassingNigativeBalance_ShouldThrowInvalidBalance() {
		AccountDatastructure originalAccount = new AccountDatastructure();
		originalAccount.setIban("JO94CBJO0010000000000131000302");
		originalAccount.setAccountType("TYPE");
		originalAccount.setBalance(-200);
		originalAccount.setStatus("ACTIVE");
		accountRepository.updateAccount(new Account(originalAccount));
	}

	@Test
	public void givenAccountsRepository_CallingLoadAccounts_ShouldReturnAccounts() {
		Iterable<Account> account = accountRepository.loadAccounts();
		assertTrue(account.iterator().hasNext());
	}

	

}
