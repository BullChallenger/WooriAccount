package io.woori.account.wooriaccount.service.inter;

import io.woori.account.wooriaccount.domain.entity.Role;
import io.woori.account.wooriaccount.dto.role.RoleDTO;

public interface RoleService {
    RoleDTO saveRole(Role role);
}
