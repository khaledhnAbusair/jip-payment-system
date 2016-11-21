package com.progressoft.jip.gateway.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Objects;

import javax.sql.DataSource;

import com.progressoft.jip.datastructures.PaymentPurposeDataStructure;
import com.progressoft.jip.exception.DuplicatePaymentPurposeCodeException;
import com.progressoft.jip.exception.NullDataSourceException;
import com.progressoft.jip.exception.PaymentPurposeNotFoundException;
import com.progressoft.jip.gateway.PaymentPurposeGateway;
import com.progressoft.jip.utilities.Constants;
import com.progressoft.jip.utilities.Executers;
import com.progressoft.jip.utilities.Validators;
import com.progressoft.jip.utilities.Validators.Validator;

public class MySQLPaymentPurposeGateway implements PaymentPurposeGateway {

    private final Validator<String> DUPLICATE_CODE_VALIDATOR = (code) -> {
	if (isExistedPaymentPurposeCode(code))
	    throw new DuplicatePaymentPurposeCodeException();
    };

    public MySQLPaymentPurposeGateway(DataSource dataSource) {
	if (Objects.isNull(dataSource))
	    throw new NullDataSourceException();
	initConnection(dataSource);
    }

    private Connection connection;

    @Override
    public PaymentPurposeDataStructure loadPaymentPurposeByCode(String code) {
	Validators.CODE_VALIDATORS.stream().forEach(validator -> validator.validate(code));
	return Executers.LOAD_PAYMENT_PURPOSE_FROM_DB_BY_CODE.execute(preparedStatement(
		Constants.LOAD_PAYMENT_PURPOSE_BY_CODE_SQL_STATEMENT, code));
    }

    @Override
    public void insertPaymentPurpose(PaymentPurposeDataStructure paymentPurposeDataStructure) {
	Validators.CODE_LENGTH_VALIDATOR.validate(paymentPurposeDataStructure.getCode());
	Validators.NAME_LENGTH_VALIDATOR.validate(paymentPurposeDataStructure.getName());
	DUPLICATE_CODE_VALIDATOR.validate(paymentPurposeDataStructure.getCode());
	Executers.INSERT_PAYMENT_PURPOSE.execute(preparedStatement(
		Constants.INSERT_PAYMENT_PURPOSE_SQL_STATEMENT,
		paymentPurposeDataStructure.getCode(), paymentPurposeDataStructure.getName()));
    }

    @Override
    public Collection<PaymentPurposeDataStructure> loadPaymentPurposes() {
	return Executers.LOAD_PAYMENT_PURPOSES
		.execute(preparedStatement(Constants.LOAD_PAYMENT_PURPOSES_SQL_STATEMENT));
    }

    @Override
    public void deletePaymentPurposeByCode(String code) {
	Validators.CODE_VALIDATORS.stream().forEach(validator -> validator.validate(code));
	Executers.DELETE_PAYMENT_PURPOSE_BY_CODE.execute(preparedStatement(
		Constants.DELETE_PAYMENT_PURPOSE_SQL_STATEMENT, code));
    }

    @Override
    public void updatePaymentPurposeName(String code, String newName) {
	Validators.CODE_VALIDATORS.stream().forEach(validator -> validator.validate(code));
	Validators.NULL_NAME_VALIDATOR.validate(newName);
	Executers.UPDATE_PAYMENT_PURPOSE_BY_NAME.execute(preparedStatement(
		Constants.UPDATE_PAYMENT_PURPOSE_SQL_STATEMENT, newName, code));
    }

    private boolean isExistedPaymentPurposeCode(String code) {
	try {
	    loadPaymentPurposeByCode(code);
	    return true;
	} catch (PaymentPurposeNotFoundException e) {
	    return false;
	}
    }

    private void initConnection(DataSource dataSource) {
	try {
	    this.connection = dataSource.getConnection();
	} catch (SQLException e) {
	    throw new IllegalStateException("Can't establish PaymentPurposeGateway connection");
	}
    }

    private PreparedStatement preparedStatement(String sql, Object... vals) {
	try {
	    PreparedStatement prepareStatement = connection.prepareStatement(sql);
	    for (int i = 0; i < vals.length; ++i)
		prepareStatement.setObject(i + 1, vals[i]);
	    return prepareStatement;
	} catch (SQLException e) {
	    throw new IllegalStateException(e);
	}
    }

}
