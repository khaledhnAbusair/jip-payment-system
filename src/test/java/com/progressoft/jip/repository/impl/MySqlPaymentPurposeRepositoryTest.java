package com.progressoft.jip.repository.impl;

import static org.junit.Assert.assertEquals;

import org.apache.commons.dbcp.BasicDataSource;
import org.junit.Before;
import org.junit.Test;

import com.progressoft.jip.exception.EmptyPaymentPurposeCodeException;
import com.progressoft.jip.exception.NoneExistingPaymentPurposeException;
import com.progressoft.jip.exception.NullPaymentPurposeCodeException;
import com.progressoft.jip.exception.NullPaymentPurposeNameException;
import com.progressoft.jip.gateway.impl.MySQLPaymentPurposeGateway;
import com.progressoft.jip.utilities.PaymentPurposeBehaviorsFactoryImpl;

public class MySqlPaymentPurposeRepositoryTest {

    private MySqlPaymentPurposeRepositoryMock paymentPurposeRepository;

    @Before
    public void setup() {
	BasicDataSource dataSource = new BasicDataSource();
	dataSource.setUsername("root");
	dataSource.setPassword("root");
	dataSource.setUrl("jdbc:mysql://localhost:3306/PAYMENT_SYSTEM");
	dataSource.setDriverClassName("com.mysql.jdbc.Driver");
	paymentPurposeRepository = new MySqlPaymentPurposeRepositoryMock(new MySQLPaymentPurposeGateway(dataSource,
		new PaymentPurposeBehaviorsFactoryImpl()));
    }

    @Test
    public void givenPaymentPurposeRepository_CallingLoadPaymentPurposeByCode_PassingExistingCode_ShouldReturnThePaymentPurpose() {
	assertEquals("GHAD", paymentPurposeRepository.loadPaymentPurposeByCode("GHAD").getCode());
    }

    @Test(expected = NullPaymentPurposeCodeException.class)
    public void givenPaymentPurposeRepository_CallingLoadPaymentPurposeByCode_PassingNullCode_ShouldThrowNullPaymentPurposeCode() {
	paymentPurposeRepository.loadPaymentPurposeByCode(null);
    }

    @Test(expected = EmptyPaymentPurposeCodeException.class)
    public void givenPaymentPurposeRepository_CallingLoadPaymentPurposeByCode_PassingEmptyCode_ShouldThrowEmptyPaymentPurposeCode() {
	paymentPurposeRepository.loadPaymentPurposeByCode("");
    }

    @Test
    public void givenPaymentPurposeRepository_CallingLoadPaymentPurposes_ShouldReturnAllPaymentPurposes() {
	assertEquals(9, paymentPurposeRepository.loadPaymentPurposes().size());
    }

    @Test
    public void givenPaymentPurposeRepository_CallingUpdatePaymentPurposeName_PassingExistingPaymentPurposeCode_ThenCallingLoadPaymentPurpose_ShouldUpdatePaymentPurposeName() {
	paymentPurposeRepository.updatePaymenPurposeName("GHAD", "new name");
	assertEquals("new name", paymentPurposeRepository.loadPaymentPurposeByCode("GHAD").getName());
    }

    @Test(expected = NullPaymentPurposeCodeException.class)
    public void givenPaymentPurposeRepository_CallingUpdatePaymentPurposeName_PassingNullPaymentPurposeCode_ShouldThrowNullPaymentPurposeCode() {
	paymentPurposeRepository.updatePaymenPurposeName(null, "new name");
    }

    @Test(expected = EmptyPaymentPurposeCodeException.class)
    public void givenPaymentPurposeRepository_CallingUpdatePaymentPurposeName_PassingEmptyPaymentPurposeCode_ShouldThrowEmptyPaymentPurposeCode() {
	paymentPurposeRepository.updatePaymenPurposeName("", "new name");
    }

    @Test(expected = NullPaymentPurposeNameException.class)
    public void givenPaymentPurposeRepository_CallingUpdatePaymentPurposeName_PassingNullPaymentPurposeName_ShouldThrowNullPaymentPurposeName() {
	paymentPurposeRepository.updatePaymenPurposeName("GHAD", null);
    }

    @Test
    public void givenPaymentPurposeGateway_CallingInsertPaymentPurpose_ShouldInsertPaymentPurpose() {
	paymentPurposeRepository.insertPaymentPurpose("newCode", "newName");
	paymentPurposeRepository.deletePaymentPurpose("newCode");
    }

    @Test(expected = NoneExistingPaymentPurposeException.class)
    public void givenPaymentPurposeRepository_CallingDeletePaymentPurpose_PassingNoneExistingPaymentPurposeCode_ShouldThrowNoneExistingPaymentPurpose() {
	paymentPurposeRepository.deletePaymentPurpose("jjjj");
    }

}
