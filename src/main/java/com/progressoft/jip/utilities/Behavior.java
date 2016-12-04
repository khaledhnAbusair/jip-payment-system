package com.progressoft.jip.utilities;

import javax.sql.DataSource;

public interface Behavior<R> {

    void openConnection(DataSource dataSource);

    void registerParameters(Object... parameters);

    R operation();

    void closeConnection();

    default R execute(DataSource dataSource, Object... parameters) {
	openConnection(dataSource);
	registerParameters(parameters);
	R res = operation();
	closeConnection();
	return res;
    }
}