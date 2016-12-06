package com.progressoft.jip.gateway;

import java.util.Objects;

import javax.sql.DataSource;

import com.progressoft.jip.gateway.exception.NullDataSourceException;

public abstract class AbstractGateway {
    protected DataSource dataSource;

    public AbstractGateway(DataSource dataSource) {
	if (Objects.isNull(dataSource))
	    throw new NullDataSourceException();
	this.dataSource = dataSource;
    }
}
