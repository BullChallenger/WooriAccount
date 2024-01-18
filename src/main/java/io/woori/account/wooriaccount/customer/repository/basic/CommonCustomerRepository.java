package io.woori.account.wooriaccount.customer.repository.basic;

import java.util.Optional;

import io.woori.account.wooriaccount.customer.domain.entity.Customer;

public interface CommonCustomerRepository {

	Optional<Customer> findById(Long id);

	void deleteById(Long id);

	Optional<Customer> findByCustomerEmail(String customerEmail);

	Customer save(Customer customer);

}
