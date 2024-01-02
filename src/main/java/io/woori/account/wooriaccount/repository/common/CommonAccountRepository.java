package io.woori.account.wooriaccount.repository.common;

import io.woori.account.wooriaccount.domain.entity.Account;

import java.util.Optional;

public interface CommonAccountRepository {

    Optional<Account> findById(Long id);
    void deleteById(Long id);
    Account save(Account account);

    Optional<Account> findByAccountNumber(String accountNnumber);


}
