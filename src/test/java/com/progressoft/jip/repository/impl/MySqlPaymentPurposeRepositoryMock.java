package com.progressoft.jip.repository.impl;

import com.progressoft.jip.gateway.PaymentPurposeGateway;

public class MySqlPaymentPurposeRepositoryMock extends MySqlPaymentPurposeRepository{


    public MySqlPaymentPurposeRepositoryMock(PaymentPurposeGateway paymentPurposeGateway) {
	super(paymentPurposeGateway);
    }
    
    @Override
    public PaymentPurposeSpy loadPaymentPurposeByCode(String code) {
	return new PaymentPurposeSpy(paymentPurposeGateway.loadPaymentPurposeByCode(code));
    }

}
