package com.progressoft.jip.repository;

import com.progressoft.jip.model.CurrencyExchangeRate;

public interface CurrencyExchangeRateRepository {

	CurrencyExchangeRate loadCurrencyExchangeRate(String codeFrom, String codeTo);

}