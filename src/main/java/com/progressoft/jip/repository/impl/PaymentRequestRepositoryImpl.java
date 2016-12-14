package com.progressoft.jip.repository.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.progressoft.jip.datastructures.PaymentRequestDataStructure;
import com.progressoft.jip.datastructures.builder.PaymentRequestDataStructureBuilder;
import com.progressoft.jip.datastructures.builder.impl.PaymentRequestDataStructureBuilderImpl;
import com.progressoft.jip.gateway.PaymentRequestGateway;
import com.progressoft.jip.iban.exception.InvalidIBANException;
import com.progressoft.jip.iban.impl.IBANFormatValidator;
import com.progressoft.jip.iban.impl.IBANModValidator;
import com.progressoft.jip.model.PaymentRequest;
import com.progressoft.jip.repository.PaymentRequestRepository;

public class PaymentRequestRepositoryImpl implements PaymentRequestRepository {

    private PaymentRequestGateway gateway;

    public PaymentRequestRepositoryImpl(PaymentRequestGateway gateway) {
	this.gateway = gateway;
    }

    @Override
    public PaymentRequest loadPaymentRequestById(int id) {
	return new PaymentRequest(gateway.loadPaymentRequestById(id));
    }

    @Override
    public void inserPaymentRequest(PaymentRequest paymentRequest) {
	if (!paymentRequest.validateIBAN(new IBANFormatValidator(), new IBANModValidator()))
	    throw new InvalidIBANException("Invalid Beneficiary IBAN");
	PaymentRequestDataStructureBuilder builder = new PaymentRequestDataStructureBuilderImpl();
	paymentRequest.build(builder);
	gateway.insertPaymentRequest(builder.build());
    }

    @Override
    public Collection<PaymentRequest> loadPaymentRequests() {
	return datastructureListToModelList((List<PaymentRequestDataStructure>) gateway.loadPaymentRequests());
    }

    @Override
    public void deletePaymentRequestById(int id) {
	gateway.deletePaymentRequestById(id);
    }

    @Override
    public Collection<PaymentRequest> loadPaymentRequestsByOrderingAccountIBAN(String IBAN) {
	return datastructureListToModelList((List<PaymentRequestDataStructure>) gateway
		.loadPaymentRequestsByOrderingAccountIBAN(IBAN));
    }

    private Collection<PaymentRequest> datastructureListToModelList(List<PaymentRequestDataStructure> datastructureList) {
	List<PaymentRequest> modelList = new ArrayList<>();
	for (PaymentRequestDataStructure ds : datastructureList)
	    modelList.add(new PaymentRequest(ds));
	return new ArrayList<PaymentRequest>(modelList);
    }

}
