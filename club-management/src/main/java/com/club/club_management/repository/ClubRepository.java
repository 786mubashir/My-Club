package com.club.club_management.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.club.club_management.entity.Club;
import com.club.club_management.entity.Users;

@Repository
public interface ClubRepository extends JpaRepository<Club, Long> {

    Optional<Club> findByManager(Users manager);

    boolean existsByManager(Users manager);

}