package com.progressoft.jip.repository;

import java.util.Collection;

import com.progressoft.jip.model.PaymentRequest;
import com.progressoft.jip.model.exception.AccountRuleViolationException;
import com.progressoft.jip.utilities.chequewriting.ChequeAmountWriter;

public interface PaymentRequestRepository {

    PaymentRequest loadPaymentRequestById(int id);

    void inserPaymentRequest(PaymentRequest paymentRequest) throws AccountRuleViolationException;

    Collection<PaymentRequest> loadPaymentRequests();

    void deletePaymentRequestById(int id);

    Collection<PaymentRequest> loadPaymentRequestsByOrderingAccountIBAN(String iban);
    
    void registerChequeWriter(ChequeAmountWriter chequeAmountWriter);

}