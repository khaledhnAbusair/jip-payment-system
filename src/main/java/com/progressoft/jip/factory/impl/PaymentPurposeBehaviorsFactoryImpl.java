package com.progressoft.jip.factory.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.Collection;

import javax.sql.DataSource;

import com.progressoft.jip.datastructures.PaymentPurposeDataStructure;
import com.progressoft.jip.factory.Behavior;
import com.progressoft.jip.factory.PaymentPurposeBehaviorsFactory;
import com.progressoft.jip.gateway.exception.DuplicatePaymentPurposeCodeException;
import com.progressoft.jip.utilities.Constants;
import com.progressoft.jip.utilities.Executers;
import com.progressoft.jip.utilities.Utilities;
import com.progressoft.jip.utilities.Validators;

public class PaymentPurposeBehaviorsFactoryImpl implements PaymentPurposeBehaviorsFactory {

    @Override
    public Behavior<PaymentPurposeDataStructure> loadPaymentPurposeByCodeBehavior() {
	return LOAD_PAYMENT_PURPOSE_BY_CODE;
    }

    @Override
    public Behavior<Void> insertPaymentPurposeBehavior() {
	return INSERT_PAYMENT_RURPOSE_BEHAVIOR;
    }

    @Override
    public Behavior<Collection<PaymentPurposeDataStructure>> loadPaymentPurposesBehavior() {
	return LOAD_PAYMENT_PURPOSES;
    }

    @Override
    public Behavior<Void> deletePaymentPurposeByCodeBehavior() {
	return DELETE_PAYMENT_PURPOSE_BY_CODE;
    }

    @Override
    public Behavior<Void> updatePaymentPurposeNameBehavior() {
	return UPDATE_PAYMENT_PURPOSE_NAME;
    }

    public static final Behavior<PaymentPurposeDataStructure> LOAD_PAYMENT_PURPOSE_BY_CODE = new Behavior<PaymentPurposeDataStructure>() {

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
	public PaymentPurposeDataStructure operation() {
	    Validators.CODE_VALIDATORS.stream().forEach(validator -> validator.validate(code));
	    return Executers.LOAD_PAYMENT_PURPOSE_FROM_DB_BY_CODE.execute(Utilities.preparedStatement(connection,
		    Constants.LOAD_PAYMENT_PURPOSE_BY_CODE_SQL_STATEMENT, code));
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

    public static final Behavior<Void> INSERT_PAYMENT_RURPOSE_BEHAVIOR = new Behavior<Void>() {

	private PaymentPurposeDataStructure paymentPurposeDataStructure;
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
	    paymentPurposeDataStructure = (PaymentPurposeDataStructure) parameters[0];
	}

	@Override
	public Void operation() {
	    Validators.CODE_LENGTH_VALIDATOR.validate(paymentPurposeDataStructure.getCode());
	    Validators.NAME_LENGTH_VALIDATOR.validate(paymentPurposeDataStructure.getName());
	    try {
		Utilities.preparedStatement(connection, Constants.INSERT_PAYMENT_PURPOSE_SQL_STATEMENT,
			paymentPurposeDataStructure.getCode(), paymentPurposeDataStructure.getName()).executeUpdate();
	    } catch (SQLIntegrityConstraintViolationException e) {
		throw new DuplicatePaymentPurposeCodeException();
	    } catch (SQLException e) {
		throw new IllegalStateException(e);
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

    public static final Behavior<Collection<PaymentPurposeDataStructure>> LOAD_PAYMENT_PURPOSES = new Behavior<Collection<PaymentPurposeDataStructure>>() {

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
	public Collection<PaymentPurposeDataStructure> operation() {
	    return Executers.LOAD_PAYMENT_PURPOSES.execute(Utilities.preparedStatement(connection,
		    Constants.LOAD_PAYMENT_PURPOSES_SQL_STATEMENT));
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

    public static final Behavior<Void> DELETE_PAYMENT_PURPOSE_BY_CODE = new Behavior<Void>() {

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
	public Void operation() {
	    Validators.CODE_VALIDATORS.stream().forEach(validator -> validator.validate(code));
	    Executers.DELETE_PAYMENT_PURPOSE_BY_CODE.execute(Utilities.preparedStatement(connection,
		    Constants.DELETE_PAYMENT_PURPOSE_SQL_STATEMENT, code));
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

    public static final Behavior<Void> UPDATE_PAYMENT_PURPOSE_NAME = new Behavior<Void>() {

	private String code;
	private String newName;
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
	    newName = (String) parameters[1];
	}

	@Override
	public Void operation() {
	    Validators.CODE_VALIDATORS.stream().forEach(validator -> validator.validate(code));
	    Validators.NULL_NAME_VALIDATOR.validate(newName);
	    Executers.UPDATE_PAYMENT_PURPOSE_BY_NAME.execute(Utilities.preparedStatement(connection,
		    Constants.UPDATE_PAYMENT_PURPOSE_SQL_STATEMENT, newName, code));
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

}
