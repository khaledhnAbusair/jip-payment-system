package com.progressoft.jip.gateway;

import static org.junit.Assert.assertEquals;

import org.apache.commons.dbcp2.BasicDataSource;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.progressoft.jip.datastructures.PaymentPurposeDataStructure;

public class MySQLPaymentPurposeGatewayTest {

	private MySQLPaymentPurposeGateway mySQLPaymentPurposeGateway;
	private BasicDataSource dataSource;

	@Before
	public void setup() {
		dataSource = new BasicDataSource();
		dataSource.setUrl("jdbc:mysql://localhost:3306/PAYMENT_SYSTEM");
		dataSource.setUsername("root");
		dataSource.setPassword("root");
		dataSource.setDriverClassName("com.mysql.jdbc.Driver");
		mySQLPaymentPurposeGateway = new MySQLPaymentPurposeGateway(dataSource);
	}

	@Test(expected = MySQLPaymentPurposeGateway.NullDataSourceException.class)
	public void createPaymentPurposeGateway_PassingNullDataSource_ShouldThrowNullDataSource() {
		mySQLPaymentPurposeGateway = new MySQLPaymentPurposeGateway(null);
	}

	@Test(expected = MySQLPaymentPurposeGateway.EmptyPaymentPurposeCodeException.class)
	public void givenPaymentPurposeGateway_CallingLoadPaymentPurposeByCode_PassingEmptyCode_ShouldThrowNullPaymentPurposeCode() {
		mySQLPaymentPurposeGateway.loadPaymentPurposeByCode("");
	}

	@Test(expected = MySQLPaymentPurposeGateway.NullPaymentPurposeCodeException.class)
	public void givenPaymentPurposeGateway_CallingLoadPaymentPurposeByCode_PassingNullCode_ShouldThrowNullPaymentPurposeCode() {
		mySQLPaymentPurposeGateway.loadPaymentPurposeByCode(null);
	}

	@Test
	public void givenPaymentPurposeGateway_CallingLoadPaymentPurposeByCode_PassingExistingCode_ShouldReturnPaymentPurpose() {
		assertEquals(mySQLPaymentPurposeGateway.loadPaymentPurposeByCode("SALA").getCode(), "SALA");
	}
	
	@Test(expected = MySQLPaymentPurposeGateway.EmptyResultSetException.class)
	public void givenPaymentPurposeGateway_CallingLoadPaymentPurposeByCode_PassingNoneExistingCode() {
		mySQLPaymentPurposeGateway.loadPaymentPurposeByCode("ggg");
	}

	@Ignore
	@Test
	public void givenPaymentPurposeGateway_CallingInsertPaymentPurpose_PassNewPaymentPurpose_ThenCallingLoadPaymentPurposeByCode_ShouldReturnThePurpose() {
		PaymentPurposeDataStructure paymentPurposeDataStructure = new PaymentPurposeDataStructure("SALA", "SALARY");
		// mySQLPaymentPurposeGateway.insertPaymentPurpose(paymentPurposeDataStructure);
	}

	@Ignore
	@Test
	public void givenPaymentPurposeGateway_CallingLoadPaymentPurposes_ShouldRetunPaymentPurposes() {

	}

}
