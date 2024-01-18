package io.woori.account.wooriaccount.repository.jpa;

import io.woori.account.wooriaccount.domain.entity.CustomerRole;
import io.woori.account.wooriaccount.domain.entity.CustomerRole.CustomerRolePk;
import io.woori.account.wooriaccount.repository.common.CommonCustomerRoleRepository;
import org.springframework.data.repository.Repository;

public interface CustomerRoleRepository extends Repository<CustomerRole , CustomerRolePk>, CommonCustomerRoleRepository {


}
