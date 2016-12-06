package com.progressoft.jip.gateway.impl;

import java.util.Collection;

import javax.sql.DataSource;

import com.progressoft.jip.datastructures.CurrencyDataStructure;
import com.progressoft.jip.factory.Behavior;
import com.progressoft.jip.factory.CurrencyGatewayDBBehaviorsFactory;
import com.progressoft.jip.gateway.AbstractGateway;
import com.progressoft.jip.gateway.CurrencyGateway;

public class MySqlCurrencyGateway extends AbstractGateway implements CurrencyGateway {

    private Behavior<Collection<CurrencyDataStructure>> loadCurrencies;
    private Behavior<CurrencyDataStructure> loadCurrencyByCode;

    public MySqlCurrencyGateway(DataSource dataSource, CurrencyGatewayDBBehaviorsFactory factory) {
	super(dataSource);
	this.loadCurrencies = factory.loadCurrencies();
	this.loadCurrencyByCode = factory.loadCurrencyByCode();
    }

    @Override
    public Collection<CurrencyDataStructure> loadCurrencies() {
	return loadCurrencies.execute(dataSource);
    }

    public CurrencyDataStructure loadCurrencyByCode(String currencyCode) {
	return loadCurrencyByCode.execute(dataSource, currencyCode);
    }
}
