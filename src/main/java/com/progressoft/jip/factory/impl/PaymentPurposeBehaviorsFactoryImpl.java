package com.progressoft.jip.factory.impl;

import java.sql.SQLException;
import java.util.Collection;
import java.util.List;

import org.apache.commons.dbutils.handlers.BeanListHandler;

import com.progressoft.jip.datastructures.PaymentPurposeDataStructure;
import com.progressoft.jip.factory.AbstractBehavior;
import com.progressoft.jip.factory.Behavior;
import com.progressoft.jip.factory.PaymentPurposeBehaviorsFactory;
import com.progressoft.jip.gateway.exception.DuplicatePaymentPurposeCodeException;
import com.progressoft.jip.gateway.exception.NoneExistingPaymentPurposeException;
import com.progressoft.jip.gateway.exception.PaymentPurposeNotFoundException;
import com.progressoft.jip.utilities.Constants;
import com.progressoft.jip.utilities.Validators;

public class PaymentPurposeBehaviorsFactoryImpl implements PaymentPurposeBehaviorsFactory {

    private static final String SQL_STATE_DUPLICATE_ENTRY = "23000";

    @Override
    public Behavior<PaymentPurposeDataStructure> loadPaymentPurposeByCodeBehavior() {
	return LOAD_PAYMENT_PURPOSE_BY_CODE;
    }

    @Override
    public Behavior<Void> insertPaymentPurposeBehavior() {
	return INSERT_PAYMENT_RURPOSE_BEHAVIOR;
    }

    @Override
    public Behavior<Collection<PaymentPurposeDataStructure>> loadPaymentPurposesBehavior() {
	return LOAD_PAYMENT_PURPOSES;
    }

    @Override
    public Behavior<Void> deletePaymentPurposeByCodeBehavior() {
	return DELETE_PAYMENT_PURPOSE_BY_CODE;
    }

    @Override
    public Behavior<Void> updatePaymentPurposeNameBehavior() {
	return UPDATE_PAYMENT_PURPOSE_NAME;
    }

    public static final Behavior<PaymentPurposeDataStructure> LOAD_PAYMENT_PURPOSE_BY_CODE = new AbstractBehavior<PaymentPurposeDataStructure>() {

	@Override
	public PaymentPurposeDataStructure operation() {
	    String code = (String) parameters[0];
	    Validators.CODE_VALIDATORS.stream().forEach(validator -> validator.validate(code));
	    try {
		List<PaymentPurposeDataStructure> list = runner.query(
			Constants.LOAD_PAYMENT_PURPOSE_BY_CODE_SQL_STATEMENT, new BeanListHandler<>(
				PaymentPurposeDataStructure.class), code);
		if (list.isEmpty())
		    throw new PaymentPurposeNotFoundException();
		return list.get(0);
	    } catch (SQLException e) {
		throw new IllegalStateException(e);
	    }
	}

    };

    public static final Behavior<Void> INSERT_PAYMENT_RURPOSE_BEHAVIOR = new AbstractBehavior<Void>() {

	@Override
	public Void operation() {
	    PaymentPurposeDataStructure paymentPurposeDataStructure = (PaymentPurposeDataStructure) parameters[0];
	    Validators.CODE_LENGTH_VALIDATOR.validate(paymentPurposeDataStructure.getCode());
	    Validators.NAME_LENGTH_VALIDATOR.validate(paymentPurposeDataStructure.getName());
	    try {
		runner.update(Constants.INSERT_PAYMENT_PURPOSE_SQL_STATEMENT, paymentPurposeDataStructure.getCode(),
			paymentPurposeDataStructure.getName());
		return null;
	    } catch (SQLException e) {
		if (e.getSQLState().equals(SQL_STATE_DUPLICATE_ENTRY))
		    throw new DuplicatePaymentPurposeCodeException();
		throw new IllegalStateException(e);
	    }
	}

    };

    public static final Behavior<Collection<PaymentPurposeDataStructure>> LOAD_PAYMENT_PURPOSES = new AbstractBehavior<Collection<PaymentPurposeDataStructure>>() {

	@Override
	public Collection<PaymentPurposeDataStructure> operation() {
	    try {
		return runner.query(Constants.LOAD_PAYMENT_PURPOSES_SQL_STATEMENT, new BeanListHandler<>(
			PaymentPurposeDataStructure.class));
	    } catch (SQLException e) {
		throw new IllegalStateException(e);
	    }
	}

    };

    public static final Behavior<Void> DELETE_PAYMENT_PURPOSE_BY_CODE = new AbstractBehavior<Void>() {

	private String code;

	@Override
	public Void operation() {
	    code = (String) parameters[0];
	    Validators.CODE_VALIDATORS.stream().forEach(validator -> validator.validate(code));
	    try {
		int effectedRows = runner.update(Constants.DELETE_PAYMENT_PURPOSE_SQL_STATEMENT, code);
		if (effectedRows == 0)
		    throw new NoneExistingPaymentPurposeException();
		return null;
	    } catch (SQLException e) {
		throw new IllegalStateException(e);
	    }
	}

    };

    public static final Behavior<Void> UPDATE_PAYMENT_PURPOSE_NAME = new AbstractBehavior<Void>() {

	private String code;
	private String newName;

	@Override
	public Void operation() {
	    code = (String) parameters[0];
	    newName = (String) parameters[1];
	    Validators.CODE_VALIDATORS.stream().forEach(validator -> validator.validate(code));
	    Validators.NULL_NAME_VALIDATOR.validate(newName);
	    int effectedRows;
	    try {
		effectedRows = runner.update(Constants.UPDATE_PAYMENT_PURPOSE_SQL_STATEMENT, newName, code);
		if (effectedRows == 0)
		    throw new NoneExistingPaymentPurposeException();
		return null;
	    } catch (SQLException e) {
		throw new IllegalStateException(e);
	    }
	}

    };

}
