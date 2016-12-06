package com.progressoft.jip.utilities;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

public class QueryRunner {

    private static Connection connection;
    private PreparedStatement prepareStatement;
    private Class<?> catching;
    private Class<?> throwing;

    private static void close() {
	try {
	    connection.close();
	} catch (SQLException e) {
	}
    }

    public PreparedStatement getPrepareStatement() {
	return prepareStatement;
    }

    public QueryRunner(DataSource dataSource) {
	try {
	    connection = dataSource.getConnection();
	} catch (SQLException e) {
	}
    }

    public SQLExecutor executeSQL(String sql, Object... params) {
	try {
	    prepareStatement = connection.prepareStatement(sql);
	    int i = 0;
	    for (Object param : params) {
		prepareStatement.setObject(++i, param);
	    }
	    return new SQLExecutor();
	} catch (SQLException e) {
	    throw new IllegalStateException();
	}

    }

    public interface QueryExecutor<T> {
	T execute(ResultSet resultSet) throws SQLException;

	default void autoClose() {
	    close();
	};
    }

    public final class SQLExecutor {
	public <T> T executeQuery(QueryExecutor<T> executor) {
	    ResultSet resultSet;
	    try {
		resultSet = prepareStatement.executeQuery();
		return executor.execute(resultSet);
	    } catch (SQLException e) {
		throw checkForException(e);
	    }
	}

	public int executeUpdate(boolean autoClose) {
	    int rowsAffected;
	    try {
		rowsAffected = prepareStatement.executeUpdate();
		if (autoClose)
		    close();
		return rowsAffected;
	    } catch (SQLException e) {
		throw checkForException(e);
	    }

	}

	public SQLExecutor catchThrow(Class<? extends Exception> catching, Class<? extends RuntimeException> throwing) {
	    QueryRunner.this.catching = catching;
	    QueryRunner.this.throwing = throwing;
	    return this;
	}

	private RuntimeException checkForException(SQLException e) {
	    boolean instance = e.getClass().isInstance(newInstance(catching));
	    if (instance)
		return (RuntimeException) newInstance(QueryRunner.this.throwing);
	    return new IllegalStateException(e);
	}

	private Object newInstance(Class<?> type) {
	    try {
		return (Exception) type.newInstance();
	    } catch (InstantiationException e) {
		e.printStackTrace();
	    } catch (IllegalAccessException e) {
		e.printStackTrace();
	    }
	    return null;
	}
    }

}