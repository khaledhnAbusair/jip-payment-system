package com.progressoft.jip.usecase;

import com.progressoft.jip.datastructures.PaymentRequestDataStructure;
import com.progressoft.jip.model.PaymentRequest;
import com.progressoft.jip.model.exception.AccountRuleViolationException;
import com.progressoft.jip.repository.PaymentRequestRepository;

public class CreateNewPaymentRequestUseCase {
	private PaymentRequestRepository paymentRequestRepository;

	public CreateNewPaymentRequestUseCase(PaymentRequestRepository repository) {
		this.paymentRequestRepository = repository;
	}

	public void createPaymentRequestFrom(PaymentRequestDataStructure paymentRequestDataStructure) throws AccountRuleViolationException {
		PaymentRequest paymentRequest = new PaymentRequest(paymentRequestDataStructure);
		paymentRequestRepository.inserPaymentRequest(paymentRequest);
	}
	
}
