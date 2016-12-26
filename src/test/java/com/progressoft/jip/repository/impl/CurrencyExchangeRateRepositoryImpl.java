package com.progressoft.jip.repository.impl;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.progressoft.jip.gateway.exception.NullGatewayException;
import com.progressoft.jip.gateway.impl.YahooCurrencyExchangeRateGateway;
import com.progressoft.jip.model.CurrencyExchangeRate;
import com.progressoft.jip.repository.CurrenctExchangeRateRepository;
import com.progressoft.jip.utilities.restful.RestfulResponseFormat;
import com.progressoft.jip.utilities.restful.impl.YahooCurrenciesXmlResponseParser;

public class CurrencyExchangeRateRepositoryImpl {

	private CurrenctExchangeRateRepository repository;

	@Before
	public void createCurrenctExchangeRateRepository() {
		repository = new CurrenctExchangeRateRepositoryImpl(new YahooCurrencyExchangeRateGateway(
				RestfulResponseFormat.XML, new YahooCurrenciesXmlResponseParser()));
	}

	@Test(expected = NullGatewayException.class)
	public void createCurrenctExchangeRateRepository_PassingNullGateway_ShouldThrowNullGateway() {
		new CurrenctExchangeRateRepositoryImpl(null);
	}

	@Test
	public void givenCurrencyExchangeRateRepository_CallingloadCurrencyExchangeRate_PassingValidCurrencyCodes() {
		CurrencyExchangeRate currencyExchangeRate = repository.loadCurrencyExchangeRate("JOD", "USD");
		assertNotNull(currencyExchangeRate);
	}

}
