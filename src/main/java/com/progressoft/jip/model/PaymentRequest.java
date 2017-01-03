package com.progressoft.jip.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;

import com.progressoft.jip.datastructures.PaymentRequestDataStructure;
import com.progressoft.jip.datastructures.builder.PaymentRequestDataStructureBuilder;
import com.progressoft.jip.iban.IBANValidator;
import com.progressoft.jip.model.exception.AccountRuleViolationException;
import com.progressoft.jip.repository.AccountRepository;
import com.progressoft.jip.utilities.chequewriting.ChequeAmountWriter;

public class PaymentRequest {

	private PaymentRequestDataStructure dataStructure;

	public PaymentRequest(PaymentRequestDataStructure dataStructure) {
		this.dataStructure = dataStructure;
	}

	public boolean validateIBAN(IBANValidator... validators) {
		return Arrays.asList(validators).stream()
				.allMatch(validator -> validator.isValid(dataStructure.getBeneficiaryAccountIBAN()));
	}

	public void build(PaymentRequestDataStructureBuilder builder) {
		builder.setId(dataStructure.getId()).setOrderingAccountIBAN(dataStructure.getOrderingAccountIBAN())
				.setBeneficiaryAccountIBAN(dataStructure.getBeneficiaryAccountIBAN())
				.setBeneficiaryName(dataStructure.getBeneficiaryName())
				.setPaymentAmount(dataStructure.getPaymentAmount()).setCurrencyCode(dataStructure.getCurrencyCode())
				.setPurposeCdoe(dataStructure.getPurposeCode()).setPaymentDate(LocalDate.parse(dataStructure.getPaymentDate().toString())).setPaymentAmountInWords(dataStructure.getAmountInWords());
	}

	public void checkAccountRules(AccountRepository accountRepository) throws AccountRuleViolationException {
		Account loadAccountByIBAN = accountRepository.loadAccountByIBAN(dataStructure.getOrderingAccountIBAN());
		loadAccountByIBAN.checkPaymentRule(LocalDate.parse(dataStructure.getPaymentDate().toString()));
	}
	
	public void chequeWriteAmount(ChequeAmountWriter amountWriter){
		BigDecimal paymentAmount = dataStructure.getPaymentAmount();
		String currencyCode = dataStructure.getCurrencyCode();
		String writeAmountInWords = amountWriter.writeAmountInWords(paymentAmount, currencyCode);
	    dataStructure.setAmountInWords(writeAmountInWords);
		
		
	}
}
