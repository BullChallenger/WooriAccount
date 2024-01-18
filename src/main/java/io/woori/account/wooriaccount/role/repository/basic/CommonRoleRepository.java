package io.woori.account.wooriaccount.role.repository.basic;

import java.util.Optional;

import io.woori.account.wooriaccount.role.domain.Role;

public interface CommonRoleRepository {

	Optional<Role> findById(Byte roleId);

	Optional<Role> save(Role role);

}
