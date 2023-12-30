package io.woori.account.wooriaccount.repository.common;

import io.woori.account.wooriaccount.domain.entity.Account;

public interface CommonAccountRepository {

    Account findById(Long id);
    void deleteById(Long id);


}
