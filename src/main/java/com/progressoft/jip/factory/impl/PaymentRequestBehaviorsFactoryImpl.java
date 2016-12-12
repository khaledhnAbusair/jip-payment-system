package com.progressoft.jip.factory.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

import org.apache.commons.dbutils.handlers.BeanListHandler;

import com.progressoft.jip.datastructures.PaymentRequestDataStructure;
import com.progressoft.jip.factory.AbstractBehavior;
import com.progressoft.jip.factory.Behavior;
import com.progressoft.jip.factory.PaymentRequestBehaviorsFactory;
import com.progressoft.jip.gateway.exception.AccountDoesNotHavePaymentRequestsException;
import com.progressoft.jip.gateway.exception.EmptyAccountIBANException;
import com.progressoft.jip.gateway.exception.NoneExistingPaymentRequestException;
import com.progressoft.jip.gateway.exception.NullAccountIBANException;
import com.progressoft.jip.utilities.Constants;

public class PaymentRequestBehaviorsFactoryImpl implements PaymentRequestBehaviorsFactory {

    @Override
    public Behavior<PaymentRequestDataStructure> loadPaymentRequestById() {
	return LOAD_PAYMENT_REQUEST_BY_ID;
    }

    @Override
    public Behavior<Void> deletePaymentRequestById() {
	return DELETE_PAYMENT_REQUEST_BY_ID;
    }

    @Override
    public Behavior<Void> insertPaymentRequest() {
	return INSERT_PAYMENT_REQUEST;
    }

    @Override
    public Behavior<Collection<PaymentRequestDataStructure>> loadPaymentRequests() {
	return LOAD_PAYMENT_REQUESTS;
    }

    @Override
    public Behavior<Collection<PaymentRequestDataStructure>> loadPaymentRequestsByOrderingAccIBAN() {
	return LOAD_PAYMENT_REQUESTS_BY_ORDERING_ACCOUNT_IBAN;
    }

    public static final Behavior<PaymentRequestDataStructure> LOAD_PAYMENT_REQUEST_BY_ID = new AbstractBehavior<PaymentRequestDataStructure>() {

	private int id;

	@Override
	public PaymentRequestDataStructure operation() {
	    id = (int) parameters[0];
	    try {
		List<PaymentRequestDataStructure> list = runner.query(
			Constants.LOAD_PAYMENT_REQUEST_BY_ID_SQL_STATEMENT, new BeanListHandler<>(
				PaymentRequestDataStructure.class), id);
		if (list.isEmpty())
		    throw new NoneExistingPaymentRequestException();
		return list.get(0);
	    } catch (SQLException e) {
		throw new IllegalStateException(e);
	    }

	}

    };

    public static final Behavior<Void> DELETE_PAYMENT_REQUEST_BY_ID = new AbstractBehavior<Void>() {

	@Override
	public Void operation() {
	    int id = (int) parameters[0];
	    try {
		int effectedRow = runner.update(Constants.DELETE_PAYMENT_REQUEST_SQL_STATMENT, id);
		if (effectedRow == 0)
		    throw new NoneExistingPaymentRequestException();
		return null;
	    } catch (SQLException e) {
		throw new IllegalStateException(e);
	    }
	}

    };

    public static final Behavior<Void> INSERT_PAYMENT_REQUEST = new AbstractBehavior<Void>() {

	@Override
	public Void operation() {
	    PaymentRequestDataStructure dataStructure = (PaymentRequestDataStructure) parameters[0];
	    try {
		int effectedRow = runner
			.update(Constants.INSERT_PAYMENT_REQUEST_SQL, dataStructure.getOrderingAccountIBAN(),
				dataStructure.getBeneficiaryAccountIBAN(), dataStructure.getBeneficiaryName(),
				dataStructure.getPaymentAmount(), dataStructure.getCurrencyCode(),
				dataStructure.getPurposeCode(), dataStructure.getPaymentDate());
		if (effectedRow == 0)
		    throw new NoneExistingPaymentRequestException();
		return null;
	    } catch (SQLException e) {
		throw new IllegalStateException(e);
	    }
	}

    };

    public static final Behavior<Collection<PaymentRequestDataStructure>> LOAD_PAYMENT_REQUESTS = new AbstractBehavior<Collection<PaymentRequestDataStructure>>() {

	@Override
	public Collection<PaymentRequestDataStructure> operation() {
	    try {
		return runner.query(Constants.SELECT_ALL_PAYMENT_REQUESTS, new BeanListHandler<>(
			PaymentRequestDataStructure.class));
	    } catch (SQLException e) {
		throw new IllegalStateException(e);
	    }
	}
    };

    public static final Behavior<Collection<PaymentRequestDataStructure>> LOAD_PAYMENT_REQUESTS_BY_ORDERING_ACCOUNT_IBAN = new AbstractBehavior<Collection<PaymentRequestDataStructure>>() {

	@Override
	public Collection<PaymentRequestDataStructure> operation() {
	    String IBAN = (String) parameters[0];
	    if (Objects.isNull(IBAN))
		throw new NullAccountIBANException();
	    if (IBAN.isEmpty())
		throw new EmptyAccountIBANException();
	    try {
		List<PaymentRequestDataStructure> paymentRequests = runner.query(
			Constants.SELECT_PAYMENT_REQUESTS_BY_ORDERING_ACCOUNT_IBAN, new BeanListHandler<>(
				PaymentRequestDataStructure.class), IBAN);
		if (paymentRequests.isEmpty())
		    throw new AccountDoesNotHavePaymentRequestsException();
		return new ArrayList<PaymentRequestDataStructure>(paymentRequests);
	    } catch (SQLException e) {
		throw new IllegalStateException(e);
	    }
	}

    };

}
