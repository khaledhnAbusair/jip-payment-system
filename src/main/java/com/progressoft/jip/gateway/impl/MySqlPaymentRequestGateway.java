package com.progressoft.jip.gateway.impl;

import java.util.Collection;

import javax.sql.DataSource;

import com.progressoft.jip.datastructures.PaymentRequestDataStructure;
import com.progressoft.jip.factory.Behavior;
import com.progressoft.jip.factory.PaymentRequestBehaviorsFactory;
import com.progressoft.jip.gateway.AbstractGateway;
import com.progressoft.jip.gateway.PaymentRequestGateway;

public class MySqlPaymentRequestGateway extends AbstractGateway implements PaymentRequestGateway {

	private Behavior<PaymentRequestDataStructure> loadPaymentRequestById;
	private Behavior<Void> deletePaymentRequestById;
	private Behavior<Void> insertPaymentRequest;
	private Behavior<Collection<PaymentRequestDataStructure>> loadPaymentRequests;
	private Behavior<Collection<PaymentRequestDataStructure>> loadPaymentRequestsByOrderingAccIBAN;

	public MySqlPaymentRequestGateway(DataSource dataSource, PaymentRequestBehaviorsFactory factory) {
		super(dataSource);
		this.loadPaymentRequestById = factory.loadPaymentRequestById();
		this.deletePaymentRequestById = factory.deletePaymentRequestById();
		this.insertPaymentRequest = factory.insertPaymentRequest();
		this.loadPaymentRequests = factory.loadPaymentRequests();
		this.loadPaymentRequestsByOrderingAccIBAN = factory.loadPaymentRequestsByOrderingAccIBAN();
	}

	@Override
	public void deletePaymentRequestById(int id) {
		deletePaymentRequestById.execute(dataSource, id);
	}

	@Override
	public PaymentRequestDataStructure loadPaymentRequestById(int id) {
		return loadPaymentRequestById.execute(dataSource, id);
	}

	@Override
	public void insertPaymentRequest(PaymentRequestDataStructure paymentRequestDataStructure) {
		insertPaymentRequest.execute(dataSource, paymentRequestDataStructure);
	}

	@Override
	public Collection<PaymentRequestDataStructure> loadPaymentRequests() {
		return loadPaymentRequests.execute(dataSource);
	}

	@Override
	public Collection<PaymentRequestDataStructure> loadPaymentRequestsByOrderingAccountIBAN(String iban) {
		return loadPaymentRequestsByOrderingAccIBAN.execute(dataSource, iban);
	}

}
