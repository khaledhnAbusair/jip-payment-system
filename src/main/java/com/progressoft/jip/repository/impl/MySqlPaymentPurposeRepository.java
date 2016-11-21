package com.progressoft.jip.repository.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.progressoft.jip.datastructures.PaymentPurposeDataStructure;
import com.progressoft.jip.gateway.PaymentPurposeGateway;
import com.progressoft.jip.model.PaymentPurpose;
import com.progressoft.jip.repository.PaymentPurposeRepository;

public class MySqlPaymentPurposeRepository implements PaymentPurposeRepository {

    protected PaymentPurposeGateway paymentPurposeGateway;

    public MySqlPaymentPurposeRepository(PaymentPurposeGateway paymentPurposeGateway) {
	this.paymentPurposeGateway = paymentPurposeGateway;
    }

    @Override
    public PaymentPurpose loadPaymentPurposeByCode(String code) {
	return new PaymentPurpose(paymentPurposeGateway.loadPaymentPurposeByCode(code));
    }

    @Override
    public Collection<PaymentPurpose> loadPaymentPurposes() {
	List<PaymentPurpose> paymentPurposes = new ArrayList<>();
	for (PaymentPurposeDataStructure paymentPurpose : paymentPurposeGateway.loadPaymentPurposes())
	    paymentPurposes.add(new PaymentPurpose(paymentPurpose));
	return new ArrayList<PaymentPurpose>(paymentPurposes);
    }

    @Override
    public void updatePaymenPurposeName(String code, String name) {
	paymentPurposeGateway.updatePaymentPurposeName(code, name);
    }

    @Override
    public void insertPaymentPurpose(String code, String name) {
	paymentPurposeGateway.insertPaymentPurpose(new PaymentPurposeDataStructure(code, name));
    }

    @Override
    public void deletePaymentPurpose(String code) {
	paymentPurposeGateway.deletePaymentPurposeByCode(code);
    }

}
