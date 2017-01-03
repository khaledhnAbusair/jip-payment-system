package com.progressoft.jip.repository.impl;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;

import org.apache.commons.dbcp.BasicDataSource;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.progressoft.jip.datastructures.PaymentRequestDataStructure;
import com.progressoft.jip.datastructures.builder.PaymentRequestDataStructureBuilder;
import com.progressoft.jip.datastructures.builder.impl.PaymentRequestDataStructureBuilderImpl;
import com.progressoft.jip.factory.impl.AccountGatewayDBBehaviorsFactoryImpl;
import com.progressoft.jip.factory.impl.PaymentRequestBehaviorsFactoryImpl;
import com.progressoft.jip.gateway.exception.NoneExistingPaymentRequestException;
import com.progressoft.jip.gateway.impl.MySqlAccountGateway;
import com.progressoft.jip.gateway.impl.MySqlPaymentRequestGateway;
import com.progressoft.jip.model.PaymentRequest;
import com.progressoft.jip.model.exception.AccountRuleViolationException;
import com.progressoft.jip.repository.PaymentRequestRepository;
import com.progressoft.jip.utilities.Constants;
import com.progressoft.jip.utilities.DataBaseSettings;
import com.progressoft.jip.utilities.Utilities;

public class PaymentRequestRepositoryTest {

	private BasicDataSource dataSource;
	private PaymentRequestRepository paymentRequestRepository;
	SimpleDateFormat formatter;

	private static final String INSERT_PAYMENT_REQUEST_SQL_STATEMENT_1 = "insert into " + Constants.DB_NAME + "."
			+ Constants.PAYMENT_REQUEST_TABLE_NAME
			+ " values(null ,'ibanfrom1','ibanto1','bname',555.5,'JOD','SALA','2013-09-04','');";
	private static final String INSERT_PAYMENT_REQUEST_SQL_STATEMENT_2 = "insert into " + Constants.DB_NAME + "."
			+ Constants.PAYMENT_REQUEST_TABLE_NAME
			+ " values(null ,'ibanfrom1','ibanto2','bname',555.5,'JOD','SALA','2013-09-04','');";
	private static final String INSERT_PAYMENT_REQUEST_SQL_STATEMENT_3 = "insert into " + Constants.DB_NAME + "."
			+ Constants.PAYMENT_REQUEST_TABLE_NAME
			+ " values(null ,'ibanfrom2','ibanto2','bname',555.5,'JOD','SALA','2013-09-04','');";

