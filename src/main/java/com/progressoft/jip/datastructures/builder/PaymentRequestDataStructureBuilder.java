package com.progressoft.jip.datastructures.builder;

import java.sql.Date;

import com.progressoft.jip.datastructures.PaymentRequestDataStructure;

public interface PaymentRequestDataStructureBuilder {

    public PaymentRequestDataStructureBuilder setId(int id);

    public PaymentRequestDataStructureBuilder setOrderingAccountIBAN(String orderingAccountIBAN);

    public PaymentRequestDataStructureBuilder setBeneficiaryAccountIBAN(String beneficiaryAccountIBAN);

    public PaymentRequestDataStructureBuilder setBeneficiaryName(String beneficiaryName);

    public PaymentRequestDataStructureBuilder setPaymentAmount(double paymentAmount);

    public PaymentRequestDataStructureBuilder setCurrencyCode(String currencyCode);

    public PaymentRequestDataStructureBuilder setPurposeCdoe(String purposeCdoe);

    public PaymentRequestDataStructureBuilder setPaymentDate(Date paymentDate);

    public PaymentRequestDataStructure build();
}
