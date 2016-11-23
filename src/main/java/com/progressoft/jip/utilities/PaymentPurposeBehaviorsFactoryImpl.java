package com.progressoft.jip.utilities;

import java.util.Collection;

import com.progressoft.jip.datastructures.PaymentPurposeDataStructure;

public class PaymentPurposeBehaviorsFactoryImpl implements PaymentPurposeBehaviorsFactory{

    @Override
    public Behavior<PaymentPurposeDataStructure> loadPaymentPurposeByCodeBehavior() {
	return Behaviors.LOAD_PAYMENT_PURPOSE_BY_CODE;
    }

    @Override
    public Behavior<Void> insertPaymentPurposeBehavior() {
	return Behaviors.INSERT_PAYMENT_RURPOSE_BEHAVIOR;
    }

    @Override
    public Behavior<Collection<PaymentPurposeDataStructure>> loadPaymentPurposesBehavior() {
	return Behaviors.LOAD_PAYMENT_PURPOSES;
    }

    @Override
    public Behavior<Void> deletePaymentPurposeByCodeBehavior() {
	return Behaviors.DELETE_PAYMENT_PURPOSE_BY_CODE;
    }

    @Override
    public Behavior<Void> updatePaymentPurposeNameBehavior() {
	return Behaviors.UPDATE_PAYMENT_PURPOSE_NAME;
    }

}
