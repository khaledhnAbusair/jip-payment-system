package com.progressoft.jip.gateway.impl;

import static org.junit.Assert.assertEquals;

import java.sql.Date;
import java.sql.SQLException;

import org.apache.commons.dbcp.BasicDataSource;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.progressoft.jip.datastructures.PaymentRequestDataStructure;
import com.progressoft.jip.factory.impl.PaymentRequestBehaviorsFactoryImpl;
import com.progressoft.jip.gateway.PaymentRequestGateway;
import com.progressoft.jip.gateway.exception.AccountDoesNotHavePaymentRequestsException;
import com.progressoft.jip.gateway.exception.EmptyAccountIBANException;
import com.progressoft.jip.gateway.exception.NoneExistingPaymentRequestException;
import com.progressoft.jip.gateway.exception.NullAccountIBANException;
import com.progressoft.jip.gateway.exception.NullDataSourceException;
import com.progressoft.jip.utilities.Constants;
import com.progressoft.jip.utilities.DataBaseSettings;
import com.progressoft.jip.utilities.Utilities;

public class MySQLPaymentRequestGatewayTest {

    private PaymentRequestGateway paymentRequestGatway;
    private BasicDataSource dataSource;

    private static final String INSERT_PAYMENT_REQUEST_SQL_STATEMENT_1 = "insert into " + Constants.DB_NAME + "."
	    + Constants.PAYMENT_REQUEST_TABLE_NAME
	    + " values(null ,'ibanFrom','ibanto1','bname',555.5,'JOD','SALA','2013-09-04');";
    private static final String INSERT_PAYMENT_REQUEST_SQL_STATEMENT_2 = "insert into " + Constants.DB_NAME + "."
	    + Constants.PAYMENT_REQUEST_TABLE_NAME
	    + " values(null ,'ibanFrom1','ibanto2','bname',555.5,'JOD','SALA','2013-09-04');";
    private static final String INSERT_PAYMENT_REQUEST_SQL_STATEMENT_3 = "insert into " + Constants.DB_NAME + "."
	    + Constants.PAYMENT_REQUEST_TABLE_NAME
	    + " values(null ,'ibanFrom1','ibanto2','bname',555.5,'JOD','SALA','2013-09-04');";

    @Before
    public void createMySQLPymentRequestGatway() {
	dataSource = new BasicDataSource();
	DataBaseSettings dbSettings = DataBaseSettings.getInstance();
	dataSource.setUrl(dbSettings.url());
	dataSource.setUsername(dbSettings.username());
	dataSource.setPassword(dbSettings.password());
	dataSource.setDriverClassName(dbSettings.driverClassName());
	paymentRequestGatway = new MySqlPaymentRequestGateway(dataSource, new PaymentRequestBehaviorsFactoryImpl());
	fillPaymentRequestTable(dataSource);
    }

    private void fillPaymentRequestTable(BasicDataSource dataSource) {
	try {
	    Utilities.preparedStatement(dataSource.getConnection(), INSERT_PAYMENT_REQUEST_SQL_STATEMENT_1)
		    .executeUpdate();
	    Utilities.preparedStatement(dataSource.getConnection(), INSERT_PAYMENT_REQUEST_SQL_STATEMENT_2)
		    .executeUpdate();
	    Utilities.preparedStatement(dataSource.getConnection(), INSERT_PAYMENT_REQUEST_SQL_STATEMENT_3)
		    .executeUpdate();
	} catch (SQLException e) {
	    e.printStackTrace();
	}
    }

    @After
    public void after() {
	try {
	    Utilities.preparedStatement(dataSource.getConnection(), Constants.TRUNCATE_PAYMENT_REQUEST_TABLE)
		    .executeUpdate();
	} catch (SQLException e) {
	    e.printStackTrace();
	}
    }

    @Test(expected = NullDataSourceException.class)
    public void creatingPaymentRequestGateway_PassingNullDataSource_ShouldThrowNullDataSource() {
	new MySqlPaymentRequestGateway(null, new PaymentRequestBehaviorsFactoryImpl());
    }

    @Test
    public void givenPaymentRequestGateway_CallingLoadPaymentRequestById_PassingExistingID_ShouldReturnThePaymentRequest() {
	assertEquals(1, paymentRequestGatway.loadPaymentRequestById(1).getId());
    }

    @Test(expected = NoneExistingPaymentRequestException.class)
    public void givenPaymentRequestGateway_CallingLoadPaymentRequestById_PassingNoneExistingID_ShouldThrowNoneExistingPaymentRequest() {
	paymentRequestGatway.loadPaymentRequestById(10).getId();
    }

    @Test(expected = NoneExistingPaymentRequestException.class)
    public void givenMySQLPaymentRequestGateway_CallingDeletePaymentRequestById_PassingNonExistingPaymentRequest_ShouldThrowNoneExistingPaymentRequst() {
	paymentRequestGatway.deletePaymentRequestById(4);
    }

    @Test
    public void givenPaymentRequestGateway_CallingInsertPaymentRequest_ThenCallingLoadPaymentRequestById_ShouldReturnThePaymentRequest() {
	paymentRequestGatway.insertPaymentRequest(new PaymentRequestDataStructure().setBeneficiaryAccountIBAN("ibanTo")
		.setOrderingAccountIBAN("ibanFrom").setBeneficiaryName("whatever").setCurrencyCode("JOD")
		.setPaymentAmount(562.4).setPurposeCode("SALA").setPaymentDate(new Date(2016, 05, 11)));
	paymentRequestGatway.loadPaymentRequestById(3);
    }

    @Test(expected = NullAccountIBANException.class)
    public void givenPaymentRequestGateway_CallingLoadPaymentRequestsByOrderingAccountIBAN_PassingNullIBAN_ShouldThrowNullAccountIBAN() {
	assertEquals(2, paymentRequestGatway.loadPaymentRequestsByOrderingAccountIBAN(null).size());
    }

    @Test(expected = EmptyAccountIBANException.class)
    public void givenPaymentRequestGateway_CallingLoadPaymentRequestsByOrderingAccountIBAN_PassingEmptyIBAN_ShouldThrowEmptyAccountIBAN() {
	assertEquals(2, paymentRequestGatway.loadPaymentRequestsByOrderingAccountIBAN("").size());
    }

    @Test(expected = AccountDoesNotHavePaymentRequestsException.class)
    public void givenPaymentRequestGateway_CallingLoadPaymentRequestsByOrderingAccountIBAN_PassingIBANWithoutRequests_ShouldThrowAccountDoesNotHavePaymentRequestsIBAN() {
	paymentRequestGatway.loadPaymentRequestsByOrderingAccountIBAN("X");
    }

    @Test
    public void givenPaymentRequestGateway_CallingLoadPaymentRequestsByOrderingAccountIBAN_PassingExistingIBAN_ShouldReturnTwoRequests() {
	assertEquals(2, paymentRequestGatway.loadPaymentRequestsByOrderingAccountIBAN("ibanFrom1").size());
    }

    @Test
    public void givenPaymentRequestGateway_CallingLoadPaymentRequests_ShouldReturnAllPaymentRequests() {
	assertEquals(3, paymentRequestGatway.loadPaymentRequests().size());
    }

}
