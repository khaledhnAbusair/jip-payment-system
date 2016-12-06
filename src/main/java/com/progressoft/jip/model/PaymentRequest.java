package com.progressoft.jip.model;

import com.progressoft.jip.datastructures.PaymentRequestDataStructure;
import com.progressoft.jip.datastructures.builder.PaymentRequestDataStructureBuilder;

public class PaymentRequest {

    private PaymentRequestDataStructure dataStructure;

    public PaymentRequest(PaymentRequestDataStructure dataStructure) {
	this.dataStructure = dataStructure;
    }

    public void build(PaymentRequestDataStructureBuilder builder) {
	builder.setId(dataStructure.getId()).setOrderingAccountIBAN(dataStructure.getOrderingAccountIBAN())
		.setBeneficiaryAccountIBAN(dataStructure.getBeneficiaryAccountIBAN())
		.setBeneficiaryName(dataStructure.getBeneficiaryName())
		.setPaymentAmount(dataStructure.getPaymentAmount()).setCurrencyCode(dataStructure.getCurrencyCode())
		.setPurposeCdoe(dataStructure.getPurposeCode()).setPaymentDate(dataStructure.getPaymentDate());
    }

}
