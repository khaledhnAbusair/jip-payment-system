package com.progressoft.jip.model;

import com.progressoft.jip.datastructures.PaymentPurposeDataStructure;
import com.progressoft.jip.datastructures.builder.PaymentPurposeBuilder;

public class PaymentPurpose {

	protected PaymentPurposeDataStructure dataStructure;

	public PaymentPurpose(PaymentPurposeDataStructure dataStructure) {
		this.dataStructure = dataStructure;
	}

	public PaymentPurposeBuilder buildPaymentPurposeView(PaymentPurposeBuilder builder) {
		return builder.setCode(dataStructure.getCode()).setName(dataStructure.getName());
	}

}
