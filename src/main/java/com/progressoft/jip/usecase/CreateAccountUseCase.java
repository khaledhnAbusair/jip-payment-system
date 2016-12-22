package com.progressoft.jip.usecase;

import java.util.Objects;

import com.progressoft.jip.repository.AccountRepository;
import com.progressoft.jip.usecase.exception.NullAccountsRepositoryException;

public class CreateAccountUseCase {

	private AccountRepository repository;

	public CreateAccountUseCase(AccountRepository repository) {
		if(Objects.isNull(repository))
			throw new NullAccountsRepositoryException();
		this.repository = repository;
	}

}