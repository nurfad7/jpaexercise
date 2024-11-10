package com.nurfad.jpaexercise.infrastructure.users.repository;

import com.nurfad.jpaexercise.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RolesRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(String name);
}
