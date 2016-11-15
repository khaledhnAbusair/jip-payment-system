package com.progressoft.jip.gateway;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import com.progressoft.jip.datastructures.AccountDatastructure;
import com.progressoft.jip.datastructures.Accounts;
import com.progressoft.jip.datastructures.Balance;
import com.progressoft.jip.datastructures.Currency;
import com.progressoft.jip.exception.NoneExistedIBANException;
import com.progressoft.jip.exception.NoneMatchedIBANException;

public class XmlAccountsGatewayTest {

    private static final String ROOT_RESOURCES = "src/test/resources/";
    private static final String TEMP_IBAN = "iban555";
    private static final String IBAN3 = "iban3";
    private static final String IBAN = "iban";
    private static final String IBAN2 = "iban2";
    private AccountsGateway accountsGateway;
    private FileManager<Accounts> fileManager;

    @Before
    public void setup() {
	fileManager = new XmlFileManager<Accounts>(ROOT_RESOURCES + "accounts.xml", Accounts.class);
	accountsGateway = new XmlAccountsGateway(fileManager);
    }

    @Test
    public void givenAccountsGateway_CallingLoadAccounts_ShouldLoadAccounts() {
	assertEquals(3, accountsGateway.loadAccounts().size());
    }

    @Test
    public void givenAccountGateway_CallingLoadAccountByIBAN_ShouldReturnTheAccount() {
	assertEquals("iban2", accountsGateway.loadAccountByIBAN(IBAN2).getIban());
    }

    @Test(expected = NoneExistedIBANException.class)
    public void givenAccountGateway_CallingLoadAccountByIBAN_PassingNoneExistedIBAN_ShouldThrowNoneExistedIBAN() {
	accountsGateway.loadAccountByIBAN(TEMP_IBAN).getIban();
    }

    @Test
    public void givenAccountsGateway_CallingUpdateAccount_WithChangedBalance_PassingExistedIBANAndAccountWithExistedSameIBAN_ShouldUpdateAccount() {
	AccountDatastructure account = accountsGateway.loadAccountByIBAN(IBAN);
	Double rand = Math.random();
	account.setBalance(new Balance(rand, Currency.JOD));
	accountsGateway.updateAccount(IBAN, account);
	assertTrue(Math.abs(accountsGateway.loadAccountByIBAN(IBAN).getBalance().getAmount() - rand) <= 1e-7);
    }

    @Test(expected = NoneMatchedIBANException.class)
    public void givenAccountsGateway_CallingUpdateAccount_WithChangedBalance_PassingExistedIBANAndAccountNoneMatchedIBAN_ShouldThrowNoneMatchedIBAN() {
	AccountDatastructure account = accountsGateway.loadAccountByIBAN(IBAN2);
	Double rand = Math.random();
	account.setBalance(new Balance(rand, Currency.JOD));
	accountsGateway.updateAccount(IBAN3, account);
	assertTrue(Math.abs(accountsGateway.loadAccountByIBAN(IBAN3).getBalance().getAmount() - rand) <= 1e-7);
    }

    @Test(expected = NoneExistedIBANException.class)
    public void givenAccountsGateway_CallingUpdateAccount_WithChangedBalance_PassingNoneExistedIBANAndAccountAnotherNoneExistedIBAN_ShouldThrowNoneExistedIBAN() {
	AccountDatastructure account = accountsGateway.loadAccountByIBAN(IBAN2);
	Double rand = Math.random();
	account.setBalance(new Balance(rand, Currency.JOD));
	accountsGateway.updateAccount(TEMP_IBAN, account);
	assertTrue(Math.abs(accountsGateway.loadAccountByIBAN(IBAN2).getBalance().getAmount() - rand) <= 1e-7);
    }

}
