package com.sales.authorization.repository;

import com.sales.authorization.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Boolean existsByEmail(String email);  // Sử dụng phương thức này vì email đã được thêm vào User

    Optional<User> findByEmail(String email);

    List<User> findByIdIn(List<Long> ids);
}
