package com.progressoft.jip.context;

import com.progressoft.jip.repository.AccountRepository;
import com.progressoft.jip.repository.CurrencyExchangeRateRepository;
import com.progressoft.jip.repository.CurrencyRepository;
import com.progressoft.jip.repository.PaymentPurposeRepository;
import com.progressoft.jip.repository.PaymentRequestRepository;

public interface AppContext {

	PaymentRequestRepository paymentRequestRepositroy();

	PaymentPurposeRepository paymentPurposeRepository();

	CurrencyRepository currencyRepository();

	AccountRepository accountRepository();

	CurrencyExchangeRateRepository currentExchangeRateRepository();

}