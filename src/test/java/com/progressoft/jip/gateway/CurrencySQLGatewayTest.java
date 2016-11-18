package com.progressoft.jip.gateway;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.dbcp.BasicDataSource;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runners.MethodSorters;

import com.progressoft.jip.datastructures.Currency;
import com.progressoft.jip.gateway.CurrencyGatewaySQL.DuplicateCurrencyCodeException;
import com.progressoft.jip.gateway.CurrencyGatewaySQL.EmptyResultSetException;
import com.progressoft.jip.gateway.CurrencyGatewaySQL.ShortCurrencyCodeException;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CurrencySQLGatewayTest {

	@Rule
	public final ExpectedException expectedException = ExpectedException.none();

	private static List<Currency> currencies = new ArrayList<>();
	private static final Currency newCurrency = new Currency();

	{

		newCurrency.setCurrencyCode("SAR");
		newCurrency.setCurrencyRate(0.54);
		newCurrency.setCurrencyDescription("Saudi Arabian Ryal");
	}

	private BasicDataSource dataSource = new BasicDataSource();
	private CurrencyGateway currencyGatewaty;

	@Before
	public void setUp() {

		dataSource.setUrl("jdbc:mysql://localhost/payment_system");
		dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
		dataSource.setUsername("root");
		dataSource.setPassword("root");
		currencyGatewaty = new CurrencyGatewaySQL(dataSource);
	}

	@Test
	public void _01_CurrencyGateway_loadCurrencies_ResultSetHaveElements() {
		Iterable<Currency> currencies = currencyGatewaty.loadCurrencies();
		currencies.forEach(c -> CurrencySQLGatewayTest.currencies.add(c));
		assertTrue(currencies.iterator().hasNext());

	}

	@Test
	public void _02_CurrencyGateway_GetValidCurrency_CurrencyNotNull() {
		Currency validCurrency = currencies.get(0);
		Currency currency = currencyGatewaty.getCurrencyByCode(validCurrency.getCurrencyCode());
		assertNotNull(currency);
	}

	@Test
	public void _03_CurrencyGateway_GetShortCurrencyCode_ThrowExeption() {
		expectedException.expect(ShortCurrencyCodeException.class);
		currencyGatewaty.getCurrencyByCode("J");

	}

	@Test
	public void CurrencyGateway_GetInvalidCurrency_ThrowException() {
		expectedException.expect(EmptyResultSetException.class);
		currencyGatewaty.getCurrencyByCode("SOS");
	}

	@Test
	public void CurrencyGateway_updateCurrencybyCode_CurrencyUpdated() {
		double newRate = 4.3;
		double maxDelta = 0;
		Currency validCurrency = currencies.get(0);
		currencyGatewaty.updateCurrencyRateByCode(validCurrency.getCurrencyCode(), newRate);
		Currency updatedCurrency = currencyGatewaty.getCurrencyByCode(validCurrency.getCurrencyCode());
		assertEquals(newRate, updatedCurrency.getCurrencyRate(), maxDelta);
	}

	@Test
	public void CurrencyGateway_CreateCurrency_CurrencyCreated() {
		currencyGatewaty.createCurrency(newCurrency);

		Currency created = currencyGatewaty.getCurrencyByCode(newCurrency.getCurrencyCode());
		assertNotNull(created);
	}

	@Test
	public void _04_CurrencyGateway_CreateDuplicateCurrency_ThrowException() {
		expectedException.expect(DuplicateCurrencyCodeException.class);
		currencyGatewaty.createCurrency(currencies.get(0));
	}

	@Test
	public void CurrencyGateway_deleteCurrencyTest_CurrencyDeleted() {
		currencyGatewaty.deleteCurrency(newCurrency);
		expectedException.expect(EmptyResultSetException.class);
		Currency deleted = currencyGatewaty.getCurrencyByCode(newCurrency.getCurrencyCode());
		assertNull(deleted);

	}
}
