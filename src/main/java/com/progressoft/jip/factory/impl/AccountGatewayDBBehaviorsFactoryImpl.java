package com.progressoft.jip.factory.impl;

import java.sql.SQLException;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

import org.apache.commons.dbutils.handlers.BeanListHandler;

import com.progressoft.jip.datastructures.AccountDatastructure;
import com.progressoft.jip.factory.AbstractBehavior;
import com.progressoft.jip.factory.AccountGatewayDBBehaviorsFactory;
import com.progressoft.jip.factory.Behavior;
import com.progressoft.jip.gateway.exception.AccountNotFoundException;
import com.progressoft.jip.gateway.exception.NullAccountIBANException;
import com.progressoft.jip.utilities.Constants;

public class AccountGatewayDBBehaviorsFactoryImpl implements AccountGatewayDBBehaviorsFactory {

    @Override
    public Behavior<AccountDatastructure> loadAccountByIBAN() {
	return LOAD_ACCOUNT_BY_IBAN;
    }

    @Override
    public Behavior<Collection<AccountDatastructure>> loadAccounts() {
	return LOAD_ACCOUNTS;
    }

    public final static Behavior<AccountDatastructure> LOAD_ACCOUNT_BY_IBAN = new AbstractBehavior<AccountDatastructure>() {

	@Override
	public AccountDatastructure operation() {
	    String IBAN = (String) parameters[0];
	    if (Objects.isNull(IBAN) || IBAN.trim().length() == 0) {
		throw new NullAccountIBANException();
	    }
	    try {
		List<AccountDatastructure> list = runner.query(Constants.GET_ACCOUNT_BY_IBAN, new BeanListHandler<>(
			AccountDatastructure.class), IBAN);
		if (list.isEmpty())
		    throw new AccountNotFoundException();
		return list.get(0);
	    } catch (SQLException e) {
		throw new IllegalStateException(e);
	    }
	}

    };

    public static final Behavior<Collection<AccountDatastructure>> LOAD_ACCOUNTS = new AbstractBehavior<Collection<AccountDatastructure>>() {

	@Override
	public Collection<AccountDatastructure> operation() {
	    try {
		return runner.query(Constants.GET_ACCOUNTS, new BeanListHandler<>(AccountDatastructure.class));
	    } catch (SQLException e) {
		throw new IllegalStateException(e);
	    }
	}

    };

}
