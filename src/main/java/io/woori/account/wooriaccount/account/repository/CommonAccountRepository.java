package io.woori.account.wooriaccount.account.repository;

import io.woori.account.wooriaccount.account.domain.entity.Account;
import org.springframework.data.jpa.repository.Lock;

import javax.persistence.LockModeType;
import java.util.Optional;

public interface CommonAccountRepository {

    Optional<Account> findById(Long id);
    void deleteById(Long id);
    Account save(Account account);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<Account> findByAccountNumber(String accountNumber);



}
