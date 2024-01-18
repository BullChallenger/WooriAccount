package io.woori.account.wooriaccount.repository.common;

import io.woori.account.wooriaccount.domain.entity.CustomerRole;
import io.woori.account.wooriaccount.domain.entity.CustomerRole.CustomerRolePk;

import javax.swing.text.html.Option;
import java.util.Optional;

public interface CommonCustomerRoleRepository {

    Optional<CustomerRole> findById(CustomerRolePk pk);

    CustomerRole save(CustomerRole role);


}
