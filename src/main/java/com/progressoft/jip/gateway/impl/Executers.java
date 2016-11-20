package com.progressoft.jip.gateway.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.progressoft.jip.datastructures.PaymentPurposeDataStructure;
import com.progressoft.jip.gateway.impl.MySQLPaymentPurposeGateway.NoneExistingPaymentPurposeException;

public class Executers {
    
    private static final int NAME_COLUMN = 2;
    private static final int CODE_COLUMN = 1;
    
    interface QueryExecuter {
	void execute(PreparedStatement preparedStatement);
    }

    interface QueryExecuterWithValue<T> {
	T execute(PreparedStatement preparedStatement);
    }

    interface QueryExecuterWithMultiValue<T> {
	Collection<T> execute(PreparedStatement preparedStatement);
    }
    
    public static final QueryExecuter insertPaymentPurpose = (preparedStatement) -> {
	try {
	    preparedStatement.executeUpdate();
	} catch (SQLException e) {
	    throw new IllegalStateException(e);
	}
    };

    public static final QueryExecuter DELETE_PAYMENT_PURPOSE_BY_CODE = (preparedStatement) -> {
	try {
	    int effectedRows = preparedStatement.executeUpdate();
	    if (effectedRows == 0)
		throw new NoneExistingPaymentPurposeException();
	} catch (SQLException e) {
	    throw new IllegalStateException(e);
	}
    };
    
    public static final QueryExecuter UPDATE_PAYMENT_PURPOSE_BY_NAME = (preparedStatement) -> {
	try {
	    int effectedRows = preparedStatement.executeUpdate();
	    if (effectedRows == 0)
		throw new NoneExistingPaymentPurposeException();
	} catch (SQLException e) {
	    throw new IllegalStateException(e);
	}
    };

    public static final QueryExecuterWithValue<PaymentPurposeDataStructure> LOAD_PAYMENT_PURPOSE_FROM_DB_BY_CODE = (preparedStatement) -> {
	try {
	    ResultSet resultSet = preparedStatement.executeQuery();
	    if (isEmptyResultSet(resultSet))
		throw new MySQLPaymentPurposeGateway.PaymentPurposeNotFoundException();
	    return new PaymentPurposeDataStructure(resultSet.getString(CODE_COLUMN), resultSet.getString(NAME_COLUMN));
	} catch (SQLException e) {
	    throw new IllegalStateException(e);
	}
    };

    public static final QueryExecuterWithMultiValue<PaymentPurposeDataStructure> LOAD_PAYMENT_PURPOSES = (preparedStatement) -> {
	List<PaymentPurposeDataStructure> list = new ArrayList<PaymentPurposeDataStructure>();
	try {
	    ResultSet resultSet = preparedStatement.executeQuery();
	    while (resultSet.next())
		list.add(new PaymentPurposeDataStructure(resultSet.getString(CODE_COLUMN), resultSet
			.getString(NAME_COLUMN)));
	    return new ArrayList<>(list);
	} catch (SQLException e) {
	    throw new IllegalStateException(e);
	}
    };
    
    private static boolean isEmptyResultSet(ResultSet resultSet) throws SQLException {
	return !resultSet.next();
    }

}
