package io.woori.account.wooriaccount.controller;

import io.woori.account.wooriaccount.domain.entity.Role;
import io.woori.account.wooriaccount.dto.role.RoleDTO;
import io.woori.account.wooriaccount.dto.role.RoleRequestDTO;
import io.woori.account.wooriaccount.service.inter.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/roles")
@RequiredArgsConstructor
public class RoleRestController {
    private final RoleService service;

    @PostMapping("/save")
    public ResponseEntity<RoleDTO> saveRole(@RequestBody RoleRequestDTO roleDTO){


        RoleDTO response = service.saveRole(Role.builder()
                .roleName(roleDTO.getRoleName())
                .build());

        return ResponseEntity.ok(response);

    }
}
