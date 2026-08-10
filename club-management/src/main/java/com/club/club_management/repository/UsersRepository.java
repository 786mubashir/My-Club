package com.club.club_management.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.club.club_management.entity.Users;
import java.util.Optional;


public interface UsersRepository extends JpaRepository<Users, Integer> {
    Optional<Users> findByEmail(String email);
    boolean existsByEmail(String email);

}
