package com.progressoft.jip.utilities;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.sql.DataSource;

import com.progressoft.jip.datastructures.PaymentPurposeDataStructure;
import com.progressoft.jip.datastructures.PaymentRequestDataStructure;
import com.progressoft.jip.exception.DuplicatePaymentPurposeCodeException;

public class Behaviors {
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
			    .setCurrencyCode(resultSet.getString(6)).setPurposeCdoe(resultSet.getString(7))
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
}
