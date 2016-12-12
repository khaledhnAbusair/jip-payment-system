package com.progressoft.jip.datastructures.builder;

import com.progressoft.jip.datastructures.builder.impl.AccountView;

public interface AccountInfoBuilder {
    AccountInfoBuilder setIban(String IBAN);
    AccountView build();
}
