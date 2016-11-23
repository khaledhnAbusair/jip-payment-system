package com.progressoft.jip.datastructures;

import java.sql.Date;

public class PaymentRequestDataStructure {

    private int id;
    private String orderingAccountIBAN;
    private String beneficiaryAccountIBAN;
    private String beneficiaryName;
    private double paymentAmount;
    private String currencyCode;
    private String purposeCdoe;
    private Date paymentDate;

    public PaymentRequestDataStructure() {
    }

    public int getId() {
	return id;
    }

    public PaymentRequestDataStructure setId(int id) {
	this.id = id;
	return this;
    }

    public String getOrderingAccountIBAN() {
	return orderingAccountIBAN;
    }

    public PaymentRequestDataStructure setOrderingAccountIBAN(String orderingAccountIBAN) {
	this.orderingAccountIBAN = orderingAccountIBAN;
	return this;
    }

    public String getBeneficiaryAccountIBAN() {
	return beneficiaryAccountIBAN;
    }

    public PaymentRequestDataStructure setBeneficiaryAccountIBAN(String beneficiaryAccountIBAN) {
	this.beneficiaryAccountIBAN = beneficiaryAccountIBAN;
	return this;
    }

    public String getBeneficiaryName() {
	return beneficiaryName;
    }

    public PaymentRequestDataStructure setBeneficiaryName(String beneficiaryName) {
	this.beneficiaryName = beneficiaryName;
	return this;
    }

    public double getPaymentAmount() {
	return paymentAmount;
    }

    public PaymentRequestDataStructure setPaymentAmount(double paymentAmount) {
	this.paymentAmount = paymentAmount;
	return this;
    }

    public String getCurrencyCode() {
	return currencyCode;
    }

    public PaymentRequestDataStructure setCurrencyCode(String currencyCode) {
	this.currencyCode = currencyCode;
	return this;
    }

    public String getPurposeCode() {
	return purposeCdoe;
    }

    public PaymentRequestDataStructure setPurposeCdoe(String purposeCdoe) {
	this.purposeCdoe = purposeCdoe;
	return this;
    }

    public Date getPaymentDate() {
	return paymentDate;
    }

    public PaymentRequestDataStructure setPaymentDate(Date paymentDate) {
	this.paymentDate = paymentDate;
	return this;
    }

}
