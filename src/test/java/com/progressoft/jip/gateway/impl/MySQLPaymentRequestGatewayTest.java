package com.progressoft.jip.gateway.impl;

import static org.junit.Assert.assertEquals;

import java.sql.Date;
import java.sql.SQLException;

import org.apache.commons.dbcp.BasicDataSource;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.progressoft.jip.datastructures.PaymentRequestDataStructure;
import com.progressoft.jip.exception.NoneExistingPaymentRequestException;
import com.progressoft.jip.exception.NullDataSourceException;
import com.progressoft.jip.gateway.PaymentRequestGateway;
import com.progressoft.jip.utilities.Behaviors;
import com.progressoft.jip.utilities.Constants;
import com.progressoft.jip.utilities.Utilities;

public class MySQLPaymentRequestGatewayTest {

    private PaymentRequestGateway paymentRequestGatway;
    private BasicDataSource dataSource;

    private static final String INSERT_PAYMENT_REQUEST_SQL_STATEMENT_1 = "insert into " + Constants.DB_NAME + "."
	    + Constants.PAYMENT_REQUEST_TABLE_NAME
	    + " values(null ,'ibanfrom1','ibanto1','bname',555.5,'JOD','GHAD','2013-09-04');";
    private static final String INSERT_PAYMENT_REQUEST_SQL_STATEMENT_2 = "insert into " + Constants.DB_NAME + "."
	    + Constants.PAYMENT_REQUEST_TABLE_NAME
	    + " values(null ,'ibanfrom2','ibanto2','bname',555.5,'JOD','GHAD','2013-09-04');";

    @Before
    public void createMySQLPymentRequestGatway() {
	dataSource = new BasicDataSource();
	dataSource.setUrl("jdbc:mysql://localhost:3306/PAYMENT_SYSTEM");
	dataSource.setUsername("root");
	dataSource.setPassword("root");
	dataSource.setDriverClassName("com.mysql.jdbc.Driver");
	paymentRequestGatway = new MySqlPaymentRequestGatway(dataSource, Behaviors.LOAD_PAYMENT_REQUEST_BY_ID,
		Behaviors.DELETE_PAYMENT_REQUEST_BY_ID, Behaviors.INSERT_PAYMENT_REQUEST,Behaviors.LOAD_PAYMENT_REQUESTS);
	fillPaymentRequestTable(dataSource);
    }

    private void fillPaymentRequestTable(BasicDataSource dataSource) {
	try {
	    Utilities.preparedStatement(dataSource.getConnection(), INSERT_PAYMENT_REQUEST_SQL_STATEMENT_1)
		    .executeUpdate();
	    Utilities.preparedStatement(dataSource.getConnection(), INSERT_PAYMENT_REQUEST_SQL_STATEMENT_2)
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
	new MySqlPaymentRequestGatway(null, Behaviors.LOAD_PAYMENT_REQUEST_BY_ID,
		Behaviors.DELETE_PAYMENT_REQUEST_BY_ID, Behaviors.INSERT_PAYMENT_REQUEST,Behaviors.LOAD_PAYMENT_REQUESTS);
    }

    @Test
    public void givenPaymentRequestGateway_CallingLoadPaymentRequestById_PassingExistingID_ShouldReturnThePaymentRequest() {
	assertEquals(1, paymentRequestGatway.loadPaymentRequestById(1).getId());
    }

    @Test(expected = NoneExistingPaymentRequestException.class)
    public void givenPaymentRequestGateway_CallingLoadPaymentRequestById_PassingNoneExistingID_ShouldThrowNoneExistingPaymentRequest() {
	paymentRequestGatway.loadPaymentRequestById(3).getId();
    }

    @Test(expected = NoneExistingPaymentRequestException.class)
    public void givenMySQLPaymentRequestGateway_CallingDeletePaymentRequestById_PassingNonExistingPaymentRequest_ShouldThrowNoneExistingPaymentRequst() {
	paymentRequestGatway.deletePaymentRequestById(3);
    }

    @Test
    public void givenPaymentRequestGateway_CallingInsertPaymentRequest_ThenCallingLoadPaymentRequestById_ShouldReturnThePaymentRequest() {
	paymentRequestGatway.insertPaymentRequest(new PaymentRequestDataStructure().setBeneficiaryAccountIBAN("ibanTo")
		.setOrderingAccountIBAN("ibanFrom").setBeneficiaryName("whatever").setCurrencyCode("JOD")
		.setPaymentAmount(562.4).setPurposeCdoe("whatever").setPaymentDate(new Date(2016, 05, 11)));
	paymentRequestGatway.loadPaymentRequestById(3);
    }

    @Test
    public void givenPaymentRequestGateway_CallingLoadPaymentRequests_ShouldReturnAllPaymentRequests() {
	assertEquals(2, paymentRequestGatway.loadPaymentRequests().size());
    }
    
}
