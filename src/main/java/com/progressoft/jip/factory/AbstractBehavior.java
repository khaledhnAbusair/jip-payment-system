package com.progressoft.jip.factory;

import javax.sql.DataSource;

import org.apache.commons.dbutils.QueryRunner;

public abstract class AbstractBehavior<T> implements Behavior<T> {

    protected Object[] parameters;
    protected QueryRunner runner;

    @Override
    public void openConnection(DataSource dataSource) {
	runner = new QueryRunner(dataSource);
    }

    @Override
    public void registerParameters(Object... parameters) {
	this.parameters = parameters;
    }

    @Override
    public abstract T operation();

    @Override
    public void closeConnection() {
    }

}