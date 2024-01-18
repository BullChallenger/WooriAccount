package io.woori.account.wooriaccount.role.repository.jpa;

import org.springframework.data.repository.Repository;

import io.woori.account.wooriaccount.role.domain.Role;
import io.woori.account.wooriaccount.role.repository.basic.CommonRoleRepository;

public interface RoleRepository extends Repository<Role, Long>, CommonRoleRepository {

}
