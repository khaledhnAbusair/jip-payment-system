package com.progressoft.jip.usecase;

import java.util.Objects;

import com.progressoft.jip.datastructures.AccountDatastructure;
import com.progressoft.jip.model.Account;
import com.progressoft.jip.repository.AccountRepository;
import com.progressoft.jip.usecase.exception.NullAccountsRepositoryException;

public class CreateAccountUseCase {

	private AccountRepository accountRepository;

	public CreateAccountUseCase(AccountRepository repository) {
		if(Objects.isNull(repository)){
			throw new NullAccountsRepositoryException();
		}
		this.accountRepository = repository;
	}

	public void createAccount(AccountDatastructure accountDatastructure) {
		accountRepository.createAccount(new Account(accountDatastructure));
	}

}