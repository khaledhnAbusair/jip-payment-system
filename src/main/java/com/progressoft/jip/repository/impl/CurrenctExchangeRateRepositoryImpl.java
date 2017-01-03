package com.progressoft.jip.repository.impl;

import java.util.Objects;

import com.progressoft.jip.gateway.CurrencyExchangeRateGateway;
import com.progressoft.jip.gateway.exception.NullGatewayException;
import com.progressoft.jip.model.CurrencyExchangeRate;
import com.progressoft.jip.repository.CurrencyExchangeRateRepository;

public class CurrenctExchangeRateRepositoryImpl implements CurrencyExchangeRateRepository {

	private CurrencyExchangeRateGateway gateway;

	public CurrenctExchangeRateRepositoryImpl(CurrencyExchangeRateGateway gateway) {
		if (Objects.isNull(gateway))
			throw new NullGatewayException();
		this.gateway = gateway;
	}

	@Override
	public CurrencyExchangeRate loadCurrencyExchangeRate(String codeFrom, String codeTo) {
		return new CurrencyExchangeRate(gateway.loadCurrencyExchangeRate(codeFrom, codeTo));
	}

}
