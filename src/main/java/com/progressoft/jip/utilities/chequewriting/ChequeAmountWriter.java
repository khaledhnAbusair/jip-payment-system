package com.progressoft.jip.utilities.chequewriting;

import java.math.BigDecimal;

public interface ChequeAmountWriter {

	String writeAmountInWords(BigDecimal amount, String currencyCode);

}
