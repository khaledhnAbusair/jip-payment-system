package com.progressoft.jip.datastructures.builder;

import com.progressoft.jip.view.AccountView;

public interface AccountViewBuilder {
    AccountViewBuilder setIban(String IBAN);
    AccountView build();
}
