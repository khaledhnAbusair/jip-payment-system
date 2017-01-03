package com.progressoft.jip.usecase;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;
import org.junit.Before;
import org.junit.Test;

import com.progressoft.jip.context.AppContextImpl;
import com.progressoft.jip.gateway.exception.AccountNotFoundException;
import com.progressoft.jip.repository.AccountRepository;
import com.progressoft.jip.repository.CurrencyExchangeRateRepository;
import com.progressoft.jip.usecase.exception.AccountDoesNotHaveEnoughBalanceException;
import com.progressoft.jip.usecase.exception.InvalidAmountException;
import com.progressoft.jip.usecase.exception.NullAccountsRepositoryException;
import com.progressoft.jip.utilities.DataBaseSettings;

public class DebitCreditFromAccountUseCaseTest {

	private static final String BENEFICIARY_ACCOUNT = "RO49AAAA1B31007593840000";
	private static final String ORDERING_IBAN = "JO94CBJO0010000000000131000302";
	private AccountRepository repository;
	private DebitCreditFromAccountUseCase usecase;

	@Before
	public void setup() {
		repository = new AppContextImpl(dataSource()).accountRepository();
		CurrencyExchangeRateRepository currencyExchangeRepository = new AppContextImpl(dataSource())
				.currentExchangeRateRepository();
		usecase = new DebitCreditFromAccountUseCase(repository, currencyExchangeRepository);
	}

	@Test(expected = NullAccountsRepositoryException.class)
	public void createDebitFromAccountUseCase_PassingNullRepository_ShouldThrowNullAccountsRepository() {
		new DebitCreditFromAccountUseCase(null, null);
	}

	@Test(expected = AccountDoesNotHaveEnoughBalanceException.class)
	public void givenDebitCreditUseCase_CallingDebitCredit_PassingExistingAccounts_WithAmountGreaterThanOrderingAccountBalance_ShouldThrowAccountDoesNotHaveEnoughBalance() {
		usecase.debitCredit(ORDERING_IBAN, BENEFICIARY_ACCOUNT, 10000.0, "JOD");
	}

	@Test(expected = InvalidAmountException.class)
	public void givenDebitCreditUseCase_CallingDebitCredit_PassingExistingAccounts_WithInvalidAmount() {
		usecase.debitCredit(ORDERING_IBAN, BENEFICIARY_ACCOUNT, -10.0, "USD");
	}

	@Test(expected = AccountNotFoundException.class)
	public void givenDebitCreditUseCase_CallingDebitCredit_PassingNoneExistingOrderingAccount_ShouldThrowAccountNotFound() {
		usecase.debitCredit("NotExistingIBAN", BENEFICIARY_ACCOUNT, 0, "JOD");
	}

	@Test(expected = AccountNotFoundException.class)
	public void givenDebitCreditUseCase_CallingDebitCredit_PassingNoneExistingBeneficiaryAccount_ShouldThrowAccountNotFound() {
		usecase.debitCredit(ORDERING_IBAN, "NotExistingIBAN", 0, "JOD");
	}

	private DataSource dataSource() {
		BasicDataSource dataSource = new BasicDataSource();
		dataSource.setUsername(DataBaseSettings.getInstance().username());
		dataSource.setPassword(DataBaseSettings.getInstance().password());
		dataSource.setUrl(DataBaseSettings.getInstance().url());
		dataSource.setDriverClassName(DataBaseSettings.getInstance().driverClassName());
		return dataSource;
	}

}
