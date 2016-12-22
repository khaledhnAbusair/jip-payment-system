package com.progressoft.jip.usecase;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;

import com.progressoft.jip.factory.impl.AccountGatewayDBBehaviorsFactoryImpl;
import com.progressoft.jip.gateway.impl.MySqlAccountGateway;
import com.progressoft.jip.model.Account;
import com.progressoft.jip.repository.AccountRepository;
import com.progressoft.jip.repository.impl.AccountRepositoryImpl;
import com.progressoft.jip.utilities.DataBaseSettings;
import com.progressoft.jip.view.AccountView;

public class LoadAccountsUseCase {

    private AccountRepository accountRepository;

    public LoadAccountsUseCase() {
        accountRepository = new AccountRepositoryImpl(new MySqlAccountGateway(prepareDatasource(),
                new AccountGatewayDBBehaviorsFactoryImpl()));
    }

    private DataSource prepareDatasource() {
        BasicDataSource dataSource = new BasicDataSource();
        DataBaseSettings settings = DataBaseSettings.getInstance();
        dataSource.setUsername(settings.username());
        dataSource.setPassword(settings.password());
        dataSource.setUrl(settings.url());
        dataSource.setDriverClassName(settings.driverClassName());
        return dataSource;
    }

    public Collection<AccountView> loadAccounts() {
        List<AccountView> accounts = new ArrayList<>();
        AccountView accountInfo = new AccountView();
        for (Account account : accountRepository.loadAccounts()) {
            account.buildAccountView(accountInfo);
            accounts.add(accountInfo.build());
        }
        return accounts;

    }
}
