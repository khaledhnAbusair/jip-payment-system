package com.progressoft.jip.repository;

import java.util.Collection;

import com.progressoft.jip.model.PaymentRequest;

public interface PaymentRequestRepository {

    PaymentRequest loadPaymentRequestById(int id);

    void inserPaymentRequest(PaymentRequest paymentRequest);

    Collection<PaymentRequest> loadPaymentRequests();

    void deletePaymentRequestById(int id);

}