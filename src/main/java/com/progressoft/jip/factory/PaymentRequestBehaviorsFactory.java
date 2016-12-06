package com.progressoft.jip.factory;

import java.util.Collection;

import com.progressoft.jip.datastructures.PaymentRequestDataStructure;

public interface PaymentRequestBehaviorsFactory {
    Behavior<PaymentRequestDataStructure> loadPaymentRequestById();

    Behavior<Void> deletePaymentRequestById();

    Behavior<Void> insertPaymentRequest();

    Behavior<Collection<PaymentRequestDataStructure>> loadPaymentRequests();

    Behavior<Collection<PaymentRequestDataStructure>> loadPaymentRequestsByOrderingAccIBAN();
}
