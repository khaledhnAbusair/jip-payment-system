package com.progressoft.jip.gateway;

import java.util.Collection;

import com.progressoft.jip.datastructures.PaymentPurposeDataStructure;

public interface PaymentPurposeGateway {

	PaymentPurposeDataStructure loadPaymentPurposeByCode(String code);

	void insertPaymentPurpose(PaymentPurposeDataStructure paymentPurposeDataStructure);

	Collection<PaymentPurposeDataStructure> loadPaymentPurposes();

	void deletePaymentPurposeByCode(String code);

	void updatePaymentPurposeName(String code, String newName);

}