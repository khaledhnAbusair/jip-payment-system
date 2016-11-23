package com.progressoft.jip.gateway.impl;

import java.util.Collection;

import javax.sql.DataSource;

import com.progressoft.jip.datastructures.PaymentRequestDataStructure;
import com.progressoft.jip.gateway.PaymentRequestGateway;
import com.progressoft.jip.utilities.Behavior;

public class MySqlPaymentRequestGatway extends AbstractGateway implements PaymentRequestGateway {

    private Behavior<PaymentRequestDataStructure> loadPaymentRequestById;
    private Behavior<Void> deletePaymentRequestById;
    private Behavior<Void> insertPaymentRequest;
    private Behavior<Collection<PaymentRequestDataStructure>> loadPaymentRequests;

    public MySqlPaymentRequestGatway(DataSource dataSource,
	    Behavior<PaymentRequestDataStructure> loadPaymentRequestById, Behavior<Void> deletePaymentRequestById,
	    Behavior<Void> insertPaymentRequest, Behavior<Collection<PaymentRequestDataStructure>> loadPaymentRequests) {
	super(dataSource);
	this.loadPaymentRequestById = loadPaymentRequestById;
	this.deletePaymentRequestById = deletePaymentRequestById;
	this.insertPaymentRequest = insertPaymentRequest;
	this.loadPaymentRequests = loadPaymentRequests;
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

}
