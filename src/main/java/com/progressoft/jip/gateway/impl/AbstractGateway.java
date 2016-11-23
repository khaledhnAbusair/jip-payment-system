package com.progressoft.jip.gateway.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Objects;

import javax.sql.DataSource;

import com.progressoft.jip.exception.NullDataSourceException;

public abstract class AbstractGateway {
    protected DataSource dataSource;
    protected Connection connection;

    public AbstractGateway(DataSource dataSource) {
	if (Objects.isNull(dataSource))
	    throw new NullDataSourceException();
	this.dataSource = dataSource;
	establishConnection(dataSource);
    }

    private void establishConnection(DataSource dataSource) {
	try {
	    this.connection = dataSource.getConnection();
	} catch (SQLException e) {
	    throw new IllegalStateException("Can't establish PaymentPurposeGateway connection");
	}
    }

}
