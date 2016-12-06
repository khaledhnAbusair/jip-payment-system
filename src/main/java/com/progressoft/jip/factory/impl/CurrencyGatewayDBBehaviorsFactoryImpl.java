package com.progressoft.jip.factory.impl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.sql.DataSource;

import com.progressoft.jip.datastructures.CurrencyDataStructure;
import com.progressoft.jip.factory.Behavior;
import com.progressoft.jip.factory.CurrencyGatewayDBBehaviorsFactory;
import com.progressoft.jip.gateway.exception.CurrencyNotFoundExption;
import com.progressoft.jip.gateway.exception.EmptyResultSetException;
import com.progressoft.jip.gateway.exception.ShortCurrencyCodeException;
import com.progressoft.jip.utilities.Constants;
import com.progressoft.jip.utilities.Utilities;

public class CurrencyGatewayDBBehaviorsFactoryImpl implements CurrencyGatewayDBBehaviorsFactory {

    @Override
    public Behavior<Collection<CurrencyDataStructure>> loadCurrencies() {
	return LOAD_CURRENCIES;
    }

    @Override
    public Behavior<CurrencyDataStructure> loadCurrencyByCode() {
	return LOAD_CURRENCY_BY_CODE;
    }
    
    public static final Behavior<Collection<CurrencyDataStructure>> LOAD_CURRENCIES = new Behavior<Collection<CurrencyDataStructure>>() {

	private Connection connection;

	@Override
	public void openConnection(DataSource dataSource) {
	    try {
		connection = dataSource.getConnection();
	    } catch (SQLException e) {
		e.printStackTrace();
	    }
	}

	@Override
	public void registerParameters(Object... parameters) {
	}

	@Override
	public Collection<CurrencyDataStructure> operation() {
	    List<CurrencyDataStructure> currencies = new ArrayList<>();
	    try {
		ResultSet rs = Utilities.preparedStatement(connection, Constants.SELECT_ALL_CRNCYS).executeQuery();
		if (!rs.isBeforeFirst()) {
		    throw new EmptyResultSetException();
		}
		while (rs.next()) {
		    currencies.add(buildCurrency(rs));
		}
		return currencies;
	    } catch (SQLException e) {
		throw new IllegalStateException(e);
	    }
	}

	@Override
	public void closeConnection() {
	    try {
		connection.close();
	    } catch (SQLException e) {
		e.printStackTrace();
	    }
	}
    };

    public static final Behavior<CurrencyDataStructure> LOAD_CURRENCY_BY_CODE = new Behavior<CurrencyDataStructure>() {

	private String code;
	private Connection connection;

	@Override
	public void openConnection(DataSource dataSource) {
	    try {
		connection = dataSource.getConnection();
	    } catch (SQLException e) {
		e.printStackTrace();
	    }
	}

	@Override
	public void registerParameters(Object... parameters) {
	    code = (String) parameters[0];
	}

	@Override
	public CurrencyDataStructure operation() {
	    if (code.length() < 3)
		throw new ShortCurrencyCodeException();
	    ResultSet rs;
	    try {
		rs = Utilities.preparedStatement(connection, Constants.SELECT_CRNCY_BY_CODE, code).executeQuery();
		if (!rs.isBeforeFirst()) {
		    throw new CurrencyNotFoundExption();
		}
		rs.next();
		return buildCurrency(rs);
	    } catch (SQLException e) {
		throw new IllegalStateException(e);
	    }

	}

	@Override
	public void closeConnection() {
	    try {
		connection.close();
	    } catch (SQLException e) {
		e.printStackTrace();
	    }
	}
    };

    private static CurrencyDataStructure buildCurrency(ResultSet rs) {
	try {
	    String currCode = rs.getString(Constants.CRNCY_CODE_COLOMN);
	    String currDescription = rs.getString(Constants.CRNCY_DESC_COLOMN);
	    double currRate = rs.getDouble(Constants.CRNCY_RATE_COLOMN);
	    CurrencyDataStructure currency = new CurrencyDataStructure();
	    currency.setCurrencyCode(currCode);
	    currency.setCurrencyDescription(currDescription);
	    currency.setCurrencyRate(currRate);
	    return currency;
	} catch (SQLException e) {
	    throw new IllegalStateException(e);
	}
    }

}
