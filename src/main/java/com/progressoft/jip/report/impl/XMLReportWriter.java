package com.progressoft.jip.report.impl;

import java.io.PrintStream;
import java.util.Collection;

import com.progressoft.jip.datastructures.builder.PaymentRequestDataStructureBuilder;
import com.progressoft.jip.datastructures.builder.impl.PaymentRequestDataStructureBuilderImpl;
import com.progressoft.jip.model.PaymentRequest;
import com.progressoft.jip.report.ReportStructure;
import com.progressoft.jip.report.ReportWriter;
import com.thoughtworks.xstream.XStream;

public class XMLReportWriter implements ReportWriter {

    private ReportStructure report;

    @Override
    public void write(String accountIBAN, Collection<PaymentRequest> paymentRequests, PrintStream writer) {

	XStream xstream = new XStream();
	xstream.autodetectAnnotations(true);
	xstream.processAnnotations(ReportStructure.class);

	report = new ReportStructure(accountIBAN);

	for (PaymentRequest request : paymentRequests) {
	    PaymentRequestDataStructureBuilder builder = new PaymentRequestDataStructureBuilderImpl();
	    request.build(builder);
	    report.addRequest(builder.build());
	}

	writer.println(xstream.toXML(report));

    }

}
