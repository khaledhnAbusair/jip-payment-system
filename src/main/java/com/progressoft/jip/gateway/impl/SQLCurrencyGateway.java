package com.progressoft.jip.gateway.impl;

import static com.progressoft.jip.gateway.impl.ConstantSQLQueries.DELETE_CRNCY;
import static com.progressoft.jip.gateway.impl.ConstantSQLQueries.INSERT_CRNCY;
import static com.progressoft.jip.gateway.impl.ConstantSQLQueries.SELECT_ALL_CRNCYS;
import static com.progressoft.jip.gateway.impl.ConstantSQLQueries.SELECT_CRNCY_BY_CODE;
import static com.progressoft.jip.gateway.impl.ConstantSQLQueries.UPDATE_CRNCY_RATE_BY_CODE;
import static com.progressoft.jip.utilities.QueryHandler.executeQuery;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import com.progressoft.jip.datastructures.CurrencyDataStructure;
import com.progressoft.jip.gateway.CurrencyGateway;

public class SQLCurrencyGateway implements CurrencyGateway {
	static final String CRNCY_CODE_COLOMN = "CRNCY_CODE";
	static final String CRNCY_RATE_COLOMN = "CRNCY_RATE";
	static final String CRNCY_DESC_COLOMN = "CRNCY_DESC";
	static final String DB_NAME = "payment_system";
	
	private DataSource dataSource;

	public SQLCurrencyGateway(DataSource dataSource) {

		this.dataSource = dataSource;
	}

	@Override
	public Iterable<CurrencyDataStructure> loadCurrencies() {
		return executeQuery(dataSource, SELECT_ALL_CRNCYS, statement -> {
			List<CurrencyDataStructure> currencies = new ArrayList<>();
			ResultSet rs = statement.executeQuery();
			checkEmptyResultSet(rs);
			while (rs.next()) {
				currencies.add(buildCurrency(rs));
			}
			return currencies;
		});
	}

	public CurrencyDataStructure getCurrencyByCode(String currencyCode) {
		checkCurrnecyCodeLength(currencyCode);
		return executeQuery(dataSource, SELECT_CRNCY_BY_CODE, statement -> {
			statement.setString(1, currencyCode);
			ResultSet resultSet = statement.executeQuery();
			checkEmptyResultSet(resultSet);
			resultSet.next();
			return buildCurrency(resultSet);
		});
	}

	@Override
	public int updateCurrencyRateByCode(String currencyCode, double currencyRate) {
		checkCurrnecyCodeLength(currencyCode);
		return executeQuery(dataSource, UPDATE_CRNCY_RATE_BY_CODE, statement -> {
			statement.setDouble(1, currencyRate);
			statement.setString(2, currencyCode);
			statement.setDouble(3, currencyRate);
			return statement.executeUpdate();
		});
	}

	@Override
	public int createCurrency(CurrencyDataStructure currency) {
		return executeQuery(dataSource, INSERT_CRNCY, statement -> {
			statement.setString(1, currency.getCurrencyCode());
			statement.setDouble(2, currency.getCurrencyRate());
			statement.setString(3, currency.getCurrencyDescription());
			return statement.executeUpdate();
		});
	}

	@Override
	public int deleteCurrency(CurrencyDataStructure currency) {
		return executeQuery(dataSource, DELETE_CRNCY, statement -> {
			statement.setString(1, currency.getCurrencyCode());
			statement.setString(2, currency.getCurrencyDescription());
			return statement.executeUpdate();
		});
	}

	private CurrencyDataStructure buildCurrency(ResultSet rs) {
		try {
			String currCode = rs.getString(CRNCY_CODE_COLOMN);
			String currDescription = rs.getString(CRNCY_DESC_COLOMN);
			double currRate = rs.getDouble(CRNCY_RATE_COLOMN);
			CurrencyDataStructure currency = new CurrencyDataStructure();
			currency.setCurrencyCode(currCode);
			currency.setCurrencyDescription(currDescription);
			currency.setCurrencyRate(currRate);
			return currency;
		} catch (SQLException e) {
			throw new IllegalStateException(e);
		}
	}

	private void checkEmptyResultSet(ResultSet rs) throws SQLException {
		if (!rs.isBeforeFirst()) {
			throw new EmptyResultSetException();
		}
	}

	private void checkCurrnecyCodeLength(String currencyCode) {
		if (currencyCode.length() < 3)
			throw new ShortCurrencyCodeException();
	}

	static class EmptyResultSetException extends RuntimeException {

		private static final long serialVersionUID = 1L;
	}

	static class ShortCurrencyCodeException extends RuntimeException {
		private static final long serialVersionUID = 1L;
	}
}
