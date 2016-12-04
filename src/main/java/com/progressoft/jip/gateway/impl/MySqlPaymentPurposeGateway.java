package com.progressoft.jip.gateway.impl;

import java.util.Collection;

import javax.sql.DataSource;

import com.progressoft.jip.datastructures.PaymentPurposeDataStructure;
import com.progressoft.jip.gateway.PaymentPurposeGateway;
import com.progressoft.jip.utilities.Behavior;
import com.progressoft.jip.utilities.PaymentPurposeBehaviorsFactory;

public class MySqlPaymentPurposeGateway extends AbstractGateway implements PaymentPurposeGateway {

    private final Behavior<PaymentPurposeDataStructure> loadPaymentPurposeByCode;
    private final Behavior<Void> insertPaymentPurpose;
    private final Behavior<Collection<PaymentPurposeDataStructure>> loadPaymentPurposes;
    private final Behavior<Void> deletePaymentPurposeByCode;
    private final Behavior<Void> updatePaymentPurposeName;

    public MySqlPaymentPurposeGateway(DataSource dataSource, PaymentPurposeBehaviorsFactory factory) {
	super(dataSource);
	this.insertPaymentPurpose = factory.insertPaymentPurposeBehavior();
	this.loadPaymentPurposeByCode = factory.loadPaymentPurposeByCodeBehavior();
	this.loadPaymentPurposes = factory.loadPaymentPurposesBehavior();
	this.deletePaymentPurposeByCode = factory.deletePaymentPurposeByCodeBehavior();
	this.updatePaymentPurposeName = factory.updatePaymentPurposeNameBehavior();
    }

    @Override
    public PaymentPurposeDataStructure loadPaymentPurposeByCode(String code) {
	return loadPaymentPurposeByCode.execute(dataSource, code);
    }

    @Override
    public void insertPaymentPurpose(PaymentPurposeDataStructure paymentPurposeDataStructure) {
	insertPaymentPurpose.execute(dataSource, paymentPurposeDataStructure);
    }

    @Override
    public Collection<PaymentPurposeDataStructure> loadPaymentPurposes() {
	return loadPaymentPurposes.execute(dataSource);
    }

    @Override
    public void deletePaymentPurposeByCode(String code) {
	deletePaymentPurposeByCode.execute(dataSource, code);
    }

    @Override
    public void updatePaymentPurposeName(String code, String newName) {
	updatePaymentPurposeName.execute(dataSource, code, newName);
    }

}
