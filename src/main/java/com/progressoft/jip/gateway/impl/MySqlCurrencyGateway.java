package com.progressoft.jip.gateway.impl;

import static com.progressoft.jip.utilities.QueryHandler.executeQueryAndGet;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import com.progressoft.jip.datastructures.CurrencyDataStructure;
import com.progressoft.jip.gateway.CurrencyGateway;
import com.progressoft.jip.utilities.QueryRunner;
import com.progressoft.jip.utilities.QueryRunner.QueryExecutor;

public class MySqlCurrencyGateway implements CurrencyGateway {

	static final String CRNCY_CODE_COLOMN = "CRNCY_CODE";
	static final String CRNCY_RATE_COLOMN = "CRNCY_RATE";
	static final String CRNCY_DESC_COLOMN = "CRNCY_DESC";
	static final String DB_NAME = "payment_system";
	static final String CRNCY_TABLE_NAME = DB_NAME + "." + "currencies";
	static final String SELECT_CRNCY_BY_CODE = MessageFormat.format("select * from {0} where {1} = ?", CRNCY_TABLE_NAME,
			CRNCY_CODE_COLOMN);
	static final String SELECT_ALL_CRNCYS = MessageFormat.format("select * from {0}", CRNCY_TABLE_NAME);

	private DataSource dataSource;
	private QueryRunner queryRunner;

	public MySqlCurrencyGateway(DataSource dataSource) {
		this.dataSource = dataSource;
		this.queryRunner = new QueryRunner(dataSource);
	}

	@Override
	public Iterable<CurrencyDataStructure> loadCurrencies() {
		return executeQueryAndGet(dataSource, SELECT_ALL_CRNCYS, statement -> {
			List<CurrencyDataStructure> currencies = new ArrayList<>();
			ResultSet rs = statement.executeQuery();
			checkEmptyResultSet(rs);
			while (rs.next()) {
				currencies.add(buildCurrency(rs));
			}
			return currencies;
		});
	}

	public CurrencyDataStructure loadCurrencyByCode(String currencyCode) {
		checkCurrnecyCodeLength(currencyCode);
		QueryExecutor<CurrencyDataStructure> executor = rs -> {
			checkCurrencyExists(rs);
			rs.next();
			return buildCurrency(rs);
		};
		return queryRunner.executeSQL(SELECT_CRNCY_BY_CODE, currencyCode).executeQuery(executor);
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

	private void checkCurrencyExists(ResultSet resultSet) throws SQLException {
		if (!resultSet.isBeforeFirst()) {
			throw new CurrencyNotFoundExption();
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

	public static class CurrencyNotFoundExption extends RuntimeException {

		private static final long serialVersionUID = 1L;

	}

	static class NoRowsAffectedException extends RuntimeException {
		private static final long serialVersionUID = 1L;
	}

	static class EmptyResultSetException extends RuntimeException {
		private static final long serialVersionUID = 1L;

	}

	static class ShortCurrencyCodeException extends RuntimeException {
		private static final long serialVersionUID = 1L;
	}
}
