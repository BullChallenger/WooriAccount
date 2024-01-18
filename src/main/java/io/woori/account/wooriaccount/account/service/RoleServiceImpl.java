package io.woori.account.wooriaccount.service;


import io.woori.account.wooriaccount.domain.entity.Role;
import io.woori.account.wooriaccount.dto.role.RoleDTO;
import io.woori.account.wooriaccount.repository.jpa.RoleRepository;
import io.woori.account.wooriaccount.service.inter.RoleService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;


    @Transactional
    @Override
    public RoleDTO saveRole(Role role) {

        Role save = roleRepository.save(role);
        RoleDTO roleDTO = new RoleDTO(save.getRoleId(),save.getRoleName());

        return roleDTO;
    }
}
