package com.progressoft.jip.repository.impl;

import static org.junit.Assert.assertEquals;

import java.sql.Date;
import java.sql.SQLException;

import org.apache.commons.dbcp.BasicDataSource;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.progressoft.jip.builder.PaymentRequestDataStructureBuilder;
import com.progressoft.jip.datastructures.PaymentRequestDataStructure;
import com.progressoft.jip.exception.NoneExistingPaymentRequestException;
import com.progressoft.jip.gateway.impl.MySqlPaymentRequestGatway;
import com.progressoft.jip.model.PaymentRequest;
import com.progressoft.jip.repository.PaymentRequestRepository;
import com.progressoft.jip.utilities.Behaviors;
import com.progressoft.jip.utilities.Constants;
import com.progressoft.jip.utilities.Utilities;

public class MySqlPaymentRequestRepositoryTest {

    private BasicDataSource dataSource;
    private PaymentRequestRepository paymentRequestRepository;

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
	paymentRequestRepository = new MySqlPaymentRequestRepository(new MySqlPaymentRequestGatway(dataSource,
		Behaviors.LOAD_PAYMENT_REQUEST_BY_ID, Behaviors.DELETE_PAYMENT_REQUEST_BY_ID,
		Behaviors.INSERT_PAYMENT_REQUEST, Behaviors.LOAD_PAYMENT_REQUESTS));
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

    @Test
    public void givenPaymentRequestRepository_CallingLoadPaymentRequestById_PassingExistingId_ShouldReturnThePaymentRequestModel() {

	PaymentRequest paymentRequest = paymentRequestRepository.loadPaymentRequestById(1);
	PaymentRequestDataStructureBuilder builder = new PaymentRequestDataStructureBuilder() {
	    private int id;
	    private String orderingAccountIBAN;
	    private String beneficiaryAccountIBAN;
	    private String beneficiaryName;
	    private double paymentAmount;
	    private String currencyCode;
	    private String purposeCdoe;
	    private Date paymentDate;

	    @Override
	    public PaymentRequestDataStructureBuilder setPurposeCdoe(String purposeCdoe) {
		this.purposeCdoe = purposeCdoe;
		return this;
	    }

	    @Override
	    public PaymentRequestDataStructureBuilder setPaymentDate(Date paymentDate) {
		this.paymentDate = paymentDate;
		return this;
	    }

	    @Override
	    public PaymentRequestDataStructureBuilder setPaymentAmount(double paymentAmount) {
		this.paymentAmount = paymentAmount;
		return this;
	    }

	    @Override
	    public PaymentRequestDataStructureBuilder setOrderingAccountIBAN(String orderingAccountIBAN) {
		this.orderingAccountIBAN = orderingAccountIBAN;
		return this;
	    }

	    @Override
	    public PaymentRequestDataStructureBuilder setId(int id) {
		this.id = id;
		return this;
	    }

	    @Override
	    public PaymentRequestDataStructureBuilder setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
		return this;
	    }

	    @Override
	    public PaymentRequestDataStructureBuilder setBeneficiaryName(String beneficiaryName) {
		this.beneficiaryName = beneficiaryName;
		return this;
	    }

	    @Override
	    public PaymentRequestDataStructureBuilder setBeneficiaryAccountIBAN(String beneficiaryAccountIBAN) {
		this.beneficiaryAccountIBAN = beneficiaryAccountIBAN;
		return this;
	    }

	    @Override
	    public PaymentRequestDataStructure build() {
		return new PaymentRequestDataStructure().setId(id).setOrderingAccountIBAN(orderingAccountIBAN)
			.setBeneficiaryAccountIBAN(beneficiaryAccountIBAN).setBeneficiaryName(beneficiaryName)
			.setPaymentAmount(paymentAmount).setCurrencyCode(currencyCode).setPurposeCdoe(purposeCdoe)
			.setPaymentDate(paymentDate);
	    }
	};
	paymentRequest.build(builder);
	PaymentRequestDataStructure ds = builder.build();
	assertEquals(1, ds.getId());

    }

    @Test(expected = NoneExistingPaymentRequestException.class)
    public void givenPaymentRequestRepository_CallingLoadPaymentRequestById_PassingNoneExistingId_ShouldThrowNoneExistingPaymentRequest() {
	paymentRequestRepository.loadPaymentRequestById(0);
    }

    @Test
    public void givenPaymentRequestRepository_CallingInsertPaymentRequest_PassingModel_ShouldInsertTheModel() {
	PaymentRequestDataStructure ds = new PaymentRequestDataStructure().setBeneficiaryAccountIBAN("ibanTo")
		.setOrderingAccountIBAN("ibanFrom").setBeneficiaryName("whatever").setCurrencyCode("JOD")
		.setPaymentAmount(562.4).setPurposeCdoe("whatever").setPaymentDate(new Date(2016, 05, 11));
	PaymentRequest paymentRequest = new PaymentRequest(ds);
	paymentRequestRepository.inserPaymentRequest(paymentRequest);
	paymentRequestRepository.loadPaymentRequestById(3);
    }

    @Test
    public void givenPaymentRequestRepository_CallingLoadPaymentRequests_ShouldLoadAllPaymentRequests() {
	assertEquals(2, paymentRequestRepository.loadPaymentRequests().size());
    }

    @Test
    public void givenPaymentRequestRepository_CallingDeletePaymentRequestById_ThenCallingPaymentPurposes_ShouldReternOne() {
	paymentRequestRepository.deletePaymentRequestById(1);
	assertEquals(1, paymentRequestRepository.loadPaymentRequests().size());
    }

}
