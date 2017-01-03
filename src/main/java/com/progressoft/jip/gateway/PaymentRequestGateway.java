package com.progressoft.jip.gateway;

import java.util.Collection;

import com.progressoft.jip.datastructures.PaymentRequestDataStructure;

public interface PaymentRequestGateway {

    void deletePaymentRequestById(int id);

    PaymentRequestDataStructure loadPaymentRequestById(int id);

    void insertPaymentRequest(PaymentRequestDataStructure paymentRequestDataStructure);

    Collection<PaymentRequestDataStructure> loadPaymentRequests();

    Collection<PaymentRequestDataStructure> loadPaymentRequestsByOrderingAccountIBAN(String iban);

}