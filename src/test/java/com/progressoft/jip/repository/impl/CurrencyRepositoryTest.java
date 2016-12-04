package com.progressoft.jip.repository.impl;

import static com.progressoft.jip.gateway.impl.MySQLCurrencyDBMock.UPDATED_CRNCY_CODE;
import static com.progressoft.jip.gateway.impl.MySQLCurrencyDBMock.setUpDBEnviroment;
import static com.progressoft.jip.gateway.impl.MySQLCurrencyDBMock.tearDownDBEnviroment;
import static org.junit.Assert.assertTrue;

import org.apache.commons.dbcp2.BasicDataSource;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.progressoft.jip.gateway.impl.MySqlCurrencyGateway;
import com.progressoft.jip.model.Currency;
import com.progressoft.jip.repository.CurrencyRepository;

public class CurrencyRepositoryTest {
	
	private static final String DATABASE_USER = "root";
	private static final String DATABASE_PASSWORD = "root";
	private static final String DATABASE_URL = "jdbc:mysql://localhost/payment_system?autoReconnect=true&useSSL=false";
	private static final String DATABASE_DRIVER_CLASS_NAME = "com.mysql.cj.jdbc.Driver";

	private CurrencyRepository currencyRepository;
	@Rule
	public ExpectedException expectedException = ExpectedException.none();
	private BasicDataSource configureDataDource;

	@Before
	public void setUp() {
		configureDataDource = configureConnection();
		this.currencyRepository = new CurrencyRepositoryImpl(new MySqlCurrencyGateway(configureDataDource));
		setUpDBEnviroment(configureDataDource);
	}

	@After
	public void tearDown() {
		tearDownDBEnviroment(configureConnection());
	}

	@Test
	public void givenCurrencyRepository_WhenLoadCurrencies_ThenResultSetHasElements() {
		Iterable<Currency> currencies = currencyRepository.loadCurrencies();
		assertTrue(currencies.iterator().hasNext());
	}

	@Test
	public void givenCurrenctyRepository_loadCurrencyByCode_ThenValidCurrencyShouldBeReturned() {
		currencyRepository.loadCurrencyByCode(UPDATED_CRNCY_CODE);

	}

	private BasicDataSource configureConnection() {
		BasicDataSource dataSource = new BasicDataSource();
		dataSource.setUrl(DATABASE_URL);
		dataSource.setDriverClassName(DATABASE_DRIVER_CLASS_NAME);
		dataSource.setUsername(DATABASE_USER);
		dataSource.setPassword(DATABASE_PASSWORD);
		return dataSource;
	}

}
