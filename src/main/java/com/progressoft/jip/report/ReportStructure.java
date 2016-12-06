package com.progressoft.jip.report;

import java.util.ArrayList;
import java.util.List;

import com.progressoft.jip.datastructures.PaymentRequestDataStructure;
import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("Report")
public class ReportStructure {

    @XStreamAlias("OrderingAccountIBAN")
    private String orderingAccountIBAN;

    @XStreamAlias("PaymentRequests")
    private List<PaymentRequestDataStructure> paymentRequests = new ArrayList<>();

    public ReportStructure(String orderingAccountIBAN) {
	this.orderingAccountIBAN = orderingAccountIBAN;
    }

    public void addRequest(PaymentRequestDataStructure requestDS) {
	paymentRequests.add(requestDS);
    }

}
