package com.progressoft.jip.factory;

import java.util.Collection;

import com.progressoft.jip.datastructures.AccountDatastructure;

public interface AccountGatewayDBBehaviorsFactory {

    Behavior<AccountDatastructure> loadAccountByIBAN();

    Behavior<Collection<AccountDatastructure>> loadAccounts();

}
