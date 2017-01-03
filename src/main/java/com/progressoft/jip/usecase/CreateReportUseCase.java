package com.progressoft.jip.usecase;

import com.progressoft.jip.report.ReportProvider;
import com.progressoft.jip.report.impl.CSVReportWriter;
import com.progressoft.jip.report.impl.XMLReportWriter;
import com.progressoft.jip.repository.PaymentRequestRepository;

public class CreateReportUseCase {
	
	private ReportProvider reportProvider;

	public CreateReportUseCase(PaymentRequestRepository paymentRequestRepository) {
		reportProvider = new ReportProvider(paymentRequestRepository);
	}
	
	public void generateXmlRepotForAccount(String accountIban){
		reportProvider.print(accountIban, new XMLReportWriter(), System.out);
	}
	
	public void generateCsvReportForAccount(String accountIban){
		reportProvider.print(accountIban, new CSVReportWriter(), System.out);
	}

}
