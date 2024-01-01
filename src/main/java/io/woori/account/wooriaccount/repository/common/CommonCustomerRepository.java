package io.woori.account.wooriaccount.repository.common;

import io.woori.account.wooriaccount.domain.entity.Account;
import io.woori.account.wooriaccount.domain.entity.Customer;

public interface CommonCustomerRepository {

    Customer findById(Long id);
    void deleteById(Long id);


}
