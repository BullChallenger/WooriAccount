package io.woori.account.wooriaccount.role.repository.basic;

import io.woori.account.wooriaccount.role.domain.CustomerRole;

public interface CommonCustomerRoleRepository {

	CustomerRole findById(CustomerRole.Pk pk);
}
