package com.payment_gateway.Auth.Repository;

import com.payment_gateway.Auth.Entity.Role;
import com.payment_gateway.Auth.Entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository
        extends JpaRepository<User, Integer> {


    Page<User> findByActiveTrue(Pageable pageable);

    List<User> findByRole(Role role);
    Optional<User> findByEmail(String email);
}
