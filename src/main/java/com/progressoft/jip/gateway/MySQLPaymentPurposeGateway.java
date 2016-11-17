package com.progressoft.jip.gateway;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Objects;

import javax.sql.DataSource;

import com.progressoft.jip.datastructures.PaymentPurposeDataStructure;

public class MySQLPaymentPurposeGateway {

	private static final int NAME_COLUMN = 2;
	private static final int CODE_COLUMN = 1;
	private Connection connection;

	public MySQLPaymentPurposeGateway(DataSource dataSource) {
		if (Objects.isNull(dataSource))
			throw new NullDataSourceException();
		initConnection(dataSource);
	}

	public PaymentPurposeDataStructure loadPaymentPurposeByCode(String code) {
		if (Objects.isNull(code))
			throw new NullPaymentPurposeCodeException();
		if (code.isEmpty())
			throw new EmptyPaymentPurposeCodeException();
		return loadPaymentPurposeFromDatabaseByCode(code);
	}

	private void initConnection(DataSource dataSource) {
		try {
			this.connection = dataSource.getConnection();
		} catch (SQLException e) {
			throw new IllegalStateException("Can't get PaymentPurposeGateway connection");
		}
	}

	private PaymentPurposeDataStructure loadPaymentPurposeFromDatabaseByCode(String code) {
		try {
			ResultSet resultSet = stringValuesPreparedStatement(
					"select * from PAYMENT_SYSTEM.PAYMENT_PURPOSE WHERE CODE = ?", code).executeQuery();
			if(isEmptyResultSet(resultSet))
				throw new EmptyResultSetException();
			return new PaymentPurposeDataStructure(resultSet.getString(CODE_COLUMN), resultSet.getString(NAME_COLUMN));
		} catch (SQLException e) {
			throw new IllegalStateException(e);
		}
	}

	private boolean isEmptyResultSet(ResultSet resultSet) throws SQLException {
		return !resultSet.next();
	}

	private PreparedStatement stringValuesPreparedStatement(String sql, String ... vals) {
		try {
			PreparedStatement prepareStatement = connection.prepareStatement(sql);
			for (int i = 0; i < vals.length; ++i)
				prepareStatement.setString(i + 1, vals[i]);
			return prepareStatement;
		} catch (SQLException e) {
			throw new IllegalStateException(e);
		}
	}
	
	static class NullDataSourceException extends RuntimeException {
		private static final long serialVersionUID = 1L;
	}

	static class EmptyResultSetException extends RuntimeException {
		private static final long serialVersionUID = 1L;
	}

	static class EmptyPaymentPurposeCodeException extends RuntimeException {
		private static final long serialVersionUID = 1L;
	}

	static class NullPaymentPurposeCodeException extends RuntimeException {
		private static final long serialVersionUID = 1L;
	}
}
