package com.progressoft.jip.gateway;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.sql.DataSource;

import com.progressoft.jip.datastructures.Currency;

public class CurrencyGatewaySQL implements CurrencyGateway {
	private static final String CRNCY_CODE_COLOMN = "CRNCY_CODE";
	private static final String CRNCY_RATE_COLOMN = "CRNCY_RATE";
	private static final String CRNCY_DESC_COLOMN = "CRNCY_DESC";
	private static final String DB_NAME = "payment_system";
	private static final String CRNCY_TABLE_NAME = DB_NAME + "." + "currencies";

	//private List<Currency> currencies = new ArrayList<>();
	private DataSource dataSource;

	public CurrencyGatewaySQL(DataSource dataSource) {
		this.dataSource = dataSource;
		
	}

	@Override
	public Iterable<Currency> loadCurrencies() {
		
		try (Connection connection = dataSource.getConnection()) {
			return populateCurrenciesList(connection);
		} catch (SQLException e) {
			throw new IllegalStateException(e);
		}
	}

	private List<Currency> populateCurrenciesList(Connection connection) {
		String sql = "select * from " + CRNCY_TABLE_NAME;
		List<Currency> currencies = new ArrayList<>();
		try (PreparedStatement statement = connection.prepareStatement(sql)) {
			ResultSet rs = statement.executeQuery();
			checkEmptyResultSet(rs);
			while (rs.next()) { 
				currencies.add(buildCurrency(rs));
			}
			return currencies;
		} catch (SQLException e) {
			throw new IllegalArgumentException(e);
		}
	}

	private Currency buildCurrency(ResultSet rs) throws SQLException {
		String currCode = rs.getString(CRNCY_CODE_COLOMN);
		String currDescription = rs.getString(CRNCY_DESC_COLOMN);
		double currRate = rs.getDouble(CRNCY_RATE_COLOMN);

		Currency currency = new Currency();
		currency.setCurrencyCode(currCode);
		currency.setCurrencyDescription(currDescription);
		currency.setCurrencyRate(currRate);
		return currency;
	}

	public Currency getCurrencyByCode(String currencyCode) {
		checkCurrnecyCodeLength(currencyCode);

		String sql = "select * from " + CRNCY_TABLE_NAME + " where " + CRNCY_CODE_COLOMN + " = ?";
		try (Connection connection = dataSource.getConnection();
				PreparedStatement statement = connection.prepareStatement(sql)) {
			statement.setString(1, currencyCode);
			ResultSet rs = statement.executeQuery();
			checkEmptyResultSet(rs);
			rs.next();
			return buildCurrency(rs);

		} catch (SQLException e) {
			throw new IllegalStateException(e);
		}

	}

	@Override
	public void updateCurrencyRateByCode(String currencyCode, double currencyRate) {
		checkCurrnecyCodeLength(currencyCode);

		String sql = "update " + CRNCY_TABLE_NAME + " set " + CRNCY_RATE_COLOMN + "=? where " + CRNCY_CODE_COLOMN
				+ "=?";
		try (Connection connection = dataSource.getConnection();
				PreparedStatement statement = connection.prepareStatement(sql)) {
			statement.setDouble(1, currencyRate);
			statement.setString(2, currencyCode);
			statement.executeUpdate();
		} catch (SQLException e) {
			throw new IllegalStateException(e);

		}
	}

	@Override
	public void createCurrency(Currency currency) {
		String sql = "insert into " + CRNCY_TABLE_NAME + " (" + CRNCY_CODE_COLOMN + "," + CRNCY_RATE_COLOMN + ","
				+ CRNCY_DESC_COLOMN + ") values(?,?,?)";
		try (Connection connection = dataSource.getConnection();
				PreparedStatement statement = connection.prepareStatement(sql)) {
			statement.setString(1, currency.getCurrencyCode());
			statement.setDouble(2, currency.getCurrencyRate());
			statement.setString(3, currency.getCurrencyDescription());
			statement.executeUpdate();
		} catch (SQLIntegrityConstraintViolationException e) {
			throw new DuplicateCurrencyCodeException();
		} catch (SQLException e) {
			throw new IllegalStateException(e);
		}

	}

	@Override
	public void deleteCurrency(Currency currency) {
		String sql = "DELETE FROM " + CRNCY_TABLE_NAME + " WHERE " + CRNCY_CODE_COLOMN + "= ? AND " + CRNCY_DESC_COLOMN
				+ "=?";
		try (Connection connection = dataSource.getConnection();
				PreparedStatement statement = connection.prepareStatement(sql)) {
			statement.setString(1, currency.getCurrencyCode());
			statement.setString(2, currency.getCurrencyDescription());
			statement.executeUpdate();
		} catch (SQLException e) {
			throw new IllegalStateException(e);
		}
	}

	private void checkEmptyResultSet(ResultSet rs) throws SQLException{
		if(!rs.isBeforeFirst()){
			throw new EmptyResultSetException();
		}
	}

	private void checkCurrnecyCodeLength(String currencyCode) {
		if (currencyCode.length() < 3)
			throw new ShortCurrencyCodeException();
	}

	static class DuplicateCurrencyCodeException extends RuntimeException {

		private static final long serialVersionUID = 1L;
	}

	static class EmptyResultSetException extends RuntimeException {

		private static final long serialVersionUID = 1L;
	}

	static class ShortCurrencyCodeException extends RuntimeException {
		private static final long serialVersionUID = 1L;
	}
}
