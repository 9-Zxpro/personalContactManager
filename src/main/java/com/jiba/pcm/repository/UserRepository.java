package com.jiba.pcm.repository;

import com.jiba.pcm.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    User findByEmail(String email);
    User findByUsername(String userName);

    Optional<User> findByEmailToken(String token);
}
