package com.progressoft.jip.factory;

import java.util.Collection;

import com.progressoft.jip.datastructures.PaymentPurposeDataStructure;

public interface PaymentPurposeBehaviorsFactory {
    
    Behavior<PaymentPurposeDataStructure> loadPaymentPurposeByCodeBehavior();

    Behavior<Void> insertPaymentPurposeBehavior();

    Behavior<Collection<PaymentPurposeDataStructure>> loadPaymentPurposesBehavior();

    Behavior<Void> deletePaymentPurposeByCodeBehavior();

    Behavior<Void> updatePaymentPurposeNameBehavior();

}
