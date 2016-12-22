package com.progressoft.jip.context;

import javax.sql.DataSource;

import com.progressoft.jip.factory.AccountGatewayDBBehaviorsFactory;
import com.progressoft.jip.factory.CurrencyGatewayDBBehaviorsFactory;
import com.progressoft.jip.factory.PaymentPurposeBehaviorsFactory;
import com.progressoft.jip.factory.PaymentRequestBehaviorsFactory;
import com.progressoft.jip.factory.impl.AccountGatewayDBBehaviorsFactoryImpl;
import com.progressoft.jip.factory.impl.CurrencyGatewayDBBehaviorsFactoryImpl;
import com.progressoft.jip.factory.impl.PaymentPurposeBehaviorsFactoryImpl;
import com.progressoft.jip.factory.impl.PaymentRequestBehaviorsFactoryImpl;
import com.progressoft.jip.gateway.impl.MySqlAccountGateway;
import com.progressoft.jip.gateway.impl.MySqlCurrencyGateway;
import com.progressoft.jip.gateway.impl.MySqlPaymentPurposeGateway;
import com.progressoft.jip.gateway.impl.MySqlPaymentRequestGateway;
import com.progressoft.jip.repository.AccountRepository;
import com.progressoft.jip.repository.CurrencyRepository;
import com.progressoft.jip.repository.PaymentPurposeRepository;
import com.progressoft.jip.repository.PaymentRequestRepository;
import com.progressoft.jip.repository.impl.AccountRepositoryImpl;
import com.progressoft.jip.repository.impl.CurrencyRepositoryImpl;
import com.progressoft.jip.repository.impl.PaymentPurposeRepositoryImpl;
import com.progressoft.jip.repository.impl.PaymentRequestRepositoryImpl;

public class AppContext {

	private static PaymentRequestBehaviorsFactory paymentRequestBehaviorsFactory = new PaymentRequestBehaviorsFactoryImpl();
	private static PaymentPurposeBehaviorsFactory paymentPurposeBehaviorsFactory = new PaymentPurposeBehaviorsFactoryImpl();
	private static CurrencyGatewayDBBehaviorsFactory currencyBehaviorsFactory = new CurrencyGatewayDBBehaviorsFactoryImpl();
	private static AccountGatewayDBBehaviorsFactory accountBehaviorsFactory = new AccountGatewayDBBehaviorsFactoryImpl();

	private final PaymentRequestRepository paymentRequestRepository;
	private final PaymentPurposeRepository paymentPurposeRepository;
	private final CurrencyRepository currencyRepository;
	private final AccountRepository accountRepository;

	public AppContext(DataSource dataSource) {
		paymentRequestRepository = new PaymentRequestRepositoryImpl(
				new MySqlPaymentRequestGateway(dataSource, paymentRequestBehaviorsFactory));
		paymentPurposeRepository = new PaymentPurposeRepositoryImpl(
				new MySqlPaymentPurposeGateway(dataSource, paymentPurposeBehaviorsFactory));
		currencyRepository = new CurrencyRepositoryImpl(new MySqlCurrencyGateway(dataSource, currencyBehaviorsFactory));
		accountRepository = new AccountRepositoryImpl(new MySqlAccountGateway(dataSource, accountBehaviorsFactory));
	}

	public PaymentRequestRepository paymentRequestRepositroy() {
		return paymentRequestRepository;
	}

	public PaymentPurposeRepository paymentPurposeRepository() {
		return paymentPurposeRepository;
	}

	public CurrencyRepository currencyRepository() {
		return currencyRepository;
	}

	public AccountRepository accountRepository() {
		return accountRepository;
	}

}
