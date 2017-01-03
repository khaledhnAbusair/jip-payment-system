package com.progressoft.jip.datastructures.builder;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.progressoft.jip.datastructures.PaymentRequestDataStructure;

public interface PaymentRequestDataStructureBuilder {

	public PaymentRequestDataStructureBuilder setId(int id);

	public PaymentRequestDataStructureBuilder setOrderingAccountIBAN(String orderingAccountIBAN);

	public PaymentRequestDataStructureBuilder setBeneficiaryAccountIBAN(String beneficiaryAccountIBAN);

	public PaymentRequestDataStructureBuilder setBeneficiaryName(String beneficiaryName);

	public PaymentRequestDataStructureBuilder setPaymentAmount(BigDecimal paymentAmount);

	public PaymentRequestDataStructureBuilder setCurrencyCode(String currencyCode);

	public PaymentRequestDataStructureBuilder setPurposeCdoe(String purposeCdoe);

	public PaymentRequestDataStructureBuilder setPaymentDate(LocalDate paymentDate);

	public PaymentRequestDataStructureBuilder setPaymentAmountInWords(String paymentAmountInWords);

	public PaymentRequestDataStructure build();
}
