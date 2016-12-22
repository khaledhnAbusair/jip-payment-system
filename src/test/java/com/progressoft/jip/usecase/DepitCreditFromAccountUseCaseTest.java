package com.progressoft.jip.usecase;

import static org.junit.Assert.*;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;
import org.junit.Before;
import org.junit.Test;

import com.progressoft.jip.context.AppContext;
import com.progressoft.jip.datastructures.AccountDatastructure;
import com.progressoft.jip.gateway.exception.AccountNotFoundException;
import com.progressoft.jip.model.Account;
import com.progressoft.jip.repository.AccountRepository;
import com.progressoft.jip.usecase.exception.AccountDoesNotHaveEnoughBalanceException;
import com.progressoft.jip.usecase.exception.InvalidAmountException;
import com.progressoft.jip.usecase.exception.NullAccountsRepositoryException;
import com.progressoft.jip.utilities.DataBaseSettings;
import com.progressoft.jip.view.AccountView;

public class DepitCreditFromAccountUseCaseTest {

	private static final String BENEFICIARY_ACCOUNT = "RO49AAAA1B31007593840000";
	private static final String ORDERING_IBAN = "JO94CBJO0010000000000131000302";
	private AccountRepository repository;
	private DepitCreditFromAccountUseCase usecase;

	@Before
	public void setup() {
		repository = new AppContext(dataSource()).accountRepository();
		usecase = new DepitCreditFromAccountUseCase(repository);
	}

	@Test(expected = NullAccountsRepositoryException.class)
	public void createDepitFromAccountUseCase_PassingNullRepository_ShouldThrowNullAccountsRepository() {
		new DepitCreditFromAccountUseCase(null);
	}

	@Test(expected = AccountDoesNotHaveEnoughBalanceException.class)
	public void givenDepitCreditUseCase_CallingDepitCredit_PassingExistingAccounts_WithAmountGreaterThanOrderingAccountBalance_ShouldThrowAccountDoesNotHaveEnoughBalance() {
		usecase.depitCredit(ORDERING_IBAN, BENEFICIARY_ACCOUNT, 10000.0);
	}

	@Test(expected = InvalidAmountException.class)
	public void givenDepitCreditUseCase_CallingDepitCredit_PassingExistingAccounts_WithInvalidAmount() {
		usecase.depitCredit(ORDERING_IBAN, BENEFICIARY_ACCOUNT, -10.0);
	}

	@Test(expected = AccountNotFoundException.class)
	public void givenDepitCreditUseCase_CallingDepitCredit_PassingNoneExistingOrderingAccount_ShouldThrowAccountNotFound() {
		usecase.depitCredit("NotExistingIBAN", BENEFICIARY_ACCOUNT, 0);
	}

	@Test(expected = AccountNotFoundException.class)
	public void givenDepitCreditUseCase_CallingDepitCredit_PassingNoneExistingBeneficiaryAccount_ShouldThrowAccountNotFound() {
		usecase.depitCredit(ORDERING_IBAN, "NotExistingIBAN", 0);
	}

	@Test
	public void givenDepitCreditUseCase_CallingDepitCredit_PassingExistingAccountsWithValidAmount_TransactionsShouldSuccess() {
		AccountDatastructure originalAccount = new AccountDatastructure();
		originalAccount.setIban(ORDERING_IBAN);
		originalAccount.setAccountType("TYPE");
		originalAccount.setBalance(500);
		originalAccount.setStatus("ACTIVE");
		Account ordAcc = new Account(originalAccount);
		repository.updateAccount(ordAcc);
		AccountDatastructure beneficiaryAccount = new AccountDatastructure();
		beneficiaryAccount.setIban(BENEFICIARY_ACCOUNT);
		beneficiaryAccount.setAccountType("TYPE");
		beneficiaryAccount.setBalance(0);
		beneficiaryAccount.setStatus("ACTIVE");
		Account benAcc = new Account(beneficiaryAccount);
		repository.updateAccount(benAcc);
		usecase.depitCredit(ORDERING_IBAN, BENEFICIARY_ACCOUNT, 250);
		ordAcc = repository.loadAccountByIBAN(ORDERING_IBAN);
		benAcc = repository.loadAccountByIBAN(BENEFICIARY_ACCOUNT);
		AccountView view = new AccountView();
		ordAcc.buildAccountView(view);
		view = view.build();
		assertTrue(Math.abs(view.getBalance() - 250.0) <= 1e-3);
		benAcc.buildAccountView(view);
		view = view.build();
		assertTrue(Math.abs(view.getBalance() - 250.0) <= 1e-3);
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
