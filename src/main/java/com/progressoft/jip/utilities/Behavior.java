package com.progressoft.jip.utilities;

import javax.sql.DataSource;

public interface Behavior<RETURN_TYPE> {

    void openConnection(DataSource dataSource);

    void registerParameters(Object... parameters);

    RETURN_TYPE doBehavior();

    void closeConnection();

    default RETURN_TYPE execute(DataSource dataSource, Object... parameters) {
	openConnection(dataSource);
	registerParameters(parameters);
	RETURN_TYPE res = doBehavior();
	closeConnection();
	return res;
    }
}