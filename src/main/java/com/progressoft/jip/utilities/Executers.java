package com.progressoft.jip.utilities;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.progressoft.jip.datastructures.PaymentPurposeDataStructure;
import com.progressoft.jip.datastructures.PaymentRequestDataStructure;
import com.progressoft.jip.gateway.exception.NoneExistingPaymentPurposeException;
import com.progressoft.jip.gateway.exception.NoneExistingPaymentRequestException;
import com.progressoft.jip.gateway.exception.PaymentPurposeNotFoundException;

public class Executers {

    public interface QueryExecuter {
	void execute(PreparedStatement preparedStatement);
    }

    public interface QueryExecuterWithValue<T> {
	T execute(PreparedStatement preparedStatement);
    }

    public interface QueryExecuterWithMultiValue<T> {
	Collection<T> execute(PreparedStatement preparedStatement);
    }

    public static final QueryExecuterWithValue<Integer> INSERT_PAYMENT_PURPOSE = (preparedStatement) -> {
	try {
	    return preparedStatement.executeUpdate();
	} catch (SQLException e) {
	    throw new IllegalStateException(e);
	}
    };

    public static final QueryExecuter DELETE_PAYMENT_PURPOSE_BY_CODE = (preparedStatement) -> {
	try {
	    if (preparedStatement.executeUpdate() == 0)
		throw new NoneExistingPaymentPurposeException();
	} catch (SQLException e) {
	    throw new IllegalStateException(e);
	}
    };

    public static final QueryExecuter UPDATE_PAYMENT_PURPOSE_BY_NAME = (preparedStatement) -> {
	try {
	    if (preparedStatement.executeUpdate() == 0)
		throw new NoneExistingPaymentPurposeException();
	} catch (SQLException e) {
	    throw new IllegalStateException(e);
	}
    };

    public static final QueryExecuterWithValue<PaymentPurposeDataStructure> LOAD_PAYMENT_PURPOSE_FROM_DB_BY_CODE = (
	    preparedStatement) -> {
	try {
	    ResultSet resultSet = preparedStatement.executeQuery();
	    if (isEmptyResultSet(resultSet))
		throw new PaymentPurposeNotFoundException();
	    return new PaymentPurposeDataStructure(resultSet.getString(Constants.PAYMENT_PURPOSE_CODE_COLUMN_INDEX),
		    resultSet.getString(Constants.PAYMENT_PURPOSE_NAME_COLUMN_INDEX));
	} catch (SQLException e) {
	    throw new IllegalStateException(e);
	}
    };

    public static final QueryExecuterWithMultiValue<PaymentPurposeDataStructure> LOAD_PAYMENT_PURPOSES = (
	    preparedStatement) -> {
	List<PaymentPurposeDataStructure> list = new ArrayList<PaymentPurposeDataStructure>();
	try {
	    ResultSet resultSet = preparedStatement.executeQuery();
	    while (resultSet.next())
		list.add(new PaymentPurposeDataStructure(resultSet
			.getString(Constants.PAYMENT_PURPOSE_CODE_COLUMN_INDEX), resultSet
			.getString(Constants.PAYMENT_PURPOSE_NAME_COLUMN_INDEX)));
	    return new ArrayList<>(list);
	} catch (SQLException e) {
	    throw new IllegalStateException(e);
	}
    };
    public static final QueryExecuter DELETE_PAYMENT_REQUEST_BY_ID = (preparedStatement) -> {
	try {
	    if (preparedStatement.executeUpdate() == 0)
		throw new NoneExistingPaymentRequestException();
	} catch (SQLException e) {
	    throw new IllegalStateException(e);
	}
    };
    public static final QueryExecuterWithValue<PaymentRequestDataStructure> LOAD_PAYMENT_REQUEST_BY_ID = (
	    preparedStatement) -> {
		
	final int ID_COLUMN = 1;	
		
	try {
	    ResultSet resultSet = preparedStatement.executeQuery();
	    if(!resultSet.next())
		throw new NoneExistingPaymentRequestException();
	    return new PaymentRequestDataStructure().setId(resultSet.getInt(ID_COLUMN))
        	    .setOrderingAccountIBAN(resultSet.getString(2))
        	    .setBeneficiaryAccountIBAN(resultSet.getString(3))
        	    .setBeneficiaryName(resultSet.getString(4))
        	    .setPaymentAmount(resultSet.getDouble(5))
        	    .setCurrencyCode(resultSet.getString(6))
        	    .setPurposeCode(resultSet.getString(7))
        	    .setPaymentDate(resultSet.getDate(8));
	} catch (SQLException e) {
	    throw new IllegalStateException(e);
	}
    };

    private static boolean isEmptyResultSet(ResultSet resultSet) throws SQLException {
	return !resultSet.next();
    }

}
