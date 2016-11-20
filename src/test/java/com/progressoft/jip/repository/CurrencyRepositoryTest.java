package com.progressoft.jip.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.apache.commons.dbcp.BasicDataSource;
import org.junit.Before;
import org.junit.Test;

import com.progressoft.jip.gateway.impl.SQLCurrencyGateway;
import com.progressoft.jip.model.CurrencyModel;
import com.progressoft.jip.repository.impl.CurrencyRepositoryImpl;

public class CurrencyRepositoryTest {

	private  static final String  VALID_CURRENCY_CODE="JOD";
	private static final double NEW_CURRENCY_RATE=5.4;
	private static final String DATABASE_USER="root";
	private static final String DATABASE_PASSWORD="root";
	private static final String DATABASE_URL="jdbc:mysql://localhost/payment_system";
	private static final String DATABASE_DRIVER_CLASS_NAME="com.mysql.cj.jdbc.Driver";
	
	private CurrencyRepositoryImpl currencyRepository;
	private BasicDataSource dataSource;
	
	@Before
	public void setUp() {
		configureConnection();
		currencyRepository = new CurrencyRepositoryImpl(new SQLCurrencyGateway(dataSource));
	}

	@Test
	public void givenCurrencyRepository_WhenloadCurrencies_ThenCurrenciesLoaded() {
		Iterable<CurrencyModel> currencies = currencyRepository.loadCurrencies();
		assertTrue(currencies.iterator().hasNext());
	}

	@Test
	public void givenCurrencycODE_WhehnloadCurrencyByCode_ThenCurrencyLoaded() {
		CurrencyModel currency = currencyRepository.loadCurrencyByCode("JOD");
		assertNotNull(currency);
	}

	@Test
	public void givenCuurencyRepository_whenUpdateCurrencyRateByCode_rateUpdated() {
		CurrencyModel originalUpdateCurrency = currencyRepository.loadCurrencyByCode(VALID_CURRENCY_CODE);
		currencyRepository.updatedCurrencyRateByCode(VALID_CURRENCY_CODE, NEW_CURRENCY_RATE);
		CurrencyModel updatedCurrency = currencyRepository.loadCurrencyByCode(VALID_CURRENCY_CODE);
		assertEquals(updatedCurrency.getCurrencyRate(), NEW_CURRENCY_RATE, 0);
		currencyRepository.updatedCurrencyRateByCode(originalUpdateCurrency.getCurrencyCode(), originalUpdateCurrency.getCurrencyRate());
	}
	
	private void configureConnection() {
		dataSource = new BasicDataSource();
		dataSource.setUrl(DATABASE_URL);
		dataSource.setDriverClassName(DATABASE_DRIVER_CLASS_NAME);
		dataSource.setUsername(DATABASE_USER);
		dataSource.setPassword(DATABASE_PASSWORD);
	}
}
