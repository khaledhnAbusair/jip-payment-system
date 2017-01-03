package com.progressoft.jip.report.impl;

import java.io.PrintStream;
import java.util.Collection;

import com.progressoft.jip.datastructures.PaymentRequestDataStructure;
import com.progressoft.jip.datastructures.builder.PaymentRequestDataStructureBuilder;
import com.progressoft.jip.datastructures.builder.impl.PaymentRequestDataStructureBuilderImpl;
import com.progressoft.jip.model.PaymentRequest;
import com.progressoft.jip.report.ReportWriter;

public class CSVReportWriter implements ReportWriter {

    @Override
    public void write(String accountIBAN, Collection<PaymentRequest> paymentRequests, PrintStream stream) {
        stream.println("Ordering Account IBAN: " + accountIBAN);
        stream.printf("%-22s,%-34s,%-34s,%-16s,%-16s,%-16s,%-16s,%-64s,%-16s%n","Payment Request Number","Ordering IBAN","Beneficiary IBAN","Beneficiary Name","Payment Amount","Currency Code","Purpose Code","Amount In Words","Payment Date");
        for (PaymentRequest request : paymentRequests) {
            PaymentRequestDataStructureBuilder builder = new PaymentRequestDataStructureBuilderImpl();
            request.build(builder);
            PaymentRequestDataStructure ds = builder.build();
            stream.printf("%-22s,%-34s,%-34s,%-16s,%-16f,%-16s,%-16s,%-64s ,%-16s%n","PaymentRequest-"+ ds.getId(), ds.getOrderingAccountIBAN(),
                    ds.getBeneficiaryAccountIBAN(), ds.getBeneficiaryName(), ds.getPaymentAmount(),
                    ds.getCurrencyCode(), ds.getPurposeCode(),ds.getAmountInWords(), ds.getPaymentDate().toString());
        }
    }

}
