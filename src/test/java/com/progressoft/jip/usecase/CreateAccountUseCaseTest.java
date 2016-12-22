package com.progressoft.jip.usecase;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;
import org.junit.Before;
import org.junit.Test;

import com.progressoft.jip.context.AppContext;
import com.progressoft.jip.repository.AccountRepository;
import com.progressoft.jip.usecase.exception.NullAccountsRepositoryException;
import com.progressoft.jip.utilities.DataBaseSettings;

public class CreateAccountUseCaseTest {

	private AccountRepository repository;

	@Before
	public void setup() {
		repository = new AppContext(dataSource()).accountRepository();
		new CreateAccountUseCase(repository);
	}
	
	@Test(expected = NullAccountsRepositoryException.class)
	public void createUseCase_PassingNullRepository_ShouldThrowNullRepository() {
		new CreateAccountUseCase(null);
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
