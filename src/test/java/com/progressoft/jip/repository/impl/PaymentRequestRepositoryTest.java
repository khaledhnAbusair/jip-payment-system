package com.progressoft.jip.repository.impl;

import static org.junit.Assert.assertEquals;

import java.sql.Date;
import java.sql.SQLException;

import org.apache.commons.dbcp.BasicDataSource;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.progressoft.jip.datastructures.PaymentRequestDataStructure;
import com.progressoft.jip.datastructures.builder.PaymentRequestDataStructureBuilder;
import com.progressoft.jip.datastructures.builder.impl.PaymentRequestDataStructureBuilderImpl;
import com.progressoft.jip.factory.impl.PaymentRequestBehaviorsFactoryImpl;
import com.progressoft.jip.gateway.exception.NoneExistingPaymentRequestException;
import com.progressoft.jip.gateway.impl.MySqlPaymentRequestGateway;
import com.progressoft.jip.model.PaymentRequest;
import com.progressoft.jip.repository.PaymentRequestRepository;
import com.progressoft.jip.utilities.Constants;
import com.progressoft.jip.utilities.DataBaseSettings;
import com.progressoft.jip.utilities.Utilities;

public class PaymentRequestRepositoryTest {

	private BasicDataSource dataSource;
	private PaymentRequestRepository paymentRequestRepository;

	private static final String INSERT_PAYMENT_REQUEST_SQL_STATEMENT_1 = "insert into " + Constants.DB_NAME + "."
			+ Constants.PAYMENT_REQUEST_TABLE_NAME
			+ " values(null ,'ibanfrom1','ibanto1','bname',555.5,'JOD','SALA','2013-09-04');";
	private static final String INSERT_PAYMENT_REQUEST_SQL_STATEMENT_2 = "insert into " + Constants.DB_NAME + "."
			+ Constants.PAYMENT_REQUEST_TABLE_NAME
			+ " values(null ,'ibanfrom1','ibanto2','bname',555.5,'JOD','SALA','2013-09-04');";
	private static final String INSERT_PAYMENT_REQUEST_SQL_STATEMENT_3 = "insert into " + Constants.DB_NAME + "."
			+ Constants.PAYMENT_REQUEST_TABLE_NAME
			+ " values(null ,'ibanfrom2','ibanto2','bname',555.5,'JOD','SALA','2013-09-04');";

	@Before
	public void createMySQLPymentRequestGatway() {
		dataSource = new BasicDataSource();
		DataBaseSettings dbSettings = DataBaseSettings.getInstance();
		dataSource.setUrl(dbSettings.url());
		dataSource.setUsername(dbSettings.username());
		dataSource.setPassword(dbSettings.password());
		dataSource.setDriverClassName(dbSettings.driverClassName());
		paymentRequestRepository = new PaymentRequestRepositoryImpl(
				new MySqlPaymentRequestGateway(dataSource, new PaymentRequestBehaviorsFactoryImpl()));
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
			throw new IllegalStateException(e);
		}
	}

	@After
	public void after() {
		try {
			Utilities.preparedStatement(dataSource.getConnection(), Constants.TRUNCATE_PAYMENT_REQUEST_TABLE)
					.executeUpdate();
		} catch (SQLException e) {
			throw new IllegalStateException(e);
		}
	}

	@Test
	public void givenPaymentRequestRepository_CallingLoadPaymentRequestById_PassingExistingId_ShouldReturnThePaymentRequestModel() {

		PaymentRequest paymentRequest = paymentRequestRepository.loadPaymentRequestById(1);
		PaymentRequestDataStructureBuilder builder = new PaymentRequestDataStructureBuilderImpl();
		paymentRequest.build(builder);
		PaymentRequestDataStructure ds = builder.build();
		assertEquals(1, ds.getId());

	}

	@Test(expected = NoneExistingPaymentRequestException.class)
	public void givenPaymentRequestRepository_CallingLoadPaymentRequestById_PassingNoneExistingId_ShouldThrowNoneExistingPaymentRequest() {
		paymentRequestRepository.loadPaymentRequestById(0);
	}

	@SuppressWarnings("deprecation")
	@Test
	public void givenPaymentRequestRepository_CallingInsertPaymentRequest_PassingModel_ShouldInsertTheModel() {
		PaymentRequestDataStructure req = new PaymentRequestDataStructure();
		req.setBeneficiaryAccountIBAN("JO94CBJO0010000000000131000302");
		req.setOrderingAccountIBAN("ibanFrom");
		req.setBeneficiaryName("whatever");
		req.setCurrencyCode("JOD");
		req.setPaymentAmount(562.4);
		req.setPurposeCode("SALA");
		req.setPaymentDate(new Date(2016, 05, 11));
		PaymentRequest paymentRequest = new PaymentRequest(req);
		paymentRequestRepository.inserPaymentRequest(paymentRequest);
		paymentRequestRepository.loadPaymentRequestById(4);
	}

	@Test
	public void givenPaymentRequestRepository_CallingLoadPaymentRequests_ShouldLoadAllPaymentRequests() {
		assertEquals(3, paymentRequestRepository.loadPaymentRequests().size());
	}

	@Test
	public void givenPaymentRequestRepository_CallingLoadPaymentRequestsByOrderingAccountIBAN_PassingExistingIBAN_ShouldReturnTwoRequests() {
		assertEquals(2, paymentRequestRepository.loadPaymentRequestsByOrderingAccountIBAN("ibanFrom1").size());
	}

	@Test
	public void givenPaymentRequestRepository_CallingDeletePaymentRequestById_ThenCallingPaymentPurposes_ShouldReternOne() {
		paymentRequestRepository.deletePaymentRequestById(1);
		assertEquals(2, paymentRequestRepository.loadPaymentRequests().size());
	}

}
