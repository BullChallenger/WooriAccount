package io.woori.account.wooriaccount.repository.common;

import io.woori.account.wooriaccount.domain.entity.Account;
import io.woori.account.wooriaccount.domain.entity.Customer;

import java.util.Optional;

public interface CommonCustomerRepository {

    Optional<Customer> findById(Long id);
    void deleteById(Long id);
    Optional<Customer> findByCustomerEmail(String customerEmail);

    Customer save(Customer customer);


}
