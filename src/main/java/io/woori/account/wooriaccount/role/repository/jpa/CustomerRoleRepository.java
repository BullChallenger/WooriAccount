package io.woori.account.wooriaccount.role.repository.jpa;

import org.springframework.data.repository.Repository;

import io.woori.account.wooriaccount.role.domain.CustomerRole;
import io.woori.account.wooriaccount.role.repository.basic.CommonCustomerRoleRepository;

public interface CustomerRoleRepository
	extends Repository<CustomerRole, CustomerRole.Pk>, CommonCustomerRoleRepository {

}
