package com.progressoft.jip.factory.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

import javax.sql.DataSource;

import com.progressoft.jip.datastructures.AccountDatastructure;
import com.progressoft.jip.datastructures.AccountDatastructure.Status;
import com.progressoft.jip.factory.AccountGatewayDBBehaviorsFactory;
import com.progressoft.jip.factory.Behavior;
import com.progressoft.jip.gateway.exception.AccountNotFoundException;
import com.progressoft.jip.gateway.exception.NullAccountIBANException;
import com.progressoft.jip.utilities.Constants;
import com.progressoft.jip.utilities.Utilities;

public class AccountGatewayDBBehaviorsFactoryImpl implements AccountGatewayDBBehaviorsFactory {

    @Override
    public Behavior<AccountDatastructure> loadAccountByIBAN() {
	return LOAD_ACCOUNT_BY_IBAN;
    }

    @Override
    public Behavior<Collection<AccountDatastructure>> loadAccounts() {
	return LOAD_ACCOUNTS;
    }

    public final static Behavior<AccountDatastructure> LOAD_ACCOUNT_BY_IBAN = new Behavior<AccountDatastructure>() {

	private String IBAN;
	private Connection connection;

	@Override
	public void openConnection(DataSource dataSource) {
	    try {
		connection = dataSource.getConnection();
	    } catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	    }
	}

	@Override
	public void registerParameters(Object... parameters) {
	    IBAN = (String) parameters[0];
	}

	@Override
	public AccountDatastructure operation() {
	    if (Objects.isNull(IBAN) || IBAN.trim().length() == 0) {
		throw new NullAccountIBANException();
	    }

	    String sql = Constants.GET_ACCOUNT_BY_IBAN;

	    try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
		preparedStatement.setString(1, IBAN);
		ResultSet resultSet = preparedStatement.executeQuery();
		if (!resultSet.next())
		    throw new AccountNotFoundException();
		return buildAccountFromDatabase(resultSet);
	    } catch (SQLException e) {
		e.printStackTrace();
		throw new IllegalStateException();
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

    public static final Behavior<Collection<AccountDatastructure>> LOAD_ACCOUNTS = new Behavior<Collection<AccountDatastructure>>() {

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
	public Collection<AccountDatastructure> operation() {
	    List<AccountDatastructure> accounts = new ArrayList<>();
	    try {
		ResultSet rs = Utilities.preparedStatement(connection, Constants.GET_ACCOUNTS).executeQuery();
		if (!rs.isBeforeFirst()) {
		    throw new AccountNotFoundException();
		}
		while (rs.next()) {
		    accounts.add(buildAccountFromDatabase(rs));
		}
		return accounts;
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

    private static AccountDatastructure buildAccountFromDatabase(ResultSet resultSet) {
	try {
	    String iban = resultSet.getString("IBAN");
	    String accountType = resultSet.getString("TYPE");
	    double balance = resultSet.getDouble("BALANCE");
	    String status = resultSet.getString("STATUS");
	    String currencyCode = resultSet.getString("CURRENCY_CODE");

	    AccountDatastructure account = new AccountDatastructure();
	    account.setIban(iban).setType(accountType).setStatus(Status.fromStatus(status)).setBalance(balance)
		    .setCurrencyCode(currencyCode);
	    return account;
	} catch (SQLException e) {
	    throw new IllegalStateException();
	}
    }

}
