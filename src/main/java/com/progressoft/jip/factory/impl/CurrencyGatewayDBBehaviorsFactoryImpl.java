package com.progressoft.jip.factory.impl;

import java.sql.SQLException;
import java.util.Collection;
import java.util.Objects;

import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import com.progressoft.jip.datastructures.CurrencyDataStructure;
import com.progressoft.jip.factory.AbstractBehavior;
import com.progressoft.jip.factory.Behavior;
import com.progressoft.jip.factory.CurrencyGatewayDBBehaviorsFactory;
import com.progressoft.jip.gateway.exception.CurrencyNotFoundExption;
import com.progressoft.jip.gateway.exception.ShortCurrencyCodeException;
import com.progressoft.jip.utilities.Constants;

public class CurrencyGatewayDBBehaviorsFactoryImpl implements CurrencyGatewayDBBehaviorsFactory {

    @Override
    public Behavior<Collection<CurrencyDataStructure>> loadCurrencies() {
	return LOAD_CURRENCIES;
    }

    @Override
    public Behavior<CurrencyDataStructure> loadCurrencyByCode() {
	return LOAD_CURRENCY_BY_CODE;
    }

    public static final Behavior<Collection<CurrencyDataStructure>> LOAD_CURRENCIES = new AbstractBehavior<Collection<CurrencyDataStructure>>() {

	@Override
	public Collection<CurrencyDataStructure> operation() {
	    try {
		return runner.query(Constants.SELECT_ALL_CRNCYS, new BeanListHandler<>(CurrencyDataStructure.class));
	    } catch (SQLException e) {
		throw new IllegalStateException(e);
	    }
	}

    };

    public static final Behavior<CurrencyDataStructure> LOAD_CURRENCY_BY_CODE = new AbstractBehavior<CurrencyDataStructure>() {

	@Override
	public CurrencyDataStructure operation() {
	    String code = (String) parameters[0];
	    if (isShortCode(code))
		throw new ShortCurrencyCodeException();
	    return loadCurrency(loadCurrenciesFromDB(code));
	}

	private CurrencyDataStructure loadCurrency(CurrencyDataStructure currency) {
	    if (Objects.isNull(currency))
		throw new CurrencyNotFoundExption();
	    return currency;
	}

	private CurrencyDataStructure loadCurrenciesFromDB(String code) {
	    try {
		return runner.query(Constants.SELECT_CRNCY_BY_CODE, new BeanHandler<>(CurrencyDataStructure.class),
			code);
	    } catch (SQLException e) {
		throw new IllegalStateException(e);
	    }
	}

	private boolean isShortCode(String code) {
	    return code.length() < 3;
	}
    };

}
