package com.progressoft.jip.report;

import java.io.PrintStream;
import java.util.Objects;

import org.apache.commons.dbcp.BasicDataSource;

import com.progressoft.jip.factory.impl.PaymentRequestBehaviorsFactoryImpl;
import com.progressoft.jip.gateway.impl.MySqlPaymentRequestGateway;
import com.progressoft.jip.repository.PaymentRequestRepository;
import com.progressoft.jip.repository.impl.PaymentRequestRepositoryImpl;
import com.progressoft.jip.utilities.DataBaseSettings;

public class ReportProvider {

    private static PaymentRequestRepository paymentRequestRepository;

    private ReportProvider() {
    }

    public static void print(String orderingAccountIBAN, ReportWriter reportWriter, PrintStream writer) {
	if (Objects.isNull(paymentRequestRepository))
	    initRepository();
	reportWriter.write(orderingAccountIBAN,
		paymentRequestRepository.loadPaymentRequestsByOrderingAccountIBAN(orderingAccountIBAN), writer);
    }

    private static synchronized void initRepository() {
	if (Objects.isNull(paymentRequestRepository)) {
	    paymentRequestRepository = new PaymentRequestRepositoryImpl(new MySqlPaymentRequestGateway(
		    initDataSource(), new PaymentRequestBehaviorsFactoryImpl()));
	}
    }

    private static BasicDataSource initDataSource() {
	BasicDataSource dataSource = new BasicDataSource();
	DataBaseSettings dbSettings = DataBaseSettings.getInstance();
	dataSource.setUsername(dbSettings.username());
	dataSource.setPassword(dbSettings.password());
	dataSource.setUrl(dbSettings.url());
	dataSource.setDriverClassName(dbSettings.driverClassName());
	return dataSource;
    }

}
