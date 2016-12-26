package com.progressoft.jip.gateway.impl;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import com.progressoft.jip.datastructures.CurrencyExchangeRateDataStructure;
import com.progressoft.jip.gateway.CurrencyExchangeRateGateway;
import com.progressoft.jip.gateway.exception.CurrencyCodeNotFoundException;
import com.progressoft.jip.gateway.exception.InvalidRestfulResponseFormatException;
import com.progressoft.jip.gateway.exception.NullRestfulParserException;
import com.progressoft.jip.utilities.restful.RestfulResponseFormat;
import com.progressoft.jip.utilities.restful.impl.YahooCurrenciesXmlResponseParser;

public class YahooCurrencyExchangeRateGatewayTest {

	private static final String DOLLAR_CURRENCY = "USD";
	private static final String JORDAN_CURRENCY_CODE = "JOD";
	private CurrencyExchangeRateGateway gateway;

	@Before
	public void createYahooCurrencyExchangeRateGateway() {
		gateway = new YahooCurrencyExchangeRateGateway(RestfulResponseFormat.XML,
				new YahooCurrenciesXmlResponseParser());
	}

	@Test(expected = NullRestfulParserException.class)
	public void createYahooCurrencyExchangeRateGateway_PassingNullParser_ShouldThrowNullRestfulParser() {
		new YahooCurrencyExchangeRateGateway(RestfulResponseFormat.XML, null);
	}
	
	@Test(expected = InvalidRestfulResponseFormatException.class)
	public void createYahooCurrencyExchangeRateGateway_PassingNullExtension_ShouldThrowNullRestfulResponseFormar() {
		new YahooCurrencyExchangeRateGateway(null, null);
	}

	@Test
	public void givenYahooCurrencyExchangeRateGateway_CallingLoadCurrencyExchangeRate_PassingValidCurrenciesCode_ShouldReturnTheCurrencyExchangeRate() {
		CurrencyExchangeRateDataStructure dataStructure = gateway.loadCurrencyExchangeRate(JORDAN_CURRENCY_CODE,
				DOLLAR_CURRENCY);
		assertEquals(JORDAN_CURRENCY_CODE, dataStructure.getCodeFrom());
		assertEquals(DOLLAR_CURRENCY, dataStructure.getCodeTo());
	}

	@Test(expected = CurrencyCodeNotFoundException.class)
	public void givenYahooCurrencyExchangeRateGateway_CallingLoadCurrencyExchangeRate_PassingInvalidValidCurrenciesCode_ShouldThrowCurrencyCodeNotFound() {
		gateway.loadCurrencyExchangeRate("QQQ", "JOD");
	}

	@Test(expected = CurrencyCodeNotFoundException.class)
	public void givenYahooCurrencyExchangeRateGateway_CallingLoadCurrencyExchangeRate_PassingInvalidValidCurrenciesCode_2_ShouldThrowCurrencyCodeNotFound() {
		gateway.loadCurrencyExchangeRate("", "JOD");
	}

	@Test(expected = CurrencyCodeNotFoundException.class)
	public void givenYahooCurrencyExchangeRateGateway_CallingLoadCurrencyExchangeRate_PassingInvalidValidCurrenciesCode_3_ShouldThrowCurrencyCodeNotFound() {
		gateway.loadCurrencyExchangeRate("JOD", "");
	}

	@Test(expected = CurrencyCodeNotFoundException.class)
	public void givenYahooCurrencyExchangeRateGateway_CallingLoadCurrencyExchangeRate_PassingInvalidValidCurrenciesCode_4_ShouldThrowCurrencyCodeNotFound() {
		gateway.loadCurrencyExchangeRate("JO", "PL");
	}

	@Test(expected = CurrencyCodeNotFoundException.class)
	public void givenYahooCurrencyExchangeRateGateway_CallingLoadCurrencyExchangeRate_PassingInvalidValidCurrenciesCode_5_ShouldThrowCurrencyCodeNotFound() {
		gateway.loadCurrencyExchangeRate(null, "JOD");
	}

	@Test(expected = CurrencyCodeNotFoundException.class)
	public void givenYahooCurrencyExchangeRateGateway_CallingLoadCurrencyExchangeRate_PassingInvalidValidCurrenciesCode_6_ShouldThrowCurrencyCodeNotFound() {
		gateway.loadCurrencyExchangeRate("JOD", null);
	}

}
