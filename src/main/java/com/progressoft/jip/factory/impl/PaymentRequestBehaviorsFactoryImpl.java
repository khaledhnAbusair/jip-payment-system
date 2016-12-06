package com.progressoft.jip.factory.impl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

import javax.sql.DataSource;

import com.progressoft.jip.datastructures.PaymentRequestDataStructure;
import com.progressoft.jip.factory.Behavior;
import com.progressoft.jip.factory.PaymentRequestBehaviorsFactory;
import com.progressoft.jip.gateway.exception.AccountDoesNotHavePaymentRequestsException;
import com.progressoft.jip.gateway.exception.EmptyAccountIBANException;
import com.progressoft.jip.gateway.exception.NullAccountIBANException;
import com.progressoft.jip.utilities.Constants;
import com.progressoft.jip.utilities.Executers;
import com.progressoft.jip.utilities.Utilities;

public class PaymentRequestBehaviorsFactoryImpl implements PaymentRequestBehaviorsFactory {

    @Override
    public Behavior<PaymentRequestDataStructure> loadPaymentRequestById() {
	return LOAD_PAYMENT_REQUEST_BY_ID;
    }

    @Override
    public Behavior<Void> deletePaymentRequestById() {
	return DELETE_PAYMENT_REQUEST_BY_ID;
    }

    @Override
    public Behavior<Void> insertPaymentRequest() {
	return INSERT_PAYMENT_REQUEST;
    }

    @Override
    public Behavior<Collection<PaymentRequestDataStructure>> loadPaymentRequests() {
	return LOAD_PAYMENT_REQUESTS;
    }

    @Override
    public Behavior<Collection<PaymentRequestDataStructure>> loadPaymentRequestsByOrderingAccIBAN() {
	return LOAD_PAYMENT_REQUESTS_BY_ORDERING_ACCOUNT_IBAN;
    }

    public static final Behavior<PaymentRequestDataStructure> LOAD_PAYMENT_REQUEST_BY_ID = new Behavior<PaymentRequestDataStructure>() {

	private int id;
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
	    id = (int) parameters[0];
	}

	@Override
	public PaymentRequestDataStructure operation() {
	    return Executers.LOAD_PAYMENT_REQUEST_BY_ID.execute(Utilities.preparedStatement(connection,
		    Constants.LOAD_PAYMENT_REQUEST_BY_ID_SQL_STATEMENT, id));
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
    public static final Behavior<Void> DELETE_PAYMENT_REQUEST_BY_ID = new Behavior<Void>() {

	private int id;
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
	    id = (int) parameters[0];
	}

	@Override
	public Void operation() {
	    Executers.DELETE_PAYMENT_REQUEST_BY_ID.execute(Utilities.preparedStatement(connection,
		    Constants.DELETE_PAYMENT_REQUEST_SQL_STATMENT, id));
	    return null;
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
    public static final Behavior<Void> INSERT_PAYMENT_REQUEST = new Behavior<Void>() {

	private Connection connection;
	private PaymentRequestDataStructure dataStructure;

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
	    dataStructure = (PaymentRequestDataStructure) parameters[0];
	}

	@Override
	public Void operation() {
	    try {
		Utilities
			.preparedStatement(connection,
				"insert into PAYMENT_SYSTEM.PAYMENT_REQUEST VALUES (null,?,?,?,?,?,?,?)",
				dataStructure.getOrderingAccountIBAN(), dataStructure.getBeneficiaryAccountIBAN(),
				dataStructure.getBeneficiaryName(), dataStructure.getPaymentAmount(),
				dataStructure.getCurrencyCode(), dataStructure.getPurposeCode(),
				dataStructure.getPaymentDate()).executeUpdate();
	    } catch (SQLException e) {
		e.printStackTrace();
	    }

	    return null;
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

    public static final Behavior<Collection<PaymentRequestDataStructure>> LOAD_PAYMENT_REQUESTS = new Behavior<Collection<PaymentRequestDataStructure>>() {

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
	public Collection<PaymentRequestDataStructure> operation() {
	    List<PaymentRequestDataStructure> paymentRequests = new ArrayList<PaymentRequestDataStructure>();
	    try {
		ResultSet resultSet = Utilities.preparedStatement(connection,
			"select * from PAYMENT_SYSTEM.PAYMENT_REQUEST").executeQuery();
		while (resultSet.next()) {
		    paymentRequests.add(new PaymentRequestDataStructure().setId(resultSet.getInt(1))
			    .setOrderingAccountIBAN(resultSet.getString(2))
			    .setBeneficiaryAccountIBAN(resultSet.getString(3))
			    .setBeneficiaryName(resultSet.getString(4)).setPaymentAmount(resultSet.getDouble(5))
			    .setCurrencyCode(resultSet.getString(6)).setPurposeCode(resultSet.getString(7))
			    .setPaymentDate(resultSet.getDate(8)));
		}
		return new ArrayList<PaymentRequestDataStructure>(paymentRequests);

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

    public static final Behavior<Collection<PaymentRequestDataStructure>> LOAD_PAYMENT_REQUESTS_BY_ORDERING_ACCOUNT_IBAN = new Behavior<Collection<PaymentRequestDataStructure>>() {

	private Connection connection;
	private String IBAN;

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
	    if (Objects.isNull(parameters[0]))
		throw new NullAccountIBANException();
	    IBAN = (String) parameters[0];
	    if (IBAN.isEmpty())
		throw new EmptyAccountIBANException();
	}

	@Override
	public Collection<PaymentRequestDataStructure> operation() {
	    List<PaymentRequestDataStructure> paymentRequests = new ArrayList<PaymentRequestDataStructure>();
	    try {
		ResultSet resultSet = Utilities.preparedStatement(connection,
			"select * from PAYMENT_SYSTEM.PAYMENT_REQUEST where ORD_IBAN = ?", IBAN).executeQuery();
		int setSize = 0;
		while (resultSet.next()) {
		    ++setSize;
		    paymentRequests.add(new PaymentRequestDataStructure().setId(resultSet.getInt(1))
			    .setOrderingAccountIBAN(resultSet.getString(2))
			    .setBeneficiaryAccountIBAN(resultSet.getString(3))
			    .setBeneficiaryName(resultSet.getString(4)).setPaymentAmount(resultSet.getDouble(5))
			    .setCurrencyCode(resultSet.getString(6)).setPurposeCode(resultSet.getString(7))
			    .setPaymentDate(resultSet.getDate(8)));
		}
		if (setSize == 0)
		    throw new AccountDoesNotHavePaymentRequestsException();
		return new ArrayList<PaymentRequestDataStructure>(paymentRequests);

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

}
