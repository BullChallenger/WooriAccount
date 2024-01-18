package io.woori.account.wooriaccount.repository.common;

import io.woori.account.wooriaccount.domain.entity.Role;


public interface CommonRoleRepository {

    Role findById(Long id);
    Role save(Role role);
}
