package com.progressoft.jip.repository.impl;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.progressoft.jip.datastructures.PaymentRequestDataStructure;
import com.progressoft.jip.datastructures.builder.PaymentRequestDataStructureBuilder;
import com.progressoft.jip.gateway.PaymentRequestGateway;
import com.progressoft.jip.model.PaymentRequest;
import com.progressoft.jip.repository.PaymentRequestRepository;
//talks to gateway and creates model from the datastructure
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
	PaymentRequestDataStructureBuilder builder = new PaymentRequestDataStructureBuilder() {
	    private int id;
	    private String orderingAccountIBAN;
	    private String beneficiaryAccountIBAN;
	    private String beneficiaryName;
	    private double paymentAmount;
	    private String currencyCode;
	    private String purposeCdoe;
	    private Date paymentDate;

	    @Override
	    public PaymentRequestDataStructureBuilder setPurposeCdoe(String purposeCdoe) {
		this.purposeCdoe = purposeCdoe;
		return this;
	    }

	    @Override
	    public PaymentRequestDataStructureBuilder setPaymentDate(Date paymentDate) {
		this.paymentDate = paymentDate;
		return this;
	    }

	    @Override
	    public PaymentRequestDataStructureBuilder setPaymentAmount(double paymentAmount) {
		this.paymentAmount = paymentAmount;
		return this;
	    }

	    @Override
	    public PaymentRequestDataStructureBuilder setOrderingAccountIBAN(String orderingAccountIBAN) {
		this.orderingAccountIBAN = orderingAccountIBAN;
		return this;
	    }

	    @Override
	    public PaymentRequestDataStructureBuilder setId(int id) {
		this.id = id;
		return this;
	    }

	    @Override
	    public PaymentRequestDataStructureBuilder setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
		return this;
	    }

	    @Override
	    public PaymentRequestDataStructureBuilder setBeneficiaryName(String beneficiaryName) {
		this.beneficiaryName = beneficiaryName;
		return this;
	    }

	    @Override
	    public PaymentRequestDataStructureBuilder setBeneficiaryAccountIBAN(String beneficiaryAccountIBAN) {
		this.beneficiaryAccountIBAN = beneficiaryAccountIBAN;
		return this;
	    }

	    @Override
	    public PaymentRequestDataStructure build() {
		PaymentRequestDataStructure req = new PaymentRequestDataStructure();
		req.setId(id);
		req.setOrderingAccountIBAN(orderingAccountIBAN);
		req.setBeneficiaryAccountIBAN(beneficiaryAccountIBAN);
		req.setBeneficiaryName(beneficiaryName);
		req.setPaymentAmount(paymentAmount);
		req.setCurrencyCode(currencyCode);
		req.setPurposeCode(purposeCdoe);
		req.setPaymentDate(paymentDate);
		return req;
	    }
	};
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
