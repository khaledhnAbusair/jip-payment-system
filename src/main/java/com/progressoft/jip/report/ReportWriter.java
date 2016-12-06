package com.progressoft.jip.report;

import java.io.PrintStream;
import java.util.Collection;

import com.progressoft.jip.model.PaymentRequest;

public interface ReportWriter {
    void write(String accountIBAN, Collection<PaymentRequest> paymentRequests,PrintStream writer);
}
