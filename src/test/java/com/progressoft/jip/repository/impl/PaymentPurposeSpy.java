package com.progressoft.jip.repository.impl;

import com.progressoft.jip.datastructures.PaymentPurposeDataStructure;
import com.progressoft.jip.model.PaymentPurpose;

public class PaymentPurposeSpy extends PaymentPurpose {

    public PaymentPurposeSpy(PaymentPurposeDataStructure dataStructure) {
	super(dataStructure);
    }

    public String getCode() {
	return dataStructure.getCode();
    }

    public String getName() {
	return dataStructure.getName();
    }
}
