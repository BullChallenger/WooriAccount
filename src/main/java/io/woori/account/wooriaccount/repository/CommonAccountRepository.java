package io.woori.account.wooriaccount.repository;

import io.woori.account.wooriaccount.domain.entity.Account;

public interface CommonAccountRepository {

    Account findById();
    void deleteById();


}
