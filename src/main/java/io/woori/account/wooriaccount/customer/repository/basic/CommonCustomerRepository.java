package io.woori.account.wooriaccount.customer.repository.basic;

import io.woori.account.wooriaccount.customer.domain.entity.Customer;

import java.util.Optional;

public interface CommonCustomerRepository {

    Optional<Customer> findById(Long id);
    void deleteById(Long id);
    Optional<Customer> findByCustomerEmail(String customerEmail);

    Customer save(Customer customer);


}
