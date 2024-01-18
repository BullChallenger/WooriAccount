package io.woori.account.wooriaccount.account.repository;

import java.util.Optional;

import javax.persistence.LockModeType;

import org.springframework.data.jpa.repository.Lock;

import io.woori.account.wooriaccount.account.domain.entity.Account;

public interface CommonAccountRepository {

	Optional<Account> findById(Long id);

	void deleteById(Long id);

	Account save(Account account);

	@Lock(LockModeType.PESSIMISTIC_WRITE)
	Optional<Account> findByAccountNumber(String accountNumber);

}
