package com.progressoft.jip.gateway.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Objects;

import javax.sql.DataSource;

import com.progressoft.jip.datastructures.PaymentPurposeDataStructure;
import com.progressoft.jip.gateway.PaymentPurposeGateway;

public class MySQLPaymentPurposeGateway implements PaymentPurposeGateway {

    private static final int CODE_COLUMN_MAX_LENGTH = 10;
    private static final int NAME_COLUMN_MAX_LENGTH = 45;
    private Connection connection;

    public MySQLPaymentPurposeGateway(DataSource dataSource) {
	if (Objects.isNull(dataSource))
	    throw new NullDataSourceException();
	initConnection(dataSource);
    }

    @Override
    public PaymentPurposeDataStructure loadPaymentPurposeByCode(String code) {
	if (Objects.isNull(code))
	    throw new NullPaymentPurposeCodeException();
	if (code.isEmpty())
	    throw new EmptyPaymentPurposeCodeException();
	return Executers.LOAD_PAYMENT_PURPOSE_FROM_DB_BY_CODE.execute(stringValuesPreparedStatement(
		"select * from PAYMENT_SYSTEM.PAYMENT_PURPOSE WHERE CODE = ?", code));
    }

    @Override
    public void insertPaymentPurpose(PaymentPurposeDataStructure paymentPurposeDataStructure) {
	if (paymentPurposeDataStructure.getCode().length() > CODE_COLUMN_MAX_LENGTH)
	    throw new DataExceedingCodeColumnLimitException();
	if (paymentPurposeDataStructure.getName().length() > NAME_COLUMN_MAX_LENGTH)
	    throw new DataExceedingNameColumnLimitException();
	if (isExistedPaymentPurposeCode(paymentPurposeDataStructure.getCode()))
	    throw new DuplicatePaymentPurposeCodeException();
	Executers.insertPaymentPurpose.execute(stringValuesPreparedStatement(
		"insert into PAYMENT_SYSTEM.PAYMENT_PURPOSE (CODE,NAME) VALUES(?,?)",
		paymentPurposeDataStructure.getCode(), paymentPurposeDataStructure.getName()));
    }

    @Override
    public Collection<PaymentPurposeDataStructure> loadPaymentPurposes() {
	return Executers.LOAD_PAYMENT_PURPOSES
		.execute(stringValuesPreparedStatement("select * from PAYMENT_SYSTEM.PAYMENT_PURPOSE"));
    }

    @Override
    public void deletePaymentPurposeByCode(String code) {
	if (Objects.isNull(code))
	    throw new NullPaymentPurposeCodeException();
	if (code.isEmpty())
	    throw new EmptyPaymentPurposeCodeException();
	Executers.DELETE_PAYMENT_PURPOSE_BY_CODE.execute(stringValuesPreparedStatement(
		"delete from PAYMENT_SYSTEM.PAYMENT_PURPOSE where CODE = ?", code));
    }

    @Override
    public void updatePaymentPurposeName(String code, String newName) {
	if (Objects.isNull(code))
	    throw new NullPaymentPurposeCodeException();
	if (code.isEmpty())
	    throw new EmptyPaymentPurposeCodeException();
	if (Objects.isNull(newName))
	    throw new NullPaymentPurposeNameException();
	Executers.UPDATE_PAYMENT_PURPOSE_BY_NAME.execute(stringValuesPreparedStatement(
		"UPDATE PAYMENT_SYSTEM.PAYMENT_PURPOSE SET NAME = ? WHERE CODE = ?", newName, code));
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
	    throw new IllegalStateException("Can't get PaymentPurposeGateway connection");
	}
    }

    private PreparedStatement stringValuesPreparedStatement(String sql, String... vals) {
	try {
	    PreparedStatement prepareStatement = connection.prepareStatement(sql);
	    for (int i = 0; i < vals.length; ++i)
		prepareStatement.setString(i + 1, vals[i]);
	    return prepareStatement;
	} catch (SQLException e) {
	    throw new IllegalStateException(e);
	}
    }

    static class NoneExistingPaymentPurposeException extends RuntimeException {
	private static final long serialVersionUID = 1L;
    }

    static class DataExceedingNameColumnLimitException extends RuntimeException {
	private static final long serialVersionUID = 1L;
    }

    static class DataExceedingCodeColumnLimitException extends RuntimeException {
	private static final long serialVersionUID = 1L;
    }

    static class DuplicatePaymentPurposeCodeException extends RuntimeException {
	private static final long serialVersionUID = 1L;
    }

    static class NullDataSourceException extends RuntimeException {
	private static final long serialVersionUID = 1L;
    }

    static class PaymentPurposeNotFoundException extends RuntimeException {
	private static final long serialVersionUID = 1L;
    }

    static class EmptyPaymentPurposeCodeException extends RuntimeException {
	private static final long serialVersionUID = 1L;
    }

    static class NullPaymentPurposeCodeException extends RuntimeException {
	private static final long serialVersionUID = 1L;
    }

    static class EmptyPaymentPurposeNameException extends RuntimeException {
	private static final long serialVersionUID = 1L;
    }

    static class NullPaymentPurposeNameException extends RuntimeException {
	private static final long serialVersionUID = 1L;
    }

}
