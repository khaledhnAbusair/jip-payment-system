package com.progressoft.jip.datastructures;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamOmitField;

@XStreamAlias("PaymentRequest")
public class PaymentRequestDataStructure {

	@XStreamOmitField
	private int id;
	private String orderingAccountIBAN;
	private String beneficiaryAccountIBAN;
	private String beneficiaryName;
	private BigDecimal paymentAmount;
	private String currencyCode;
	private String purposeCode;
	private Date paymentDate;
	private String amountInWords;

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

	public BigDecimal getPaymentAmount() {
		return paymentAmount;
	}

	public void setPaymentAmount(BigDecimal paymentAmount) {
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

	public String getAmountInWords() {
		return this.amountInWords;
	}

	public void setAmountInWords(String writeAmountInWords) {
		this.amountInWords = writeAmountInWords;

	}

}
