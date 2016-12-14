package com.progressoft.jip.model;

import java.util.Arrays;

import com.progressoft.jip.datastructures.PaymentRequestDataStructure;
import com.progressoft.jip.datastructures.builder.PaymentRequestDataStructureBuilder;
import com.progressoft.jip.iban.IBANValidator;

public class PaymentRequest {

    private PaymentRequestDataStructure dataStructure;

    public PaymentRequest(PaymentRequestDataStructure dataStructure) {
	this.dataStructure = dataStructure;
    }

    public boolean validateIBAN(IBANValidator... validators) {
	return Arrays.asList(validators).stream()
		.allMatch((validator) -> validator.isValid(dataStructure.getBeneficiaryAccountIBAN()));
    }

    public void build(PaymentRequestDataStructureBuilder builder) {
	builder.setId(dataStructure.getId()).setOrderingAccountIBAN(dataStructure.getOrderingAccountIBAN())
		.setBeneficiaryAccountIBAN(dataStructure.getBeneficiaryAccountIBAN())
		.setBeneficiaryName(dataStructure.getBeneficiaryName())
		.setPaymentAmount(dataStructure.getPaymentAmount()).setCurrencyCode(dataStructure.getCurrencyCode())
		.setPurposeCdoe(dataStructure.getPurposeCode()).setPaymentDate(dataStructure.getPaymentDate());
    }

}
