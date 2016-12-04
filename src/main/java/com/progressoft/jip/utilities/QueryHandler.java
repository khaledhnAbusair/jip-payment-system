package com.progressoft.jip.utilities;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLClientInfoException;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.sql.DataSource;

public class QueryHandler {
	static Class<? extends Throwable> exception = SQLClientInfoException.class; 

	public interface StatementExecutor<T> {
		T executeStatement(PreparedStatement statement) throws SQLException;
	}

	public static <T> T executeQueryAndGet(DataSource dataSource, String sql,  StatementExecutor<T> executor) {
		try (Connection connection = dataSource.getConnection();
				PreparedStatement statement = connection.prepareStatement(sql)) {
			return executor.executeStatement(statement);
		
		} catch (SQLIntegrityConstraintViolationException e) {
			throw new DuplicateCurrencyCodeException();
		} catch (SQLException e) {
			throw new IllegalStateException(e);
		}

	}

	public static List<Object> iterateThroughResultSet(ResultSet resultSet, String... colomnNames) {
		List<Object> resultSetList = new ArrayList<>();
		try {
			while (resultSet.next()) {
				for (String colomnName : colomnNames) {
					Object object = resultSet.getObject(colomnName);
					resultSetList.add(object);
				}
			}
		} catch (SQLException e) {
			throw new IllegalStateException(e);
		}
		return resultSetList;
	}

	public static void setStatementValues(PreparedStatement statement, Object... paramValues) throws SQLException {
		int i = 0;
		for (Object object : paramValues) {
			statement.setObject(++i, object);
		}
	}
	public static class DuplicateCurrencyCodeException extends RuntimeException {
		private static final long serialVersionUID = 1L;
	}

}
