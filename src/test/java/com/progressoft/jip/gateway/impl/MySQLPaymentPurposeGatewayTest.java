package com.progressoft.jip.gateway.impl;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.apache.commons.dbcp2.BasicDataSource;
import org.junit.Before;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.progressoft.jip.datastructures.PaymentPurposeDataStructure;
import com.progressoft.jip.gateway.PaymentPurposeGateway;
import com.progressoft.jip.gateway.impl.MySQLPaymentPurposeGateway;
import com.progressoft.jip.gateway.impl.MySQLPaymentPurposeGateway.NullPaymentPurposeCodeException;

public class MySQLPaymentPurposeGatewayTest {

	private PaymentPurposeGateway mySQLPaymentPurposeGateway;
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

	@Test(expected = MySQLPaymentPurposeGateway.PaymentPurposeNotFoundException.class)
	public void givenPaymentPurposeGateway_CallingLoadPaymentPurposeByCode_PassingNoneExistingCode() {
		mySQLPaymentPurposeGateway.loadPaymentPurposeByCode("ggg");
	}

	@Test(expected = MySQLPaymentPurposeGateway.DataExceedingCodeColumnLimitException.class)
	public void givenPaymentPurposeGateway_CallingInsertPaymentPurpose_PassPaymentPurposeExceedingPurposeCodeLengthLimit_ShouldThrowDataExceedingCodeColumnLimit() {
		PaymentPurposeDataStructure paymentPurposeDataStructure = new PaymentPurposeDataStructure("GHAD11111112241274",
				"GHADEER");
		mySQLPaymentPurposeGateway.insertPaymentPurpose(paymentPurposeDataStructure);
	}

	@Test(expected = MySQLPaymentPurposeGateway.DataExceedingNameColumnLimitException.class)
	public void givenPaymentPurposeGateway_CallingInsertPaymentPurpose_PassPaymentPurposeExceedingPurposeNameLengthLimit_ShouldThrowDataExceedingCodeColumnLimit() {
		PaymentPurposeDataStructure paymentPurposeDataStructure = new PaymentPurposeDataStructure("GHAD3",
				"GHADEER124GHADEER124GHADEER124GHADEER124452712");
		mySQLPaymentPurposeGateway.insertPaymentPurpose(paymentPurposeDataStructure);
	}

	@Test(expected = MySQLPaymentPurposeGateway.DuplicatePaymentPurposeCodeException.class)
	public void givenPaymentPurposeGateway_CallingInsertPaymentPurpose_PassPaymentPurposWithExistingCode_ShouldThrowDuplicatePaymentPurposeCode() {
		PaymentPurposeDataStructure paymentPurposeDataStructure = new PaymentPurposeDataStructure("GHAD", "GHADEER");
		mySQLPaymentPurposeGateway.insertPaymentPurpose(paymentPurposeDataStructure);
	}

	@Test
	public void givenPaymentPurposeGateway_CallingLoadPaymentPurposes_ShouldRetunPaymentPurposes() {
		 assertEquals(9,new ArrayList<PaymentPurposeDataStructure>(
					mySQLPaymentPurposeGateway.loadPaymentPurposes()).size());
	}
	
	@Test
	public void givenPaymentPurposeGateway_CallingInsertPaymentPurpose_ThenCallingDeletePurPose_ShouldInsertThePurposeThenDeleteIt() {
		mySQLPaymentPurposeGateway.insertPaymentPurpose(new PaymentPurposeDataStructure("AHM", "AHMAD"));
		mySQLPaymentPurposeGateway.deletePaymentPurposeByCode("AHM");
	}
	
	@Test(expected = MySQLPaymentPurposeGateway.NoneExistingPaymentPurposeException.class)
	public void givenPaymentPurposeGateway_CallingDeletePaymentPurposeByCode_PassingNoneExistingCode_ShouldThrowNoneExistingPaymentPurpose() {
		mySQLPaymentPurposeGateway.deletePaymentPurposeByCode("CDFVD");
	}
	
	@Test(expected = MySQLPaymentPurposeGateway.NullPaymentPurposeCodeException.class)
	public void givenPaymentPurposeGateway_CallingDeletePaymentPurposeByCode_PassingNullCode_ShouldThrowNullPaymentPurposeCode() {
		mySQLPaymentPurposeGateway.deletePaymentPurposeByCode(null);
	}
	
	@Test(expected = MySQLPaymentPurposeGateway.EmptyPaymentPurposeCodeException.class)
	public void givenPaymentPurposeGateway_CallingDeletePaymentPurposeByCode_PassingEmptyCode_ShouldThrowNullPaymentPurposeCode() {
		mySQLPaymentPurposeGateway.deletePaymentPurposeByCode("");
	}
	
	@Test
	public void givenPaymentPurposeGateway_CallingUpdatePurposeName_PassingExistingPurposeCodeWithNewPurposeName_ShouldUpdateName() {
		mySQLPaymentPurposeGateway.updatePaymentPurposeName("GHAD","Ahmadburghol");
		PaymentPurposeDataStructure paymentPurposeDataStructure = mySQLPaymentPurposeGateway.loadPaymentPurposeByCode("GHAD");
		assertEquals("Ahmadburghol", paymentPurposeDataStructure.getName());
	}
	
	@Test(expected = MySQLPaymentPurposeGateway.NoneExistingPaymentPurposeException.class)
	public void givenPaymentPurposeGateway_CallingUpdatePurposeName_PassingNoneExistingPurposeCodeWithNewPurposeName_ShouldThrowNoneExistingPaymentPurpose() {
		mySQLPaymentPurposeGateway.updatePaymentPurposeName("AHM","Ahmadburghol");
	}
	
	@Test(expected = MySQLPaymentPurposeGateway.NullPaymentPurposeCodeException.class)
	public void givenPaymentPurposeGateway_CallingUpdatePurposeName_PassingNullPurposeCodeWithNewPurposeName_ShouldThrowNullPaymentPurposeCode() {
		mySQLPaymentPurposeGateway.updatePaymentPurposeName(null,"Ahmadburghol");
	}
	
	@Test(expected = MySQLPaymentPurposeGateway.EmptyPaymentPurposeCodeException.class)
	public void givenPaymentPurposeGateway_CallingUpdatePurposeName_PassingEmptyPurposeCodeWithNewPurposeName_ShouldThrowEmptyPaymentPurposeCode() {
		mySQLPaymentPurposeGateway.updatePaymentPurposeName("","Ahmadburghol");
	}
	
	@Test(expected = MySQLPaymentPurposeGateway.NullPaymentPurposeNameException.class)
	public void givenPaymentPurposeGateway_CallingUpdatePurposeName_PassingNullPurposeNameWithNewPurposeName_ShouldThrowNullPaymentPurposeName() {
		mySQLPaymentPurposeGateway.updatePaymentPurposeName("AHM",null);
	}

}
