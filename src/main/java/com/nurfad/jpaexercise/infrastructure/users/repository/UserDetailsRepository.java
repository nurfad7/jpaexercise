package com.nurfad.jpaexercise.infrastructure.users.repository;

import com.nurfad.jpaexercise.entity.UserDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserDetailsRepository extends JpaRepository<UserDetail, Long> {
    Optional<UserDetail> findByUserId(Long UserId);
}
