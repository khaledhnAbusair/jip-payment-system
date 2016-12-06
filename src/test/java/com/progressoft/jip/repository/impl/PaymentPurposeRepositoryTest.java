package com.progressoft.jip.repository.impl;

import static org.junit.Assert.assertEquals;

import org.apache.commons.dbcp.BasicDataSource;
import org.junit.Before;
import org.junit.Test;

import com.progressoft.jip.factory.impl.PaymentPurposeBehaviorsFactoryImpl;
import com.progressoft.jip.gateway.exception.EmptyPaymentPurposeCodeException;
import com.progressoft.jip.gateway.exception.NoneExistingPaymentPurposeException;
import com.progressoft.jip.gateway.exception.NullPaymentPurposeCodeException;
import com.progressoft.jip.gateway.exception.NullPaymentPurposeNameException;
import com.progressoft.jip.gateway.impl.MySqlPaymentPurposeGateway;
import com.progressoft.jip.utilities.DataBaseSettings;

public class PaymentPurposeRepositoryTest {

    private PaymentPurposeRepositoryMock paymentPurposeRepository;

    @Before
    public void setup() {
	BasicDataSource dataSource = new BasicDataSource();
	DataBaseSettings dbSettings = DataBaseSettings.getInstance();
	dataSource.setUrl(dbSettings.url());
	dataSource.setUsername(dbSettings.username());
	dataSource.setPassword(dbSettings.password());
	dataSource.setDriverClassName(dbSettings.driverClassName());
	paymentPurposeRepository = new PaymentPurposeRepositoryMock(new MySqlPaymentPurposeGateway(dataSource,
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
	assertEquals(2, paymentPurposeRepository.loadPaymentPurposes().size());
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
	paymentPurposeRepository.insertPaymentPurpose("NEW1", "newName");
	paymentPurposeRepository.deletePaymentPurpose("NEW1");
    }

    @Test(expected = NoneExistingPaymentPurposeException.class)
    public void givenPaymentPurposeRepository_CallingDeletePaymentPurpose_PassingNoneExistingPaymentPurposeCode_ShouldThrowNoneExistingPaymentPurpose() {
	paymentPurposeRepository.deletePaymentPurpose("jjjj");
    }

}
