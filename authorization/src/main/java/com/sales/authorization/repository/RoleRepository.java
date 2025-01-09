package com.sales.authorization.repository;

import com.sales.generic.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(String name);

    Role findByIsDefaultTrue();
}
