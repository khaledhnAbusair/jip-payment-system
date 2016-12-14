package com.progressoft.jip.report;

import java.io.PrintStream;

import com.progressoft.jip.repository.PaymentRequestRepository;

public class ReportProvider {

    private PaymentRequestRepository paymentRequestRepository;

    public ReportProvider(PaymentRequestRepository paymentRequestRepository) {
	this.paymentRequestRepository = paymentRequestRepository;
    }

    public void print(String orderingAccountIBAN, ReportWriter reportWriter, PrintStream writer) {
	reportWriter.write(orderingAccountIBAN,
		paymentRequestRepository.loadPaymentRequestsByOrderingAccountIBAN(orderingAccountIBAN), writer);
    }

}