	@Before
	public void createMySQLPymentRequestGatway() {
		formatter = new SimpleDateFormat("yyyy/MM/dd");
		dataSource = new BasicDataSource();
		DataBaseSettings dbSettings = DataBaseSettings.getInstance();
		dataSource.setUrl(dbSettings.url());
		dataSource.setUsername(dbSettings.username());
		dataSource.setPassword(dbSettings.password());
		dataSource.setDriverClassName(dbSettings.driverClassName());
		paymentRequestRepository = new PaymentRequestRepositoryImpl(
				new MySqlPaymentRequestGateway(dataSource, new PaymentRequestBehaviorsFactoryImpl()),
				new AccountRepositoryImpl(
						new MySqlAccountGateway(dataSource, new AccountGatewayDBBehaviorsFactoryImpl())));
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

	@Test
	public void givenPaymentRequestRepository_CallingInsertPaymentRequest_PassingModel_ShouldInsertTheModel()
			throws AccountRuleViolationException {
		PaymentRequestDataStructure req = new PaymentRequestDataStructure();

		req.setBeneficiaryAccountIBAN("FR1420041010050500013M02606");
		req.setOrderingAccountIBAN("JO94CBJO0010000000000131000302");
		req.setBeneficiaryName("whatever");
		req.setCurrencyCode("JOD");
		req.setPaymentAmount(new BigDecimal("562.4"));
		req.setPurposeCode("SALA");
		req.setPaymentDate(Date.valueOf(LocalDate.of(2017, 1, 2)));
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

	@Test(expected = AccountRuleViolationException.class)
	public void givenPaymentRequestRepository_CallingInsertPaymentRequest_PassingExistingAccountWithViolatedRule_ShouldThrowAccountRuleViolationException()
			throws AccountRuleViolationException {

		PaymentRequestDataStructure req = new PaymentRequestDataStructure();

		req.setBeneficiaryAccountIBAN("AZ21NABZ00000000137010001944");
		req.setOrderingAccountIBAN("JO94CBJO0010000000000131000302");
		req.setBeneficiaryName("Never");
		req.setCurrencyCode("JOD");
		req.setPaymentAmount(new BigDecimal("562.4"));
		req.setPurposeCode("SALA");
		req.setPaymentDate(Date.valueOf(LocalDate.of(1994, 5, 10)));
		PaymentRequest paymentRequest = new PaymentRequest(req);
		paymentRequestRepository.inserPaymentRequest(paymentRequest);

	}

	@Test(expected = AccountRuleViolationException.class)
	public void givenPaymentRequestRepository_CallingInsertPaymentRequest_PassingPastDay_ShouldThrowAccountRuleViolationException()
			throws AccountRuleViolationException {

		PaymentRequestDataStructure req = new PaymentRequestDataStructure();

		req.setBeneficiaryAccountIBAN("AZ21NABZ00000000137010001944");
		req.setOrderingAccountIBAN("JO94CBJO0010000000000131000302");
		req.setBeneficiaryName("Never");
		req.setCurrencyCode("JOD");
		req.setPaymentAmount(new BigDecimal("562.4"));
		req.setPurposeCode("SALA");
		req.setPaymentDate(Date.valueOf(LocalDate.of(2016, 12, 5)));
		PaymentRequest paymentRequest = new PaymentRequest(req);
		paymentRequestRepository.inserPaymentRequest(paymentRequest);
	}

	@Test
	public void givenPaymentRequestRepository_CallingInsertPaymentRequest_PaymentAmountShouldBeChequeWritten()
			throws AccountRuleViolationException {

		PaymentRequestDataStructure req = new PaymentRequestDataStructure();
		req.setId(99);
		req.setBeneficiaryAccountIBAN("AZ21NABZ00000000137010001944");
		req.setOrderingAccountIBAN("JO94CBJO0010000000000131000302");
		req.setBeneficiaryName("Never");
		req.setCurrencyCode("JOD");
		req.setPaymentAmount(new BigDecimal("562.4"));
		req.setPurposeCode("SALA");
		req.setPaymentDate(Date.valueOf(LocalDate.of(2017, 1, 2)));
		PaymentRequest paymentRequest = new PaymentRequest(req);
		paymentRequestRepository.inserPaymentRequest(paymentRequest);

		PaymentRequest loadPaymen = paymentRequestRepository.loadPaymentRequestById(4);
		PaymentRequestDataStructureBuilderImpl dataStructureBuilderImpl = new PaymentRequestDataStructureBuilderImpl();
		loadPaymen.build(dataStructureBuilderImpl);
		PaymentRequestDataStructure build = dataStructureBuilderImpl.build();
		System.out.println(build.getAmountInWords());
	}

	@Test
	public void givenPaymentRequestRepository_CallingInsertPaymentRequest_PassingExistingAccountWithRule_ShouldBeInserted()
			throws AccountRuleViolationException {

		PaymentRequestDataStructure req = new PaymentRequestDataStructure();

		req.setBeneficiaryAccountIBAN("AZ21NABZ00000000137010001944");
		req.setOrderingAccountIBAN("JO94CBJO0010000000000131000302");
		req.setBeneficiaryName("Never");
		req.setCurrencyCode("JOD");
		req.setPaymentAmount(new BigDecimal("562.4"));
		req.setPurposeCode("SALA");
		req.setPaymentDate(Date.valueOf(LocalDate.of(2017, 1, 2)));
		PaymentRequest paymentRequest = new PaymentRequest(req);
		paymentRequestRepository.inserPaymentRequest(paymentRequest);

	}

}
