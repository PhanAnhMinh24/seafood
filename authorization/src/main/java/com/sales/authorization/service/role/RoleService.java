package com.sales.authorization.service.role;

import com.sales.authorization.repository.RoleRepository;
import com.sales.generic.entity.Role;
import org.springframework.stereotype.Service;

@Service
public class RoleService implements IRoleService {
    private final RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public Role getRoleDefault() {
        return roleRepository.findAll().stream()
                .filter(Role::getIsDefault)
                .findFirst().orElse(null);
    }
}
