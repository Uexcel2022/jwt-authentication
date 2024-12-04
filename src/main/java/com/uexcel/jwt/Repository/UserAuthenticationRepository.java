package com.uexcel.jwt.Repository;

import com.uexcel.jwt.entity.UserAuthentication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserAuthenticationRepository extends JpaRepository<UserAuthentication, String> {
    Optional<UserAuthentication> findByEmail(String email);
    Boolean existsByEmailIgnoreCase(String email);
}
