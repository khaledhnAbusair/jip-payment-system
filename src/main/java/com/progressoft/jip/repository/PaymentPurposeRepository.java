package com.progressoft.jip.repository;

import java.util.Collection;

import com.progressoft.jip.model.PaymentPurpose;

public interface PaymentPurposeRepository {

    PaymentPurpose loadPaymentPurposeByCode(String code);

    Collection<PaymentPurpose> loadPaymentPurposes();

    void updatePaymenPurposeName(String code, String name);

    void insertPaymentPurpose(String code, String name);

    void deletePaymentPurpose(String code);

}