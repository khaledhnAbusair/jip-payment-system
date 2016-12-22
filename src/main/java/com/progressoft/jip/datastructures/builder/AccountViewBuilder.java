package com.progressoft.jip.datastructures.builder;

import com.progressoft.jip.view.AccountView;

public interface AccountViewBuilder {
	AccountViewBuilder setIban(String IBAN);

	AccountViewBuilder setBalance(double balance);

	AccountViewBuilder setCurrencyCode(String currencyCode);

	AccountViewBuilder setType(String type);

	AccountViewBuilder setStatus(String status);

	AccountView build();
}
