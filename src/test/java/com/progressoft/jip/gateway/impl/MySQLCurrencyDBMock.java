package com.progressoft.jip.gateway.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.commons.dbcp2.BasicDataSource;

import com.progressoft.jip.utilities.Constants;

public class MySQLCurrencyDBMock {

	public static final String UPDATED_CRNCY_CODE = "UPD";
	public static final String DELETED_CRNCY_CODE = "DEL";

	public static void setUpDBEnviroment(BasicDataSource dataSource) {
		try (Connection connection = dataSource.getConnection()) {

			insertCurrencyIntoTable(connection, DELETED_CRNCY_CODE, "Delete Currency", 0.2);
			insertCurrencyIntoTable(connection, UPDATED_CRNCY_CODE, "Update Currency", 0.4);

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void tearDownDBEnviroment(BasicDataSource dataSource) {
		try (Connection connection = dataSource.getConnection()) {
			PreparedStatement statement = connection
					.prepareStatement("delete from " + Constants.CRNCY_TABLE_NAME + " where CODE = 'UPD'");
			statement.executeUpdate();
			PreparedStatement statement2 = connection
					.prepareStatement("delete from " + Constants.CRNCY_TABLE_NAME + " where CODE = 'DEL'");
			statement2.executeUpdate();

		} catch (SQLException e) {
			throw new IllegalStateException(e);
		}
	}

	private static void insertCurrencyIntoTable(Connection connection, String code, String name, double rate)
			throws SQLException {
		String sql = "INSERT INTO " + Constants.CRNCY_TABLE_NAME + " VALUES(?,?,?)";
		try (PreparedStatement statement = connection.prepareStatement(sql)) {
			statement.setString(1, code);
			statement.setString(2, name);
			statement.setDouble(3, rate);
			statement.executeUpdate();
		}
	}

	public static double selectUpdatedCurrencyRateValue(BasicDataSource dataSource) {
		try {
			ResultSet resultSet = selectCurrencyByCode(dataSource, UPDATED_CRNCY_CODE);
			resultSet.next();
			return resultSet.getDouble(Constants.CRNCY_RATE_COLOMN);
		} catch (SQLException e) {
			throw new IllegalStateException(e);
		}
	}

	public static boolean isCurrencyRecordAvilable(BasicDataSource dataSource, String code) {
		try {
			ResultSet resultSet = selectCurrencyByCode(dataSource, code);
			return resultSet.isBeforeFirst();
		} catch (SQLException e) {
			throw new IllegalStateException(e);
		}
	}

	private static ResultSet selectCurrencyByCode(BasicDataSource dataSource, String code) throws SQLException {
		String sql = "SELECT * FROM " + Constants.CRNCY_TABLE_NAME + " WHERE " + Constants.CRNCY_CODE_COLOMN + " = ?";
		Connection connection = dataSource.getConnection();
		PreparedStatement statement = connection.prepareStatement(sql);
		statement.setString(1, code);
		return statement.executeQuery();

	}

}
