package com.progressoft.jip.gateway.impl;

import static com.progressoft.jip.gateway.impl.MySQLCurrencyDBMock.UPDATED_CRNCY_CODE;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.apache.commons.dbcp2.BasicDataSource;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.progressoft.jip.datastructures.CurrencyDataStructure;
import com.progressoft.jip.gateway.CurrencyGateway;
import com.progressoft.jip.gateway.impl.MySqlCurrencyGateway.CurrencyNotFoundExption;
import com.progressoft.jip.gateway.impl.MySqlCurrencyGateway.ShortCurrencyCodeException;



public class MySQLCurrencyGatewayTest {

	private static final String CURRANCY_CODE = "SAR";
	private static final String INVALID_CURRENCY_CODE = "SOS";
	private static final String SHORT_CURRENCY_CODE = "J";
	private static final String DATABASE_USER = "root";
	private static final String DATABASE_PASSWORD = "root";
	private static final String DATABASE_URL = "jdbc:mysql://localhost/payment_system";
	private static final String DATABASE_DRIVER_CLASS_NAME = "com.mysql.cj.jdbc.Driver";

	private CurrencyGateway currencyGatewaty;
	private BasicDataSource dataSource;

	@Rule
	public final ExpectedException expectedException = ExpectedException.none();

	@Before
	public void setUp() {
		dataSource = new BasicDataSource();
		BasicDataSource configuredConnection = configureConnection(dataSource);
		currencyGatewaty = new MySqlCurrencyGateway(configuredConnection);
		MySQLCurrencyDBMock.setUpDBEnviroment(configuredConnection);

	}

	@After
	public void tearDown() {
		BasicDataSource configuredConnection = configureConnection(dataSource);
		MySQLCurrencyDBMock.tearDownDBEnviroment(configuredConnection);
	}

	@Test
	public void givenCurrencyGateway_WhenLoadCurrencies_ThenResultSetHasElements() {
		Iterable<CurrencyDataStructure> currencies = currencyGatewaty.loadCurrencies();
		assertTrue(currencies.iterator().hasNext());
	}

	@Test
	public void givenCurrencyGateway_WhenGetValidCurrency_ThenCurrencyNotNull() {
		CurrencyDataStructure currency = currencyGatewaty.loadCurrencyByCode(UPDATED_CRNCY_CODE);
		assertNotNull(currency);
	}

	@Test
	public void givenCurrencyGateway_WhenGetShortCurrencyCode_ThenShortCurrencyCodeExceptionIsThrown() {
		expectedException.expect(ShortCurrencyCodeException.class);
		currencyGatewaty.loadCurrencyByCode(SHORT_CURRENCY_CODE);
	}

	@Test
	public void givenCurrencyGateway_WhenGetInvalidCurrency_ThenEmptyResultSetExceptionIsThrown() {
		expectedException.expect(CurrencyNotFoundExption.class);
		currencyGatewaty.loadCurrencyByCode(INVALID_CURRENCY_CODE);
	}

	private BasicDataSource configureConnection(BasicDataSource dataSource) {
		dataSource.setUrl(DATABASE_URL);
		dataSource.setDriverClassName(DATABASE_DRIVER_CLASS_NAME);
		dataSource.setUsername(DATABASE_USER);
		dataSource.setPassword(DATABASE_PASSWORD);
		return dataSource;

	}

	private CurrencyDataStructure buildNewCurrency() {
		CurrencyDataStructure newCurrency = new CurrencyDataStructure();
		newCurrency.setCurrencyCode(CURRANCY_CODE);
		newCurrency.setCurrencyRate(0.54);
		newCurrency.setCurrencyDescription("Saudi Arabian Ryal");
		return newCurrency;
	}

}
