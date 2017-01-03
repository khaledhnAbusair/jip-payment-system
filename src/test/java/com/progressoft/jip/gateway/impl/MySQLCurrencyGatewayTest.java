package com.progressoft.jip.gateway.impl;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.apache.commons.dbcp2.BasicDataSource;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.progressoft.jip.datastructures.CurrencyDataStructure;
import com.progressoft.jip.factory.impl.CurrencyGatewayDBBehaviorsFactoryImpl;
import com.progressoft.jip.gateway.CurrencyGateway;
import com.progressoft.jip.gateway.exception.CurrencyNotFoundExption;
import com.progressoft.jip.gateway.exception.ShortCurrencyCodeException;
import com.progressoft.jip.utilities.DataBaseSettings;

public class MySQLCurrencyGatewayTest {

	private static final String INVALID_CURRENCY_CODE = "SOS";
	private static final String SHORT_CURRENCY_CODE = "J";

	private CurrencyGateway currencyGateway;
	private BasicDataSource dataSource;

	@Rule
	public final ExpectedException expectedException = ExpectedException.none();

	@Before
	public void setUp() {
		dataSource = new BasicDataSource();
		BasicDataSource configuredConnection = configureConnection(dataSource);
		currencyGateway = new MySqlCurrencyGateway(configuredConnection, new CurrencyGatewayDBBehaviorsFactoryImpl());
		MySQLCurrencyDBMock.setUpDBEnviroment(configuredConnection);

	}

	@After
	public void tearDown() {
		BasicDataSource configuredConnection = configureConnection(dataSource);
		MySQLCurrencyDBMock.tearDownDBEnviroment(configuredConnection);
	}

	@Test
	public void givenCurrencyGateway_WhenLoadCurrencies_ThenResultSetHasElements() {
		Iterable<CurrencyDataStructure> currencies = currencyGateway.loadCurrencies();
		assertTrue(currencies.iterator().hasNext());
	}

	@Test
	public void givenCurrencyGateway_WhenGetValidCurrency_ThenCurrencyNotNull() {
		assertNotNull(currencyGateway.loadCurrencyByCode("DEL"));
	}

	@Test
	public void givenCurrencyGateway_WhenGetShortCurrencyCode_ThenShortCurrencyCodeExceptionIsThrown() {
		expectedException.expect(ShortCurrencyCodeException.class);
		currencyGateway.loadCurrencyByCode(SHORT_CURRENCY_CODE);
	}

	@Test
	public void givenCurrencyGateway_WhenGetInvalidCurrency_ThenEmptyResultSetExceptionIsThrown() {
		expectedException.expect(CurrencyNotFoundExption.class);
		currencyGateway.loadCurrencyByCode(INVALID_CURRENCY_CODE);
	}

	private BasicDataSource configureConnection(BasicDataSource dataSource) {
		DataBaseSettings dbSettings = DataBaseSettings.getInstance();
		dataSource.setUrl(dbSettings.url());
		dataSource.setDriverClassName(dbSettings.driverClassName());
		dataSource.setUsername(dbSettings.username());
		dataSource.setPassword(dbSettings.password());
		return dataSource;

	}

}
