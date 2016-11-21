package com.progressoft.jip.gateway.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;

import javax.sql.DataSource;

public class QueryHandler {

	private interface DBConnectionHandler<T> {
		T prepareStatement(String sql);
	}

	public interface StatementExecutor<T> {
		T executeStatement(PreparedStatement statement) throws SQLException;
	}

	public static <T> T executeQuery(DataSource dataSource, String sql, StatementExecutor<T> executor) {
		DBConnectionHandler<T> handler = s -> {
			try (Connection connection = dataSource.getConnection();
					PreparedStatement statement = connection.prepareStatement(sql)) {
				return (T) executor.executeStatement(statement);
			} catch (SQLIntegrityConstraintViolationException e) {
				throw new DuplicateCurrencyCodeException();
			} catch (SQLException e) {
				throw new IllegalStateException(e);
			}
		};
		return handler.prepareStatement(sql);
	}

	static class DuplicateCurrencyCodeException extends RuntimeException {

		private static final long serialVersionUID = 1L;
	}

}
