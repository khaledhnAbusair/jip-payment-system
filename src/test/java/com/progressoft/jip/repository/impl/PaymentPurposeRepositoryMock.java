package com.progressoft.jip.repository.impl;

import com.progressoft.jip.gateway.PaymentPurposeGateway;

public class PaymentPurposeRepositoryMock extends PaymentPurposeRepositoryImpl{


    public PaymentPurposeRepositoryMock(PaymentPurposeGateway paymentPurposeGateway) {
	super(paymentPurposeGateway);
    }
    
    @Override
    public PaymentPurposeSpy loadPaymentPurposeByCode(String code) {
	return new PaymentPurposeSpy(paymentPurposeGateway.loadPaymentPurposeByCode(code));
    }

}
