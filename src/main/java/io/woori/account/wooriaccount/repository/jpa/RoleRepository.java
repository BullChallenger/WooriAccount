package io.woori.account.wooriaccount.repository.jpa;

import io.woori.account.wooriaccount.domain.entity.Role;
import io.woori.account.wooriaccount.repository.common.CommonRoleRepository;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.support.Repositories;

public interface RoleRepository extends Repository<Role, Long>, CommonRoleRepository{

}
