package io.woori.account.wooriaccount.role.repository.basic;

import java.util.Optional;

import io.woori.account.wooriaccount.role.domain.CustomerRole;

public interface CommonCustomerRoleRepository {

	CustomerRole findById(CustomerRole.Pk pk);
	Optional<CustomerRole> findById(CustomerRole.Pk pk);

	Optional<CustomerRole> save(CustomerRole customerRole);
}
