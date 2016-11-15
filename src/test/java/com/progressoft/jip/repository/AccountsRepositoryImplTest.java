package com.progressoft.jip.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import com.progressoft.jip.datastructures.AccountDatastructure;
import com.progressoft.jip.datastructures.Accounts;
import com.progressoft.jip.datastructures.Balance;
import com.progressoft.jip.datastructures.Currency;
import com.progressoft.jip.exception.NoneExistedIBANException;
import com.progressoft.jip.exception.NoneMatchedIBANException;
import com.progressoft.jip.gateway.AccountsGateway;
import com.progressoft.jip.gateway.FileManager;
import com.progressoft.jip.gateway.XmlAccountsGateway;
import com.progressoft.jip.gateway.XmlFileManager;
import com.progressoft.jip.model.Account;

public class AccountsRepositoryImplTest {

    private static final String ROOT_RESOURCES = "src/test/resources/";
    private static final String TEMP_IBAN = "iban555";
    private static final String IBAN3 = "iban3";
    private static final String IBAN = "iban";
    private static final String IBAN2 = "iban2";
    private AccountsGateway accountsGateway;
    private AccountsRepository accountsRepository;
    private FileManager<Accounts> fileManager;

    @Before
    public void setup() {
	fileManager = new XmlFileManager<Accounts>(ROOT_RESOURCES + "accounts.xml", Accounts.class);
	accountsGateway = new XmlAccountsGateway(fileManager);
	accountsRepository = new AccountsRepositoryImpl(accountsGateway);
    }

    @Test
    public void givenRepository_CallingLoadAccounts_ShouldReturnAccounts() {
	assertEquals(3, accountsRepository.loadAccounts().size());
    }

    @Test
    public void givenRepository_CallingLoadAccountByIBAN_PassingExistedIBANAccount_ShouldReturnTheAccounts() {
	assertNotNull(accountsRepository.loadAccountByIBAN(IBAN));
    }

    @Test(expected = NoneExistedIBANException.class)
    public void givenRepository_CallingLoadAccountByIBAN_PassingNoneExistedIBANAccount_ShouldThrowNoneExistedIBAN() {
	assertNotNull(accountsRepository.loadAccountByIBAN(TEMP_IBAN));
    }

    @Test
    public void givenRepository_CallingLoadAccounts_ThenCallingUpdateAccount_WithChangedBalance_PassingExistedIBANAndAccountWithExistedSameIBAN_ShouldUpdateAccount() {
	accountsRepository.loadAccounts();
	AccountDatastructure account = Account.copyOf(accountsRepository.loadAccountByIBAN(IBAN2)).asDataStructure();
	Double rand = Math.random();
	account.setBalance(new Balance(rand, Currency.JOD));
	accountsRepository.updateAccount(IBAN2, Account.newAccountModel(account));
	assertTrue(Math.abs(accountsRepository.loadAccountByIBAN(IBAN2).asDataStructure().getBalance().getAmount() - rand) <= 1e-7);
    }

    @Test
    public void givenRepository_CallingUpdateAccount_WithChangedBalance_PassingExistedIBANAndAccountWithExistedSameIBAN_ShouldUpdateAccount() {
	AccountDatastructure account =  Account.copyOf(accountsRepository.loadAccountByIBAN(IBAN2)).asDataStructure();
	Double rand = Math.random();
	account.setBalance(new Balance(rand, Currency.JOD));
	accountsRepository.updateAccount(IBAN2, Account.newAccountModel(account));
	assertTrue(Math.abs(accountsRepository.loadAccountByIBAN(IBAN2).asDataStructure().getBalance().getAmount() - rand) <= 1e-7);
    }

    @Test(expected = NoneMatchedIBANException.class)
    public void givenAccountsGateway_CallingUpdateAccount_WithChangedBalance_PassingExistedIBANAndAccountNoneMatchedIBAN_ShouldThrowNoneMatchedIBAN() {
	AccountDatastructure account = Account.copyOf(accountsRepository.loadAccountByIBAN(IBAN2)).asDataStructure();
	Double rand = Math.random();
	account.setBalance(new Balance(rand, Currency.JOD));
	accountsRepository.updateAccount(IBAN3, Account.newAccountModel(account));
	assertTrue(Math.abs(accountsRepository.loadAccountByIBAN(IBAN3).asDataStructure().getBalance().getAmount() - rand) <= 1e-7);
    }

    @Test(expected = NoneExistedIBANException.class)
    public void givenAccountsGateway_CallingUpdateAccount_WithChangedBalance_PassingNoneExistedIBANAndAccountAnotherNoneExistedIBAN_ShouldThrowNoneExistedIBAN() {
	AccountDatastructure account = Account.copyOf(accountsRepository.loadAccountByIBAN(IBAN2)).asDataStructure();
	Double rand = Math.random();
	account.setBalance(new Balance(rand, Currency.JOD));
	accountsRepository.updateAccount(TEMP_IBAN, Account.newAccountModel(account));
	assertTrue(Math.abs(accountsRepository.loadAccountByIBAN(IBAN3).asDataStructure().getBalance().getAmount() - rand) <= 1e-7);
    }

}
