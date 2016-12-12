package com.progressoft.jip.datastructures;

import java.sql.Date;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamOmitField;

@XStreamAlias("PaymentRequest")
public class PaymentRequestDataStructure {

    @XStreamOmitField
    private int id;
    // ID as id,ORD_IBAN as orderingAccountIBAN,BENEF_IBAN as
    // beneficiaryAccountIBAN,BENEF_NAME as beneficiaryName,AMOUNT as
    // paymentAmount,CURRENCY_CODE as currencyCode,PURPOSE_CODE as
    // purposeCode,PAYMENT_DATE as paymentDate
    private String orderingAccountIBAN;
    private String beneficiaryAccountIBAN;
    private String beneficiaryName;
    private double paymentAmount;
    private String currencyCode;
    private String purposeCode;
    private Date paymentDate;

    public PaymentRequestDataStructure() {
    }

    public int getId() {
	return id;
    }

    public void setId(int id) {
	this.id = id;
    }

    public String getOrderingAccountIBAN() {
	return orderingAccountIBAN;
    }

    public void setOrderingAccountIBAN(String orderingAccountIBAN) {
	this.orderingAccountIBAN = orderingAccountIBAN;
    }

    public String getBeneficiaryAccountIBAN() {
	return beneficiaryAccountIBAN;
    }

    public void setBeneficiaryAccountIBAN(String beneficiaryAccountIBAN) {
	this.beneficiaryAccountIBAN = beneficiaryAccountIBAN;
    }

    public String getBeneficiaryName() {
	return beneficiaryName;
    }

    public void setBeneficiaryName(String beneficiaryName) {
	this.beneficiaryName = beneficiaryName;
    }

    public double getPaymentAmount() {
	return paymentAmount;
    }

    public void setPaymentAmount(double paymentAmount) {
	this.paymentAmount = paymentAmount;
    }

    public String getCurrencyCode() {
	return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
	this.currencyCode = currencyCode;
    }

    public String getPurposeCode() {
	return purposeCode;
    }

    public void setPurposeCode(String purposeCode) {
	this.purposeCode = purposeCode;
    }

    public Date getPaymentDate() {
	return paymentDate;
    }

    public void setPaymentDate(Date paymentDate) {
	this.paymentDate = paymentDate;
    }

}
