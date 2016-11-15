package com.progressoft.jip.model;

import java.util.Date;

import com.progressoft.jip.datastructures.AccountDatastructure;
import com.progressoft.jip.datastructures.Currency;
import com.progressoft.jip.datastructures.PurposeCodeName;
import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("PaymentRequest")
public class PaymentRequestDataStructer {

    @XStreamAlias("paymentPurpose")
    private PurposeCodeName purpose;

    @XStreamAlias("orderingAccount")
    private AccountDatastructure orderingAccount;

    @XStreamAlias("beneficiaryAccount")
    private AccountDatastructure beneficiaryAccount;

    @XStreamAlias("amount")
    private double amount;

    @XStreamAlias("transfareCurrency")
    private Currency transfareCurrency;

    @XStreamAlias("paymentDate")
    private Date paymentDate;

    public PaymentRequestDataStructer(PurposeCodeName purpose, AccountDatastructure orderingAccount,
	    AccountDatastructure beneficiaryAccount, double amount, Currency transfareCurrency, Date paymentDate) {
	this.purpose = purpose;
	this.orderingAccount = orderingAccount;
	this.beneficiaryAccount = beneficiaryAccount;
	this.amount = amount;
	this.transfareCurrency = transfareCurrency;
	this.paymentDate = paymentDate;
    }

    public PurposeCodeName getPurpose() {
	return purpose;
    }

    public void setPurpose(PurposeCodeName purpose) {
	this.purpose = purpose;
    }

    public AccountDatastructure getOrderingAccount() {
	return orderingAccount;
    }

    public void setOrderingAccount(AccountDatastructure orderingAccount) {
	this.orderingAccount = orderingAccount;
    }

    public AccountDatastructure getBeneficiaryAccount() {
	return beneficiaryAccount;
    }

    public void setBeneficiaryAccount(AccountDatastructure beneficiaryAccount) {
	this.beneficiaryAccount = beneficiaryAccount;
    }

    public double getAmount() {
	return amount;
    }

    public void setAmount(double amount) {
	this.amount = amount;
    }

    public Currency getTransfareCurrency() {
	return transfareCurrency;
    }

    public void setTransfareCurrency(Currency transfareCurrency) {
	this.transfareCurrency = transfareCurrency;
    }

    public Date getPaymentDate() {
	return paymentDate;
    }

    public void setPaymentDate(Date paymentDate) {
	this.paymentDate = paymentDate;
    }

}
