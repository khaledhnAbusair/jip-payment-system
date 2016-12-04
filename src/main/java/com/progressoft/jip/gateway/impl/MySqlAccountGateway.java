package com.progressoft.jip.gateway.impl;

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
import com.progressoft.jip.exception.AccountNotFoundException;
import com.progressoft.jip.exception.NullAccountIBANException;
import com.progressoft.jip.gateway.AccountGateway;
import com.progressoft.jip.utilities.QueryHandler;

public class MySqlAccountGateway implements AccountGateway {

	private static final String GET_ACCOUNT_BY_IBAN = "select IBAN, accountType, balance, status, currencyCode from account where iban = ?";
	private static final String GET_ACCOUNTS = "select IBAN, accountType, balance, status, currencyCode from account";

	private Connection connection;
	private DataSource dataSource;

	public MySqlAccountGateway(DataSource dataSource) {
		this.dataSource = dataSource;
		try {
			this.connection = dataSource.getConnection();
		} catch (SQLException e) {
			throw new IllegalStateException("Failed to get connection from datasource: ", e);
		}
	}

	@Override
	public AccountDatastructure loadAccountByIBAN(String IBAN) {

		if (Objects.isNull(IBAN) || IBAN.trim().length() == 0) {
			throw new NullAccountIBANException();
		}

		String sql = GET_ACCOUNT_BY_IBAN;

		try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
			preparedStatement.setString(1, IBAN);
			ResultSet resultSet = preparedStatement.executeQuery();
			checkEmptyResultSet(resultSet);
			resultSet.next();
			return buildAccountFromDatabase(resultSet);
		} catch (SQLException e) {
			throw new AccountNotFoundException();
		}
	}

	private AccountDatastructure buildAccountFromDatabase(ResultSet resultSet) {
		try {
			String iban = resultSet.getString("IBAN");
			String accountType = resultSet.getString("accountType");
			double balance = resultSet.getDouble("balance");
			String status = resultSet.getString("status");
			String currencyCode = resultSet.getString("currencyCode");

			AccountDatastructure account = new AccountDatastructure();
			account.setIban(iban).setType(accountType).setStatus(Status.fromStatus(status)).setBalance(balance)
					.setCurrencyCode(currencyCode);
			return account;
		} catch (SQLException e) {
			throw new IllegalStateException();
		}
	}

	@Override
	public Collection<AccountDatastructure> loadAccounts() {
		return QueryHandler.executeQueryAndGet(dataSource, GET_ACCOUNTS, statement -> {
			List<AccountDatastructure> accounts = new ArrayList<>();
			ResultSet rs = statement.executeQuery();
			checkEmptyResultSet(rs);
			while (rs.next()) {
				accounts.add(buildAccountFromDatabase(rs));
			}
			return accounts;
		});
	}

	private void checkEmptyResultSet(ResultSet resultSet) {
		try {
			if (!resultSet.isBeforeFirst()) {
				throw new AccountNotFoundException();
			}
		} catch (SQLException e) {
			throw new IllegalStateException();
		}
	}

}
