package com.sales.authorization.service.role;

import com.sales.authorization.entity.Role;

public interface IRoleService {
    Role getRoleDefault();

    Role getRoleSeller();
}
