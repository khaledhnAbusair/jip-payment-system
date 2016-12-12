package com.progressoft.jip.datastructures.builder.impl;

import java.sql.Date;

import com.progressoft.jip.datastructures.PaymentRequestDataStructure;
import com.progressoft.jip.datastructures.builder.PaymentRequestDataStructureBuilder;

public class PaymentRequestDataStructureBuilderImpl implements PaymentRequestDataStructureBuilder {

    private int id;
    private String orderingAccountIBAN;
    private String beneficiaryAccountIBAN;
    private String beneficiaryName;
    private double paymentAmount;
    private String currencyCode;
    private String purposeCode;
    private Date paymentDate;

    @Override
    public PaymentRequestDataStructureBuilder setPurposeCdoe(String purposeCode) {
	this.purposeCode = purposeCode;
	return this;
    }

    @Override
    public PaymentRequestDataStructureBuilder setPaymentDate(Date paymentDate) {
	this.paymentDate = paymentDate;
	return this;
    }

    @Override
    public PaymentRequestDataStructureBuilder setPaymentAmount(double paymentAmount) {
	this.paymentAmount = paymentAmount;
	return this;
    }

    @Override
    public PaymentRequestDataStructureBuilder setOrderingAccountIBAN(String orderingAccountIBAN) {
	this.orderingAccountIBAN = orderingAccountIBAN;
	return this;
    }

    @Override
    public PaymentRequestDataStructureBuilder setId(int id) {
	this.id = id;
	return this;
    }

    @Override
    public PaymentRequestDataStructureBuilder setCurrencyCode(String currencyCode) {
	this.currencyCode = currencyCode;
	return this;
    }

    @Override
    public PaymentRequestDataStructureBuilder setBeneficiaryName(String beneficiaryName) {
	this.beneficiaryName = beneficiaryName;
	return this;
    }

    @Override
    public PaymentRequestDataStructureBuilder setBeneficiaryAccountIBAN(String beneficiaryAccountIBAN) {
	this.beneficiaryAccountIBAN = beneficiaryAccountIBAN;
	return this;
    }

    @Override
    public PaymentRequestDataStructure build() {
	PaymentRequestDataStructure req = new PaymentRequestDataStructure();
	req.setId(id);
	req.setOrderingAccountIBAN(orderingAccountIBAN);
	req.setBeneficiaryAccountIBAN(beneficiaryAccountIBAN);
	req.setBeneficiaryName(beneficiaryName);
	req.setPaymentAmount(paymentAmount);
	req.setCurrencyCode(currencyCode);
	req.setPurposeCode(purposeCode);
	req.setPaymentDate(paymentDate);
	return req;
    }
}
