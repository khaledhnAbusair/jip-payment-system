package com.progressoft.jip.gateway.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.text.DecimalFormat;
import java.util.Random;

import org.apache.commons.dbcp.BasicDataSource;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.progressoft.jip.datastructures.CurrencyDataStructure;
import com.progressoft.jip.gateway.CurrencyGateway;
import com.progressoft.jip.gateway.impl.SQLCurrencyGateway.EmptyResultSetException;
import com.progressoft.jip.gateway.impl.SQLCurrencyGateway.ShortCurrencyCodeException;
import com.progressoft.jip.utilities.QueryHandler.DuplicateCurrencyCodeException;

public class CurrencySQLGatewayTest {

	private static final String INVALID_CURRENCY_CODE = "SOS";
	private static final String VALID_CURRENCY_CODE = "JOD";
	private static final String SHORT_CURRENCY_CODE = "J";
	private static final double RATE_FOR_UPDATE = Double.valueOf(new DecimalFormat("##.####").format( new Random().nextDouble() * .01));
	private static final double MAX_DELTA = 0;
	private static final String DATABASE_USER = "root";
	private static final String DATABASE_PASSWORD = "root";
	private static final String DATABASE_URL = "jdbc:mysql://localhost/payment_system";
	private static final String DATABASE_DRIVER_CLASS_NAME = "com.mysql.cj.jdbc.Driver";

	private BasicDataSource dataSource = new BasicDataSource();
	private CurrencyGateway currencyGatewaty;

	@Rule
	public final ExpectedException expectedException = ExpectedException.none();

	@Before
	public void setUp() {
		
		configureConnection();
		currencyGatewaty = new SQLCurrencyGateway(dataSource);
	}

	@Test
	public void givenCurrencyGateway_WhenLoadCurrencies_ThenResultSetHasElements() {
		Iterable<CurrencyDataStructure> currencies = currencyGatewaty.loadCurrencies();
		assertTrue(currencies.iterator().hasNext());
	}

	@Test
	public void givenCurrencyGateway_WhenGetValidCurrency_ThenCurrencyNotNull() {
		CurrencyDataStructure currency = currencyGatewaty.getCurrencyByCode(VALID_CURRENCY_CODE);
		assertNotNull(currency);
	}

	@Test
	public void givenCurrencyGateway_WhenGetShortCurrencyCode_ThenShortCurrencyCodeExceptionIsThrow() {
		expectedException.expect(ShortCurrencyCodeException.class);
		currencyGatewaty.getCurrencyByCode(SHORT_CURRENCY_CODE);
	}

	@Test
	public void givenCurrencyGateway_WhenGetInvalidCurrency_ThenEmptyResultSetExceptionIsThrown() {
		expectedException.expect(EmptyResultSetException.class);
		currencyGatewaty.getCurrencyByCode(INVALID_CURRENCY_CODE);
	}
	
	@Test
	public void givenCurrencyGateway_WhenUpdateCurrencyByCode_ThenCurrencyShoudBeUpdated(){
		isSingleRowAffected(currencyGatewaty.updateCurrencyRateByCode(VALID_CURRENCY_CODE, RATE_FOR_UPDATE));
	}

	private void isSingleRowAffected(int rowsAffected) {
		assertEquals(1, rowsAffected);
	}

	@Test
	public void givenCurrencyGateway_WhenUpdateCurrencybyCode_AndGetCurrencyByCode__ThenCurrencyIsUpdated() {
		CurrencyDataStructure originalCurrency = currencyGatewaty.getCurrencyByCode(VALID_CURRENCY_CODE);
		currencyGatewaty.updateCurrencyRateByCode(VALID_CURRENCY_CODE, RATE_FOR_UPDATE);
		CurrencyDataStructure updatedCurrency = currencyGatewaty.getCurrencyByCode(VALID_CURRENCY_CODE);
		assertEquals(RATE_FOR_UPDATE, updatedCurrency.getCurrencyRate(), MAX_DELTA);
		returnCurrencyToOriginalRate(originalCurrency);
	}

	private void returnCurrencyToOriginalRate(CurrencyDataStructure originalCurrency) {
		currencyGatewaty.updateCurrencyRateByCode(originalCurrency.getCurrencyCode(),
				originalCurrency.getCurrencyRate());
	}

	@Test
	public void givenCurrencyGateway_WhenCreateNewCurrency_AndCreateDuplicateCurrency_ThenDuplicateCurrencyCodeExceptionIsThrown() {
		expectedException.expect(DuplicateCurrencyCodeException.class);
		currencyGatewaty.createCurrency(currencyGatewaty.getCurrencyByCode(VALID_CURRENCY_CODE));
	}

	@Test
	public void givenCurrencyGateway_WhenCreateNewCurrency_AndDeleteSameCurrency_ThenCurrencyShouldBeCreated_AndSameCurrencyShouldBeDeleted() {
		CurrencyDataStructure newCurrency = buildNewCurrency();
		isSingleRowAffected(currencyGatewaty.createCurrency(newCurrency));
		isSingleRowAffected(currencyGatewaty.deleteCurrency(newCurrency));

	}

	private void configureConnection() {
		dataSource.setUrl(DATABASE_URL);
		dataSource.setDriverClassName(DATABASE_DRIVER_CLASS_NAME);
		dataSource.setUsername(DATABASE_USER);
		dataSource.setPassword(DATABASE_PASSWORD);
	}

	private CurrencyDataStructure buildNewCurrency() {
		CurrencyDataStructure newCurrency = new CurrencyDataStructure();
		newCurrency.setCurrencyCode("SAR");
		newCurrency.setCurrencyRate(0.54);
		newCurrency.setCurrencyDescription("Saudi Arabian Ryal");

		return newCurrency;
	}

}
