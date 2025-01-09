package com.sales.authorization.repository;

import com.sales.generic.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Boolean existsByEmail(String email);  // Sử dụng phương thức này vì email đã được thêm vào User

    Optional<User> findByEmail(String email);
}
