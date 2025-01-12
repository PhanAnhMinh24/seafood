package com.sales.authorization.service.role;

import com.sales.authorization.entity.Role;
import com.sales.authorization.exception.AppException;
import com.sales.authorization.exception.ErrorCode;
import com.sales.authorization.repository.RoleRepository;
import com.sales.authorization.utils.constants.CommonConstants;
import org.apache.commons.lang3.ObjectUtils;
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

    @Override
    public Role getRoleSeller() {
        Role role = roleRepository.findByName(CommonConstants.SELLER);
        if (ObjectUtils.isEmpty(role)) {
            throw new AppException(ErrorCode.ROLE_NOT_FOUND);
        }
        return role;
    }
}
